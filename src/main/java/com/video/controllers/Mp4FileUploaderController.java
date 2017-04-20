package com.video.controllers;

import com.video.services.DocumentService;
import com.video.to.DocumentData;
import com.video.to.UploadResponse;
import com.video.utils.DG;
import com.video.utils.SecurityContextUtils;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.MultimediaInfo;
import it.sauronsoftware.jave.VideoInfo;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;

/**
 * Created by kotabek on 4/20/17.
 */
@Controller
public class Mp4FileUploaderController {
    @Autowired
    private DocumentService documentService;

    /**
     * Upload Multipart file with duration
     *
     * @param file income data
     * @return message for client
     */
    @RequestMapping(value = "/upload-file", method = RequestMethod.POST)
    public
    @ResponseBody
    UploadResponse upload(@RequestParam(value = "file", required = false) MultipartFile file) {

        if (file == null || file.isEmpty()) {
            return new UploadResponse("Please select a file to upload", false);
        }

        String message = null;
        boolean success = false;
        try {
            final String path = ((DiskFileItem) ((CommonsMultipartFile) file).getFileItem()).getStoreLocation().getPath();
            Encoder encoder = new Encoder();
            MultimediaInfo info = encoder.getInfo(new File(path));
            VideoInfo vInfo = info.getVideo();

            long duration = Math.round(Double.valueOf(info.getDuration()) / 1000d);


            if (duration > 600) {
                message = "Your file was longe than 10 minutes.";
            } else {
                // todo place to upload it to amazon S3
                DocumentData documentData = new DocumentData();
                documentData.setMemberId(SecurityContextUtils.getMemberId());
                documentData.setName(DG.getString(file.getOriginalFilename(), "NO_NAME"));
                documentData.setDuraton(duration);
                if (vInfo.getBitRate() > 0) {
                    documentData.setBitRate(vInfo.getBitRate());
                }
                documentData.setFrameRate(vInfo.getFrameRate());
                if (vInfo.getSize() != null) {
                    documentData.setFrameWidth(vInfo.getSize().getWidth());
                    documentData.setFrameHeight(vInfo.getSize().getHeight());
                }
                documentService.registerDocument(documentData);
                message = "You successfully uploaded '" + documentData.getName() + "' with duration = " + duration + " seconds ";
                success = true;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        if (message == null) {
            message = "File incorrect or broken. Please try Again";
            success = false;
        }
        return new UploadResponse(message, success);
    }
}

