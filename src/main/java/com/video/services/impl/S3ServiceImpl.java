package com.video.services.impl;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.video.services.S3Service;
import com.video.to.FileTO;
import com.video.utils.DG;
import com.video.utils.EncryptionUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by kotabek on 4/20/17.
 */
@Service
public class S3ServiceImpl implements S3Service {
    private static final Logger LOG = Logger.getLogger(S3ServiceImpl.class);

    @Override
    @Transactional
    public FileTO upload(String fileName, String fileExtensions, String contentType, byte[] data) throws IOException {

        FileTO to = new FileTO();
        to.setName(fileName);
        this.uploadData(fileName, data, fileExtensions, contentType, to);

        to.setUrl(this.getResourceUrlForDay(to.getKey()));
        return to;
    }

    private FileTO uploadData(String fileName, byte[] content, String fileExtensions, String contentType, FileTO attachment) throws IOException {
        InputStream inputStream = new ByteArrayInputStream(content);
        final String key = this.generateKey(fileExtensions, inputStream, contentType);
        attachment.setKey(key);
        attachment.setMimeType(contentType);
        attachment.setName(fileName);
        attachment.setSize(content.length);
        this.upload(attachment, inputStream, true);
        return attachment;
    }

    /**
     * Uploads the file and generates globally available temporary URL string
     *
     * @param attachment  the content type of resulting file
     * @param inputStream the input stream of resulting file
     * @param forPublic
     * @return The glabaly available link to the file being uploaded
     *
     * @throws IOException
     */
    private String upload(FileTO attachment, InputStream inputStream, boolean forPublic) {
        final String fileName = attachment.getName();
        final String key = attachment.getKey();
        final String contentType = attachment.getMimeType();

        final AmazonS3 s3 = this.getS3();

        final String bucketName = this.getBucketName();

        //Set content type
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentEncoding("UTF-8");
        try {
            objectMetadata.setContentLength(inputStream.available());
        } catch (IOException ioe) {
            LOG.error(ioe.getMessage(), ioe);
        }
        objectMetadata.setExpirationTime(null);
        objectMetadata.setContentDisposition("attachment; filename=" + fileName);
        objectMetadata.setContentType(contentType);

        //Upload file
        PutObjectResult result = s3.putObject(new PutObjectRequest(bucketName, key, inputStream, objectMetadata));
        System.out.println("etag=>" + result.getETag());
        if (forPublic) {
            s3.setObjectAcl(bucketName, key, CannedAccessControlList.PublicRead);
            return generateURL(s3, key, null);
        } else {
            return generateURL(s3, key, null);
        }
    }

    @Override
    public S3Object download(String key) {
        S3Object object = getS3().getObject(new GetObjectRequest(getBucketName(), key));
        return object;
    }

    /**
     * Generate file key for upload;
     *
     * @param fileExtension the extension of the resulting file
     * @param inputStream   the input stream of resulting file
     * @param contentType   the content type of resulting file
     */
    @Override
    public String generateKey(String fileExtension, InputStream inputStream, String contentType) {
        String key = EncryptionUtils.md5(String.valueOf(System.currentTimeMillis()) + "_" + fileExtension + "_" + contentType);
        return "V" + key + DG.getString(fileExtension);
    }

    /**
     * Generates globally available temporary URL string
     *
     * @param key  the key of the resulting file
     * @param date the time of link expiration interval
     * @return the resulting file URL
     *
     * @throws IOException
     */
    @Override
    public String getResourceUrl(String key, Date date) {
        return generateURL(getS3(), key, date);
    }

    @Override
    public String getResourceUrlForDay(String key) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1);
        return generateURL(getS3(), key, calendar.getTime());
    }

    private String generateURL(AmazonS3 s3, String key, Date date) {
        //Generate the publicly available URL
        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(getBucketName(), key);
        if (date == null) {
            request.setExpiration(new Date(130, 1, 1));
        } else {
            request.setExpiration(date);
        }
        URL url = s3.generatePresignedUrl(request);
        return url.toString();
    }

    /**
     * Initialize the AWS S3 service
     *
     * @return AWS S3 service
     *
     * @throws IOException
     */
    private AmazonS3 getS3() {
        //todo need move to file wih prefix by spring-profile
        final String accessKey = "AKIAIIHUVJUI2XMUQO7A";
        final String secretKey = "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";

        final AmazonS3 s3 = new AmazonS3Client(new BasicAWSCredentials(accessKey, secretKey));
        return s3;
    }

    private String getBucketName() {
        //todo need move to file wih prefix by spring-profile
        return "myvideotest2017";
    }
}
