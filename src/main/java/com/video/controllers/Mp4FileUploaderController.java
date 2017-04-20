package com.video.controllers;

import com.xuggle.xuggler.IContainer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kotabek on 4/20/17.
 */
@Controller
public class Mp4FileUploaderController {
    @RequestMapping(value = "/upload-file", method = RequestMethod.POST)
    public
    @ResponseBody
    Object upload(@RequestParam(value = "file", required = false) MultipartFile file,
                  RedirectAttributes redirectAttributes,
                  HttpServletRequest request) {

        if (file == null || file.isEmpty()) {
            return new HashMap<String, String>() {{
                put("message", "Please select a file to upload");
            }};
        }


        String message = null;
        try {

            IContainer container = IContainer.make();
            int result = container.open(file.getInputStream(), null);
            long duration = Math.round(container.getDuration() / 1000000d);


            if (duration > 600) {
                message = "Your file was longe than 10 minutes.";
            } else {
                // todo place to upload it to amazon S3
                message = "You successfully uploaded '" + file.getOriginalFilename() + "' with duration = " + duration + " seconds ";
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        if (message == null) {
            message = "File incorrect or broken. Please try Again";
        }
        Map<String, String> result = new HashMap<String, String>();
        result.put("message", message);
        return result;
    }
}

