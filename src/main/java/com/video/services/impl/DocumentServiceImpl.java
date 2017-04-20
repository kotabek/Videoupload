package com.video.services.impl;

import com.video.dao.MemberDocumentDao;
import com.video.domains.MemberDocumentDomain;
import com.video.services.DocumentService;
import com.video.to.DocumentData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        domain.setDuration(data.getDuration());
        domain.setBitRate(data.getBitRate());
        domain.setFrameRate(data.getFrameRate());
        domain.setFrameWidth(data.getFrameWidth());
        domain.setFrameHeight(data.getFrameHeight());
        domain.setS3key(data.getKey());
        domain.setMimeType(data.getMimeType());
        domain.setSize(data.getSize());
        memberDocumentDao.save(domain);
    }

    @Override
    @Transactional
    public List<MemberDocumentDomain> getMemberDocuments(Long memberId) {
        return memberDocumentDao.getByMemberId(memberId);
    }
}
