package org.agatom.springatom.data.support.user;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-11-17</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
public class AuthenticatedUserBean
        extends UserBean {
    private String                                 username                = null;
    private Collection<? extends GrantedAuthority> authorities             = null;
    private boolean                                isAccountNonExpired     = false;
    private boolean                                isAccountNonLocked      = false;
    private boolean                                isCredentialsNonExpired = false;
    private boolean                                isEnabled               = false;

    public AuthenticatedUserBean() {
        super(true);
    }

    public AuthenticatedUserBean setIsAccountNonExpired(final boolean isAccountNonExpired) {
        this.isAccountNonExpired = isAccountNonExpired;
        return this;
    }

    public AuthenticatedUserBean setIsAccountNonLocked(final boolean isAccountNonLocked) {
        this.isAccountNonLocked = isAccountNonLocked;
        return this;
    }

    public AuthenticatedUserBean setIsCredentialsNonExpired(final boolean isCredentialsNonExpired) {
        this.isCredentialsNonExpired = isCredentialsNonExpired;
        return this;
    }

    public AuthenticatedUserBean setIsEnabled(final boolean isEnabled) {
        this.isEnabled = isEnabled;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public AuthenticatedUserBean setUsername(final String username) {
        this.username = username;
        return this;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public AuthenticatedUserBean setAuthorities(final Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
        return this;
    }

    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    public boolean isEnabled() {
        return isEnabled;
    }
}
