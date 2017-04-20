package com.video.spring.to;

/**
 * Created by kotabek on 4/20/17.
 */
public class AuthDetails {
    private Long memberId;

    public AuthDetails(Long memberId) {
        this.memberId = memberId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }
}

