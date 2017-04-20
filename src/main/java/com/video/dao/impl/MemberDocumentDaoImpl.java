package com.video.dao.impl;

import com.video.dao.MemberDocumentDao;
import com.video.domains.MemberDocumentDomain;
import org.springframework.stereotype.Repository;

/**
 * Created by kotabek on 4/20/17.
 */
@Repository
public class MemberDocumentDaoImpl extends SimpleDao<MemberDocumentDomain> implements MemberDocumentDao {
    public MemberDocumentDaoImpl() {
        super(MemberDocumentDomain.class);
    }
}
