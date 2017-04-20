package com.video.dao;

import com.video.domains.SessionDomain;

/**
 * Created by kotabek on 4/20/17.
 */
public interface SessionDao {
    SessionDomain getById(String sessionId);

    SessionDomain createSession(Long memberId);
}
