package com.video.dao.impl;

import com.video.dao.MemberDocumentDao;
import com.video.domains.MemberDocumentDomain;
import com.video.utils.DG;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

/**
 * Created by kotabek on 4/20/17.
 */
@Repository
public class MemberDocumentDaoImpl extends SimpleDao<MemberDocumentDomain> implements MemberDocumentDao {
    public MemberDocumentDaoImpl() {
        super(MemberDocumentDomain.class);
    }

    @Override
    public List<MemberDocumentDomain> getByMemberId(Long memberId) {
        if (DG.getLong(memberId) <= 0) {
            return Collections.emptyList();
        }

        String sql = "select d from MemberDocumentDomain d " +
                     "  where d.memberId = :memberId " +
                     "  order by d.id desc  ";
        return entityManager.createQuery(sql, entityClass)
                            .setParameter("memberId", memberId)
                            .getResultList();
    }
}
