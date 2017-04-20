package com.video.spring.filters;

import com.video.services.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.util.Assert;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by kotabek on 4/20/17.
 */
public final class ProjectSecurityContextPersistenceFilter extends GenericFilterBean {

    static final String FILTER_APPLIED = "__spring_security_scpf_applied";

    private SecurityContextRepository repo = new HttpSessionSecurityContextRepository();

    private boolean forceEagerSessionCreation = false;

    @Autowired
    private MemberService memberService;

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        if (request.getAttribute(FILTER_APPLIED) != null) {
            // ensure that filter is only applied once per request
            chain.doFilter(request, response);
            return;
        }

        request.setAttribute(FILTER_APPLIED, Boolean.TRUE);

        if (forceEagerSessionCreation) {
            HttpSession session = request.getSession();

            if (session.isNew()) {
                logger.debug("Eagerly created session: " + session.getId());
            }
        }

        HttpRequestResponseHolder holder = new HttpRequestResponseHolder(request, response);
        SecurityContext contextBeforeChainExecution = repo.loadContext(holder);

        try {
            //todo we can read session from cookie if we will keet it in database
            if(!memberService.processSessionId(contextBeforeChainExecution, response, request)){
                SecurityContextHolder.clearContext();
            }

            SecurityContextHolder.setContext(contextBeforeChainExecution);

            chain.doFilter(holder.getRequest(), holder.getResponse());

        } finally {
            SecurityContext contextAfterChainExecution = SecurityContextHolder.getContext();
            // Crucial removal of SecurityContextHolder contents - do this before anything else.
            SecurityContextHolder.clearContext();
            repo.saveContext(contextAfterChainExecution, holder.getRequest(), holder.getResponse());
            request.removeAttribute(FILTER_APPLIED);

            logger.debug("SecurityContextHolder now cleared, as request processing completed");
        }
    }

    public void setSecurityContextRepository(SecurityContextRepository repo) {
        Assert.notNull(repo, "SecurityContextRepository cannot be null");
        this.repo = repo;
    }

    public void setForceEagerSessionCreation(boolean forceEagerSessionCreation) {
        this.forceEagerSessionCreation = forceEagerSessionCreation;
    }
}
