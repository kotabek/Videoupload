package com.video.domains;

import org.hibernate.envers.NotAudited;

import javax.persistence.*;

/**
 * Created by kotabek on 4/20/17.
 */
@Entity
@Table(name = Tables.MemberDocument)
public class MemberDocumentDomain extends SimpleEntity {
    private Long memberId;
    @NotAudited
    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId", insertable = false, updatable = false)
    private MemberDomain member;

    private String fileName;
    private Long duration;
    private Integer bitRate;
    private Float frameRate;
    private Integer frameWidth;
    private Integer frameHeight;

    @Override
    public String getEntityKey() {
        return Tables.MemberDocument;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public MemberDomain getMember() {
        return member;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Integer getBitRate() {
        return bitRate;
    }

    public void setBitRate(Integer bitRate) {
        this.bitRate = bitRate;
    }

    public void setMember(MemberDomain member) {
        this.member = member;
    }

    public Float getFrameRate() {
        return frameRate;
    }

    public void setFrameRate(Float frameRate) {
        this.frameRate = frameRate;
    }

    public Integer getFrameWidth() {
        return frameWidth;
    }

    public void setFrameWidth(Integer frameWidth) {
        this.frameWidth = frameWidth;
    }

    public Integer getFrameHeight() {
        return frameHeight;
    }

    public void setFrameHeight(Integer frameHeight) {
        this.frameHeight = frameHeight;
    }

    public String getFrameSize() {
        if (this.frameHeight != null
            && this.frameWidth != null) {
            return this.frameWidth + " x " + this.frameHeight;
        }
        return "";
    }
}