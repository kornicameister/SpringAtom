package org.agatom.springatom.data.hades.service.impl;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import org.agatom.springatom.data.constraints.Password;
import org.agatom.springatom.data.constraints.UserName;
import org.agatom.springatom.data.constraints.ValidUser;
import org.agatom.springatom.data.event.PersistenceEventListenerAdapter;
import org.agatom.springatom.data.hades.model.data.NDataInstallationStatus;
import org.agatom.springatom.data.hades.model.person.NPerson;
import org.agatom.springatom.data.hades.model.user.NUser;
import org.agatom.springatom.data.hades.model.user.NUserToPersonLink;
import org.agatom.springatom.data.hades.model.user.authority.NRole;
import org.agatom.springatom.data.hades.model.user.authority.UserToRoleLink;
import org.agatom.springatom.data.hades.repo.repositories.authority.NRoleRepository;
import org.agatom.springatom.data.hades.repo.repositories.authority.NUserToRoleRepository;
import org.agatom.springatom.data.hades.repo.repositories.user.NUserRepository;
import org.agatom.springatom.data.hades.repo.repositories.user.NUserToPersonLinkRepository;
import org.agatom.springatom.data.hades.service.NNotificationService;
import org.agatom.springatom.data.hades.service.NPersonService;
import org.agatom.springatom.data.hades.service.NUserService;
import org.agatom.springatom.data.types.person.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.domain.Persistable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.Collection;
import java.util.Queue;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-09-29</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@Service
@Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
class UserDomainService
        extends AbstractDomainService<NUser>
        implements NUserService, AuditorAware<NUser> {
    private static final Logger                      LOGGER                     = LoggerFactory.getLogger(UserDomainService.class);
    @Autowired
    private              PasswordEncoder             passwordEncoder            = null;
    @Autowired
    private              NPersonService              personService              = null;
    @Autowired
    private              NUserToPersonLinkRepository userToPersonLinkRepository = null;
    @Autowired
    private              NUserToRoleRepository       userToRoleRepository       = null;
    @Autowired
    private              NRoleRepository             roleRepository             = null;
    @Autowired
    private              NNotificationService        notificationService        = null;

    @Override
    protected void registerListeners(final ListenerAppender listenerAppender) {
        final Queue<Persistable<Long>> pendingToNotify = Queues.newArrayDeque();
        listenerAppender.add(new PersistenceEventListenerAdapter<NDataInstallationStatus>() {
            @Override
            protected void onAfterCreate(final NDataInstallationStatus entity) {
                final NUser system = repo().findByCredentialsUsername("SYSTEM");
                if (system == null) {
                    pendingToNotify.add(entity);
                } else {
                    notificationService.sendNotification(
                            messageSource.getMessage("user.notify.data.installed", new Object[]{entity.getInstallationPath()}, LocaleContextHolder.getLocale()),
                            system,
                            entity
                    );
                }
            }
        }).add(new PersistenceEventListenerAdapter<NUser>() {
            @Override
            protected void onAfterCreate(final NUser entity) {
                if (entity.getUsername().equalsIgnoreCase("system")) {
                    Persistable<Long> longPersistable;
                    while ((longPersistable = pendingToNotify.poll()) != null) {
                        if (ClassUtils.isAssignableValue(NDataInstallationStatus.class, longPersistable)) {
                            final NDataInstallationStatus status = (NDataInstallationStatus) longPersistable;
                            notificationService.sendNotification(
                                    messageSource.getMessage("user.notify.data.installed", new Object[]{status.getInstallationPath()}, LocaleContextHolder.getLocale()),
                                    entity,
                                    longPersistable
                            );
                        }
                    }
                }
            }
        });
    }

    private NUserRepository repo() {
        return (NUserRepository) this.repository;
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRES_NEW, rollbackFor = UsernameNotFoundException.class)
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final NUser user = this.repo().findByCredentialsUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User with userName=%s not found", username));
        }

        Collection<UserToRoleLink> roles = this.userToRoleRepository.findByUser(user);
        if (roles == null) {
            roles = Lists.newArrayList();
        }

        final Collection<GrantedAuthority> grantedAuthorities = FluentIterable.from(roles).transform(new Function<UserToRoleLink, GrantedAuthority>() {
            @Nullable
            @Override
            public GrantedAuthority apply(final UserToRoleLink input) {
                final NRole role = input.getRole();
                final String authority = role.getAuthority();
                if (LOGGER.isTraceEnabled()) {
                    LOGGER.trace("Transforming {} to {} with authority = {}", new Object[]{ClassUtils.getShortName(NRole.class), ClassUtils.getShortName(SimpleGrantedAuthority.class), authority});
                }
                return new SimpleGrantedAuthority(authority);
            }
        }).toSet();

        return new User(user.getUsername(), user.getPassword(), user.isEnabled(), user.isAccountNonExpired(), user.isCredentialsNonExpired(), user.isAccountNonLocked(), grantedAuthorities);
    }

    @Override
    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
    public NUser registerNewUser(@UserName final String userName, @Password final String password, final long personId) throws Exception {
        final NUser user = new NUser().setUsername(userName).setPassword(password);
        final NPerson person = this.personService.findOne(personId);
        return this.registerNewUser(user, person);
    }

    @Override
    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
    public NUser registerNewUser(@ValidUser final NUser user) throws Exception {
        return this.registerNewUser(user, null);
    }

    @Override
    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
    public <P extends Person> NUser registerNewUser(@ValidUser NUser user, P rawPerson) throws Exception {
        return this.registerNewUser(user, rawPerson, null);
    }

    @Override
    @Transactional(readOnly = false, isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public <P extends Person> NUser registerNewUser(@ValidUser NUser user, final P rawPerson, final Collection<? extends GrantedAuthority> authorities) throws Exception {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("registerNewUser({},{},{})", new Object[]{user, rawPerson, authorities});
        }
        boolean authoritiesEmpty = CollectionUtils.isEmpty(authorities);

        // user logic
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        if (authoritiesEmpty) {
            LOGGER.trace(String.format("%s has no authorities, his account will be disabled", user));
            user.setEnabled(false);
        }

        // save user for further associations
        user = super.save(user);

        LOGGER.info("{} has been registered", user.getUsername());

        // if person not null associate with user
        if (rawPerson != null) {
            final NPerson person = this.getOrCreatePerson((NPerson) rawPerson);
            Assert.isTrue(!person.isNew(), String.format("Person=%s should be persisted", person));
            final NUserToPersonLink link = this.userToPersonLinkRepository.save(new NUserToPersonLink().setPerson(person).setUser(user));
            user = link.getUser();
        }

        // if authorities not empty/null create links
        if (!authoritiesEmpty) {
            final NUser localUser = user;
            final Collection<UserToRoleLink> set = FluentIterable.from(authorities).transform(new Function<GrantedAuthority, UserToRoleLink>() {
                @Nullable
                @Override
                public UserToRoleLink apply(@Nullable final GrantedAuthority input) {
                    assert input != null;
                    final NRole role = getRole(input.getAuthority());
                    return new UserToRoleLink(localUser, role);
                }
            }).toSet();
            this.userToRoleRepository.save(set);
        }

        return user;
    }

    @Override
    public NUser getAuthenticatedUser() {
        final UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) this.getAuthenticatedPrincipal();
        if (!authentication.isAuthenticated()) {
            throw new SessionAuthenticationException(String.format("Authentication\n\t[%s]\nis not authenticated", authentication));
        }
        return this.repo().findByCredentialsUsername(authentication.getName());
    }

    @Override
    public Principal getAuthenticatedPrincipal() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public NUser getAdministrator() {
        return this.repo().findByCredentialsUsername("SYSTEM");
    }

    @Override
    public Iterable<NRole> getAllRoles() {
        return this.roleRepository.findAll();
    }

    @Override
    public NRole getRole(@NotNull final String authority) {
        return this.roleRepository.findByAuthority(authority);
    }

    @Override
    public NUser loadUserByEmail(@NotNull final String email) {
        final NUser user = this.repo().findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User with email=%s not found", email));
        }
        return user;
    }

    private NPerson getOrCreatePerson(final NPerson person) throws Exception {
        if (person.isNew()) {
            return this.personService.save(person);
        }
        this.checkIfPersonAlreadyAssignedToUser(person);
        return this.personService.findOne(person.getId());
    }

    private void checkIfPersonAlreadyAssignedToUser(final NPerson person) throws Exception {
        final NUserToPersonLink userToPersonLink = this.userToPersonLinkRepository.findByPerson(person);
        final NUser one = userToPersonLink != null ? userToPersonLink.getRoleA() : null;
        if (one != null) {
            throw new Exception(String.format("%s already has user associated with it", person));
        }
    }

    @Override
    public NPerson getPerson(final NUser user) {
        final NUserToPersonLink member = this.userToPersonLinkRepository.findByUser(user);
        return member != null ? member.getPerson() : null;
    }

    @Override
    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
    public NUser save(final NUser persistable) {
        try {
            return this.registerNewUser(persistable);
        } catch (Exception exp) {
            LOGGER.error("Failed to save user instance, registration failed", exp);
        }
        return null;
    }

    /**
     * Method retrieves current auditor. Works
     * in following fashion.
     * <ol>
     * <li>Try to locate currently authenticated user</li>
     * <li>If failed, try to locate system administrator</li>
     * <li>Otherwise return boot, as it may be boot operation</li>
     * </ol>
     *
     * @return current auditor
     */
    @Override
    public NUser getCurrentAuditor() {
        NUser user = null;
        try {
            user = this.getAuthenticatedUser();
        } catch (Exception exp) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(String.format("No user is currently authenticated"));
            }
        }
        if (user == null) {
            try {
                user = this.getAdministrator();
            } catch (Exception exp) {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug(String.format("Administrator has not been loaded so far, most like boot.load.data operation"));
                }
            }
        }
        return user;
    }
}
