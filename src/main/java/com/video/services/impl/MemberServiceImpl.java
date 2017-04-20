package com.video.services.impl;

import com.video.dao.MemberDao;
import com.video.dao.SessionDao;
import com.video.domains.MemberDomain;
import com.video.domains.SessionDomain;
import com.video.services.MemberService;
import com.video.spring.ProjectAuthenticationProvider;
import com.video.spring.to.AuthDetails;
import com.video.spring.tokens.ProjectAuthenticationToken;
import com.video.utils.CookiesHelper;
import com.video.utils.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by kotabek on 4/20/17.
 */
@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private SessionDao sessionDao;

    @Override
    @Transactional
    public AuthDetails authenticateMember(String username, String password) {
        if (StrUtil.isEmpty(username)
            || StrUtil.isEmpty(password)) {
            return null;
        }

        MemberDomain member = memberDao.getByUserName(username);
        if (member == null
            || !password.equals(member.getPassword())) {
            return null;
        }

        return new AuthDetails(member.getId());
    }

    @Override
    @Transactional
    public void createSession(AuthDetails details, HttpServletRequest request, HttpServletResponse response) {
        if (details != null
            && details.getMemberId() != null
            && response != null) {
            SessionDomain session = sessionDao.createSession(details.getMemberId());
            response.addCookie(CookiesHelper.createCookie(CookiesHelper.SESSION_COOKIE, session.getId()));
        }
    }

    @Override
    @Transactional
    public boolean processSessionId(SecurityContext contextBeforeChainExecution, HttpServletResponse response, HttpServletRequest request) {
        try {
            String sessionId = CookiesHelper.getCookieVal(CookiesHelper.SESSION_COOKIE, request.getCookies());
            if (!StrUtil.isEmpty(sessionId)) {
                SessionDomain session = sessionDao.getById(sessionId);
                if (session != null
                    && !session.isExpired()) {
                    if (contextBeforeChainExecution != null) {
                        if (contextBeforeChainExecution.getAuthentication() != null
                            && contextBeforeChainExecution.getAuthentication() instanceof ProjectAuthenticationToken) {
                            ProjectAuthenticationToken token = (ProjectAuthenticationToken) contextBeforeChainExecution.getAuthentication();

                            MemberDomain member = memberDao.getByUserName(token.getPrincipal());
                            if (member != null
                                && session.getMemberId().equals(member.getId())) {
                                token.setAuthDetails(new AuthDetails(member.getId()));
                                return true;
                            }
                        } else {
                            MemberDomain member = memberDao.get(session.getMemberId());
                            if (member != null) {
                                ProjectAuthenticationToken token = new ProjectAuthenticationToken(member.getUsername(), null, ProjectAuthenticationProvider.AUTHORITIES);
                                token.setAuthDetails(new AuthDetails(member.getId()));
                                contextBeforeChainExecution.setAuthentication(token);
                                return true;
                            }
                        }
                    }
                    CookiesHelper.removeCookie(request, response, CookiesHelper.SESSION_COOKIE);
                }
            }
            contextBeforeChainExecution.setAuthentication(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
