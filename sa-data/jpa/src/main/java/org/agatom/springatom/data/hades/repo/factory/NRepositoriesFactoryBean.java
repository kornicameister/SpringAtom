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

package org.agatom.springatom.data.hades.repo.factory;

import org.agatom.springatom.data.hades.model.revision.AuditedRevisionEntity;
import org.agatom.springatom.data.hades.repo.NRepository;
import org.agatom.springatom.data.hades.repo.NVersionedRepository;
import org.hibernate.envers.DefaultRevisionEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.data.envers.repository.support.ReflectionRevisionEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.data.repository.history.support.RevisionEntityInformation;
import org.springframework.util.ClassUtils;

import javax.persistence.EntityManager;
import java.io.Serializable;


public class NRepositoriesFactoryBean<T extends NRepository<S>, S>
        extends JpaRepositoryFactoryBean<T, S, Long>
        implements ApplicationEventPublisherAware {
    private Class<?>                  revisionEntityClass = null;
    private ApplicationEventPublisher eventPublisher      = null;

    /**
     * <p>Setter for the field <code>revisionEntityClass</code>.</p>
     *
     * @param revisionEntityClass a {@link Class} object.
     */
    public void setRevisionEntityClass(Class<?> revisionEntityClass) {
        this.revisionEntityClass = revisionEntityClass;
    }

    /** {@inheritDoc} */
    @Override
    protected RepositoryFactorySupport createRepositoryFactory(final EntityManager entityManager) {
        return new SRepositoryFactory(entityManager, this.revisionEntityClass, this.eventPublisher);
    }

    @Override
    public void setApplicationEventPublisher(final ApplicationEventPublisher applicationEventPublisher) {
        this.eventPublisher = applicationEventPublisher;
    }

    private static class SRepositoryFactory
            extends JpaRepositoryFactory {
        private static final Logger LOGGER = LoggerFactory.getLogger(SRepositoryFactory.class);
        private final Class<?>                  revisionEntityClass;
        private final RevisionEntityInformation revisionEntityInformation;
        private final ApplicationEventPublisher eventPublisher;

        public SRepositoryFactory(final EntityManager entityManager,
                                  final Class<?> revisionEntityClass,
                                  final ApplicationEventPublisher eventPublisher) {
            super(entityManager);

            this.revisionEntityClass = revisionEntityClass == null ? AuditedRevisionEntity.class : revisionEntityClass;
            this.revisionEntityInformation = DefaultRevisionEntity.class
                    .equals(revisionEntityClass)
                    ? new AuditingRevisionEntityInformation()
                    : new ReflectionRevisionEntityInformation(this.revisionEntityClass);

            this.eventPublisher = eventPublisher;

            if (LOGGER.isTraceEnabled()) {
                LOGGER.trace(String.format("Created %s with arguments=[em=%s,rec=%s,rei=%s]",
                        SRepositoryFactory.class.getSimpleName(),
                        entityManager,
                        this.revisionEntityClass,
                        this.revisionEntityInformation));
            }
        }

        @Override
        @SuppressWarnings({"unchecked", "UnusedDeclaration"})
        protected <T, ID extends Serializable> SimpleJpaRepository<?, ?> getTargetRepository(
                final RepositoryMetadata metadata,
                final EntityManager entityManager) {
            final JpaEntityInformation<T, Serializable> entityInformation =
                    (JpaEntityInformation<T, Serializable>) getEntityInformation(metadata
                            .getDomainType());
            final Class<?> repositoryInterface = metadata.getRepositoryInterface();
            SimpleJpaRepository<?, ?> repository;
            if (!ClassUtils.isAssignable(NRepository.class, repositoryInterface)) {
                repository = new NRepositoryImpl(entityInformation, entityManager);
            } else {
                repository = new NVersionedRepositoryImpl(entityInformation, revisionEntityInformation, entityManager);
            }
            ((NRepositoryImpl) repository).setApplicationEventPublisher(this.eventPublisher);
            return repository;
        }

        @Override
        protected Class<?> getRepositoryBaseClass(final RepositoryMetadata metadata) {
            final Class<?> repositoryInterface = metadata.getRepositoryInterface();
            if (!NRepository.class.isAssignableFrom(repositoryInterface)) {
                return NRepositoryImpl.class;
            }
            return NVersionedRepository.class;
        }
    }
}
