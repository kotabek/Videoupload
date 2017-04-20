package com.video.to;

/**
 * Created by kotabek on 4/20/17.
 */
public class UploadResponse {
    private boolean success;
    private String message;

    private DocumentTO item;

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

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public DocumentTO getItem() {
        return item;
    }

    public void setItem(DocumentTO item) {
        this.item = item;
    }
}
