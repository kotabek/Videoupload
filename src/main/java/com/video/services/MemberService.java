package com.video.services;

import com.video.spring.to.AuthDetails;
import org.springframework.security.core.context.SecurityContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by kotabek on 4/20/17.
 */
public interface MemberService {
    boolean processSessionId(SecurityContext contextBeforeChainExecution, HttpServletResponse response, HttpServletRequest request);

    AuthDetails authenticateMember(String username, String password);

    void createSession(AuthDetails details, HttpServletRequest request, HttpServletResponse response);
}
