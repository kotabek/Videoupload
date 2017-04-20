package com.video.services;

import com.amazonaws.services.s3.model.S3Object;
import com.video.to.FileTO;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

/**
 * Created by kotabek on 4/20/17.
 */
public interface S3Service {
    FileTO upload(String fileName, String fileExtensions, String contentType, byte[] data) throws IOException;

    S3Object download(String key);

    String generateKey(String fileExtension, InputStream inputStream, String contentType);

    String getResourceUrl(String key, Date date);

    String getResourceUrlForDay(String key);
}
