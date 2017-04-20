package com.video.dao;

import com.video.domains.MemberDomain;

/**
 * Created by kotabek on 4/20/17.
 */
public interface MemberDao extends Dao<MemberDomain> {
    MemberDomain getByUserName(String username);
}
