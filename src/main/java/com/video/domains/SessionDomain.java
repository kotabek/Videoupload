package com.video.domains;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by kotabek on 4/20/17.
 */
@Entity
@Table(name = Tables.Session)
public class SessionDomain {
    @Id
    private String id;
    @NotNull
    private Long memberId;
    private Date createdTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public boolean isExpired() {
        return this.getMemberId() == null
               || this.getCreatedTime() == null
               || this.getCreatedTime().getTime() <= System.currentTimeMillis();
    }
}
