package org.agatom.springatom.boot.security.impl;

import org.agatom.springatom.boot.security.LoggingUserDetailsService;
import org.agatom.springatom.data.hades.service.NUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-10-02</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@Service
class DelegatedUserDetailsService
        implements LoggingUserDetailsService {
    @Autowired
    private NUserService userService = null;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        return this.userService.loadUserByUsername(username);
    }
}
