package com.video.to;

/**
 * Created by kotabek on 4/20/17.
 */
public class DocumentTO extends FileTO {
    private Long duration;
    private Integer bitRate;
    private Float frameRate;
    private Integer frameWidth;
    private Integer frameHeight;

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public void setBitRate(Integer bitRate) {
        this.bitRate = bitRate;
    }

    public Integer getBitRate() {
        return bitRate;
    }

    public void setFrameRate(Float frameRate) {
        this.frameRate = frameRate;
    }

    public Float getFrameRate() {
        return frameRate;
    }

    public void setFrameWidth(Integer frameWidth) {
        this.frameWidth = frameWidth;
    }

    public Integer getFrameWidth() {
        return frameWidth;
    }

    public void setFrameHeight(Integer frameHeight) {
        this.frameHeight = frameHeight;
    }

    public Integer getFrameHeight() {
        return frameHeight;
    }

    public String getFrameSize() {
        if (this.frameHeight != null
            && this.frameWidth != null) {
            return this.frameWidth + " x " + this.frameHeight;
        }
        return "";
    }
}
