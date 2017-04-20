package com.video.to;

/**
 * Created by kotabek on 4/20/17.
 */
public class UploadResponse {
    private boolean success;
    private String message;

    public UploadResponse() {
    }

    public UploadResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
