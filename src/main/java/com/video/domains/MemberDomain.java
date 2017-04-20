package com.video.domains;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Created by kotabek on 4/20/17.
 */
@Entity
@Table(name = Tables.Member)
public class MemberDomain extends SimpleEntity {
    @NotNull
    @Column(unique = true)
    private String username;
    @NotNull
    private String password;

    @Override
    public String getEntityKey() {
        return Tables.Member;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
