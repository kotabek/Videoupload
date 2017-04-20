package com.video.services.impl;

import com.video.dao.MemberDocumentDao;
import com.video.domains.MemberDocumentDomain;
import com.video.services.DocumentService;
import com.video.to.DocumentData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by kotabek on 4/20/17.
 */
@Service
public class DocumentServiceImpl implements DocumentService {
    @Autowired
    private MemberDocumentDao memberDocumentDao;

    @Override
    @Transactional
    public void registerDocument(DocumentData data) {
        if (data == null
            || data.getMemberId() == null) {
            return;
        }
        MemberDocumentDomain domain = new MemberDocumentDomain();
        domain.setMemberId(data.getMemberId());
        domain.setFileName(data.getName());
        domain.setDuration(data.getDuraton());
        domain.setBitRate(data.getBitRate());
        memberDocumentDao.save(domain);
    }
}
