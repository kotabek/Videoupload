package com.video.services.impl;

import com.video.dao.MemberDao;
import com.video.domains.MemberDomain;
import com.video.services.MemberService;
import com.video.spring.to.AuthDetails;
import com.video.spring.tokens.ProjectAuthenticationToken;
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
    public boolean processSessionId(SecurityContext contextBeforeChainExecution, HttpServletResponse response, HttpServletRequest request) {
        try {
            if (contextBeforeChainExecution != null) {
                if (contextBeforeChainExecution.getAuthentication() != null
                    && contextBeforeChainExecution.getAuthentication() instanceof ProjectAuthenticationToken) {
                    ProjectAuthenticationToken token = (ProjectAuthenticationToken) contextBeforeChainExecution.getAuthentication();

                    MemberDomain member = memberDao.getByUserName(token.getPrincipal());
                    if (member != null) {
                        token.setAuthDetails(new AuthDetails(member.getId()));
                        return true;
                    }
                }
                contextBeforeChainExecution.setAuthentication(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
