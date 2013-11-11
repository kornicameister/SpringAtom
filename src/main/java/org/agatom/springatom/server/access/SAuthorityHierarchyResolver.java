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

package org.agatom.springatom.server.access;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.agatom.springatom.server.model.beans.user.authority.SAuthority;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.access.hierarchicalroles.CycleInRoleHierarchyException;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.Collection;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class SAuthorityHierarchyResolver
        implements RoleHierarchy,
                   InitializingBean {
    private static final Logger LOGGER = Logger.getLogger(SAuthorityHierarchyResolver.class);
    private Properties hierarchyBundle;
    /**
     * rolesReachableInOneStepMap is a Map that under the key of a specific role name contains a set of all roles
     * reachable from this role in 1 step
     */
    private Map<GrantedAuthority, Set<GrantedAuthority>> rolesReachableInOneStepMap        = null;
    /**
     * rolesReachableInOneOrMoreStepsMap is a Map that under the key of a specific role name contains a set of all
     * roles reachable from this role in 1 or more steps
     */
    private Map<GrantedAuthority, Set<GrantedAuthority>> rolesReachableInOneOrMoreStepsMap = null;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (this.hierarchyBundle != null) {
            LOGGER.debug("afterPropertiesSet() - The following role hierarchyBundle was set: " + this.hierarchyBundle);
            this.buildRolesReachableInOneStepMap();
            this.buildRolesReachableInOneOrMoreStepsMap();
        } else {
            throw new Exception("hierarchyBundle is null which is forbidden and prevents from building the hierarchy");
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getReachableGrantedAuthorities(final Collection<? extends GrantedAuthority> authorities) {
        if (authorities == null || authorities.isEmpty()) {
            return AuthorityUtils.NO_AUTHORITIES;
        }

        final Set<GrantedAuthority> reachableRoles = Sets.newHashSet();

        for (final GrantedAuthority authority : authorities) {
            this.addReachableRoles(reachableRoles, authority);
            final Set<GrantedAuthority> additionalReachableRoles = this.getRolesReachableInOneOrMoreSteps(authority);
            if (additionalReachableRoles != null) {
                reachableRoles.addAll(additionalReachableRoles);
            }
        }

        LOGGER.debug(String
                .format("getReachableGrantedAuthorities() - From the roles %s one can reach %s in zero or more steps.", authorities, reachableRoles));

        return Lists.newArrayList(reachableRoles);
    }

    private void addReachableRoles(Set<GrantedAuthority> reachableRoles,
                                   GrantedAuthority authority) {
        for (final GrantedAuthority testAuthority : reachableRoles) {
            final String testKey = testAuthority.getAuthority();
            if ((testKey != null) && (testKey.equals(authority.getAuthority()))) {
                return;
            }
        }
        reachableRoles.add(authority);
    }

    // SEC-863
    private Set<GrantedAuthority> getRolesReachableInOneOrMoreSteps(
            GrantedAuthority authority) {

        if (authority.getAuthority() == null) {
            return null;
        }

        for (GrantedAuthority testAuthority : rolesReachableInOneOrMoreStepsMap.keySet()) {
            String testKey = testAuthority.getAuthority();
            if ((testKey != null) && (testKey.equals(authority.getAuthority()))) {
                return rolesReachableInOneOrMoreStepsMap.get(testAuthority);
            }
        }

        return null;
    }

    /**
     * Parse input and build the map for the roles reachable in one step: the higher role will become a key that
     * references a set of the reachable lower roles.
     */
    private void buildRolesReachableInOneStepMap() throws Exception {
        this.rolesReachableInOneStepMap = Maps.newHashMap();
        for (final String key : this.hierarchyBundle.stringPropertyNames()) {
            final String[] rawEntry = this.hierarchyBundle.getProperty(key).split(",");
            final GrantedAuthority higherRole = new SAuthority(key);
            for (final String lowerRawRole : rawEntry) {
                final GrantedAuthority lowerRole = new SAuthority(lowerRawRole);
                final Set<GrantedAuthority> rolesReachableInOneStepSet;
                if (!this.rolesReachableInOneStepMap.containsKey(higherRole)) {
                    rolesReachableInOneStepSet = Sets.newHashSet();
                    this.rolesReachableInOneStepMap.put(higherRole, rolesReachableInOneStepSet);
                } else {
                    rolesReachableInOneStepSet = this.rolesReachableInOneStepMap.get(higherRole);
                }
                this.addReachableRoles(rolesReachableInOneStepSet, lowerRole);
                LOGGER.debug(String
                        .format("buildRolesReachableInOneStepMap() - From role %s one can reach role %s in one step.", higherRole, lowerRole));
            }
        }
    }

    /**
     * For every higher role from rolesReachableInOneStepMap store all roles that are reachable from it in the map of
     * roles reachable in one or more steps. (Or throw a CycleInRoleHierarchyException if a cycle in the role
     * hierarchy definition is detected)
     */
    private void buildRolesReachableInOneOrMoreStepsMap() {
        this.rolesReachableInOneOrMoreStepsMap = Maps.newHashMap();
        // iterate over all higher roles from rolesReachableInOneStepMap

        for (final GrantedAuthority role : rolesReachableInOneStepMap.keySet()) {
            final Set<GrantedAuthority> rolesToVisitSet = Sets.newHashSet();

            if (this.rolesReachableInOneStepMap.containsKey(role)) {
                rolesToVisitSet.addAll(this.rolesReachableInOneStepMap.get(role));
            }

            final Set<GrantedAuthority> visitedRolesSet = Sets.newHashSet();

            while (!rolesToVisitSet.isEmpty()) {
                // take a role from the rolesToVisit set
                final GrantedAuthority aRole = rolesToVisitSet.iterator().next();
                rolesToVisitSet.remove(aRole);
                this.addReachableRoles(visitedRolesSet, aRole);
                if (this.rolesReachableInOneStepMap.containsKey(aRole)) {
                    final Set<GrantedAuthority> newReachableRoles = rolesReachableInOneStepMap.get(aRole);

                    // definition of a cycle: you can reach the role you are starting from
                    if (rolesToVisitSet.contains(role) || visitedRolesSet.contains(role)) {
                        throw new CycleInRoleHierarchyException();
                    } else {
                        // no cycle
                        rolesToVisitSet.addAll(newReachableRoles);
                    }
                }
            }
            this.rolesReachableInOneOrMoreStepsMap.put(role, visitedRolesSet);

            LOGGER.debug(String
                    .format("buildRolesReachableInOneOrMoreStepsMap() - From role %s one can reach %s in one or more steps.", role, visitedRolesSet));
        }

    }

    /**
     * Sets the path to hierarchy bundle file.
     *
     * @param hierarchyBundle
     *         path to the hierarchy bundle file (*.properties)
     */
    @Required
    public void setHierarchyBundle(final Properties hierarchyBundle) {
        this.hierarchyBundle = hierarchyBundle;
    }

}
