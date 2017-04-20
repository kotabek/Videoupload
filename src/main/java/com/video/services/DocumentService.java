package com.video.services;

import com.video.domains.MemberDocumentDomain;
import com.video.to.DocumentData;

import java.util.List;

/**
 * Created by kotabek on 4/20/17.
 */
public interface DocumentService {
    void registerDocument(DocumentData data);

    List<MemberDocumentDomain> getMemberDocuments(Long memberId);
}
