package com.video.dao.impl;

import com.video.dao.MemberDao;
import com.video.domains.MemberDomain;
import com.video.utils.StrUtil;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by kotabek on 4/20/17.
 */
@Repository
public class MemberDaoImpl extends SimpleDao<MemberDomain> implements MemberDao {
    public MemberDaoImpl() {
        super(MemberDomain.class);
    }

    @Override
    public MemberDomain getByUserName(String username) {
        if (StrUtil.isEmpty(username)) {
            return null;
        }
        String sql = "select m from MemberDomain m " +
                     "  where lower(m.username) = :username ";
        List<MemberDomain> list = entityManager.createQuery(sql, entityClass)
                                               .setParameter("username", username.trim().toLowerCase())
                                               .setMaxResults(1)
                                               .getResultList();
        return list.isEmpty() ? null : list.get(0);
    }
}