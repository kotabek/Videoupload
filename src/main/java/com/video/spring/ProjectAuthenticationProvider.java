package com.video.spring;

import com.video.services.MemberService;
import com.video.spring.to.AuthDetails;
import com.video.spring.tokens.ProjectAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kotabek on 4/20/17.
 */
public final class ProjectAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private MemberService memberService;

    public static List<GrantedAuthority> AUTHORITIES = new ArrayList<GrantedAuthority>();

    static {
        AUTHORITIES.add(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        final AuthDetails authDetail = memberService.authenticateMember(username, password);

        ProjectAuthenticationToken result;

        if (authDetail != null
            && authDetail.getMemberId() != null) {
            List<GrantedAuthority> userAuthorities = AUTHORITIES;
            result = new ProjectAuthenticationToken(username, password, userAuthorities);
            result.setAuthDetails(authDetail);
        } else {
            throw new UsernameNotFoundException("Invalid username or password");
        }
        return result;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return ProjectAuthenticationToken.class.isAssignableFrom(aClass);
    }
}
