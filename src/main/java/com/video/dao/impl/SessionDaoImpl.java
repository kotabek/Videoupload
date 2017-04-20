package com.video.dao.impl;

import com.video.dao.SessionDao;
import com.video.domains.SessionDomain;
import com.video.utils.IdGenerator;
import com.video.utils.StrUtil;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.NotNull;
import java.util.Calendar;

/**
 * Created by kotabek on 4/20/17.
 */
@Repository
public class SessionDaoImpl implements SessionDao {
    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public SessionDomain getById(String sessionId) {
        if (StrUtil.isEmpty(sessionId)) {
            return null;
        }
        return entityManager.find(SessionDomain.class, sessionId);
    }

    @Override
    @NotNull
    public SessionDomain createSession(Long memberId) {
        SessionDomain session = new SessionDomain();
        session.setId(new IdGenerator().nextId());
        session.setMemberId(memberId);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.DATE, 1);//todo create session for 1 day, it is test
        session.setCreatedTime(calendar.getTime());
        entityManager.persist(session);
        return session;
    }
}
