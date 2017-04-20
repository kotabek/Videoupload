package com.video.spring.tokens;

import com.video.spring.to.AuthDetails;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * Created by kotabek on 4/20/17.
 */
public final class ProjectAuthenticationToken extends AbstractAuthenticationToken {

    private final String username;
    private String password;
    private AuthDetails authDetails;

    public ProjectAuthenticationToken(String username, String password) {
        super(null);
        this.username = String.valueOf(username);
        this.password = String.valueOf(password);
        setAuthenticated(false);
    }

    public ProjectAuthenticationToken(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.username = username;
        this.password = password;
        super.setAuthenticated(true); // must use super, as we override
    }

    public String getCredentials() {
        return this.password;
    }

    public String getPrincipal() {
        return this.username;
    }

    @Override
    public Object getDetails() {
        return authDetails;
    }

    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException(
                    "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }

        super.setAuthenticated(false);
    }

    public void setAuthDetails(AuthDetails authDetails) {
        this.authDetails = authDetails;
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        password = null;
    }
}