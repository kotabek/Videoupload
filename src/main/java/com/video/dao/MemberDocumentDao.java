package com.video.dao;

import com.video.domains.MemberDocumentDomain;

import java.util.List;

/**
 * Created by kotabek on 4/20/17.
 */
public interface MemberDocumentDao extends Dao<MemberDocumentDomain> {
    List<MemberDocumentDomain> getByMemberId(Long memberId);
}
