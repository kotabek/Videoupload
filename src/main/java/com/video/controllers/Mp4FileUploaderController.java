package com.video.controllers;

import com.video.services.DocumentService;
import com.video.to.DocumentData;
import com.video.to.UploadResponse;
import com.video.utils.SecurityContextUtils;
import com.xuggle.xuggler.IContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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

            IContainer container = IContainer.make();
            int result = container.open(file.getInputStream(), null);
            long duration = Math.round(container.getDuration() / 1000000d);


            if (duration > 600) {
                message = "Your file was longe than 10 minutes.";
            } else {
                // todo place to upload it to amazon S3
                DocumentData documentData = new DocumentData();
                documentData.setMemberId(SecurityContextUtils.getMemberId());
                documentData.setName(file.getOriginalFilename());
                documentData.setDuraton(duration);
                documentData.setBitRate(container.getBitRate());
                documentService.registerDocument(documentData);
                message = "You successfully uploaded '" + documentData.getName() + "' with duration = " + duration + " seconds ";
                success = true;
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        if (message == null) {
            message = "File incorrect or broken. Please try Again";
            success = false;
        }
        return new UploadResponse(message, success);
    }
}

