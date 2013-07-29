/**************************************************************************************************
 * This file is part of [SpringAtom] Copyright [kornicameister@gmail.com][2013]                   *
 *                                                                                                *
 * [SpringAtom] is free software: you can redistribute it and/or modify                           *
 * it under the terms of the GNU General Public License as published by                           *
 * the Free Software Foundation, either version 3 of the License, or                              *
 * (at your option) any later version.                                                            *
 *                                                                                                *
 * [SpringAtom] is distributed in the hope that it will be useful,                                *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of                                 *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the                                  *
 * GNU General Public License for more details.                                                   *
 *                                                                                                *
 * You should have received a copy of the GNU General Public License                              *
 * along with [SpringAtom].  If not, see <http://www.gnu.org/licenses/gpl.html>.                  *
 **************************************************************************************************/

package org.agatom.springatom.helpers.auditing;

import org.agatom.springatom.jpa.repositories.SUserRepository;
import org.agatom.springatom.model.beans.user.QSUser;
import org.agatom.springatom.model.beans.user.SUser;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.apache.log4j.Logger.getLogger;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class SpringSecurityAuditorAware implements AuditorAware<SUser>, ApplicationListener<ContextRefreshedEvent> {
    private static final Logger LOGGER = getLogger(SpringSecurityAuditorAware.class);
    @Autowired
    SUserRepository repository;
    private SUser systemUser;

    @Override
    public SUser getCurrentAuditor() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SUser principal;
        if (authentication == null || !authentication.isAuthenticated()) {
            principal = systemUser;
        } else {
            principal = (SUser) authentication.getPrincipal();
        }
        LOGGER.info(String.format("Current auditor is >>> %s", principal));
        return principal;
    }

    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        if (this.systemUser == null) {
            LOGGER.info("%s >>> loading system user");
            systemUser = this.repository.findOne(QSUser.sUser.credentials.login.eq("SYSTEM"));
        }
    }
}
