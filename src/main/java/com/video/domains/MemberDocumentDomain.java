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
}