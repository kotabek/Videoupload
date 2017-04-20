package com.video.to;

/**
 * Created by kotabek on 4/20/17.
 */
public class DocumentData {
    private Long memberId;
    private String name;
    private Long duraton;
    private Integer bitRate;

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDuraton(Long duraton) {
        this.duraton = duraton;
    }

    public Long getDuraton() {
        return duraton;
    }

    public void setBitRate(Integer bitRate) {
        this.bitRate = bitRate;
    }

    public Integer getBitRate() {
        return bitRate;
    }
}
