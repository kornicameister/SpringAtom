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

package org.agatom.springatom.server.repository.factory;

import org.agatom.springatom.server.model.beans.revision.AuditedRevisionEntity;
import org.agatom.springatom.server.repository.SBasicRepository;
import org.agatom.springatom.server.repository.SRepository;
import org.agatom.springatom.server.repository.impl.SBasicRepositoryImpl;
import org.agatom.springatom.server.repository.impl.SRepositoryImpl;
import org.apache.log4j.Logger;
import org.hibernate.envers.DefaultRevisionEntity;
import org.springframework.data.envers.repository.support.ReflectionRevisionEntityInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.*;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.data.repository.history.support.RevisionEntityInformation;
import org.springframework.util.ClassUtils;

import javax.persistence.EntityManager;
import java.io.Serializable;

/**
 * {@code SRepositoriesFactoryBean} creates instances of either:
 * <ol>
 * <li>{@link org.agatom.springatom.server.repository.SBasicRepository}</li>
 * <li>{@link org.agatom.springatom.server.repository.SRepository}</li>
 * </ol>
 *
 * @author kornicameister
 * @version 0.0.2
 * @since 0.0.1
 */
public class SRepositoriesFactoryBean<T extends SBasicRepository<S, ID>, S, ID extends Serializable>
        extends JpaRepositoryFactoryBean<T, S, ID> {

    private Class<?> revisionEntityClass;

    public void setRevisionEntityClass(Class<?> revisionEntityClass) {
        this.revisionEntityClass = revisionEntityClass;
    }

    @Override
    protected RepositoryFactorySupport createRepositoryFactory(final EntityManager entityManager) {
        return new SRepositoryFactory(entityManager, this.revisionEntityClass);
    }

    private static class SRepositoryFactory
            extends JpaRepositoryFactory {
        private static final Logger LOGGER = Logger.getLogger(SRepositoryFactory.class);
        private final Class<?>                        revisionEntityClass;
        private final RevisionEntityInformation       revisionEntityInformation;
        private final LockModeRepositoryPostProcessor lockModePostProcessor;

        public SRepositoryFactory(final EntityManager entityManager,
                                  final Class<?> revisionEntityClass) {
            super(entityManager);
            this.lockModePostProcessor = LockModeRepositoryPostProcessor.INSTANCE;

            this.revisionEntityClass = revisionEntityClass == null ? AuditedRevisionEntity.class : revisionEntityClass;
            this.revisionEntityInformation = DefaultRevisionEntity.class
                    .equals(revisionEntityClass) ? new AuditingRevisionEntityInformation() : new ReflectionRevisionEntityInformation(this.revisionEntityClass);

            if (LOGGER.isTraceEnabled()) {
                LOGGER.trace(String.format("Created %s with arguments=[em=%s,rec=%s,rei=%s]",
                        SRepositoryFactory.class.getSimpleName(),
                        entityManager,
                        this.revisionEntityClass,
                        this.revisionEntityInformation));
            }
        }

        @Override
        @SuppressWarnings("unchecked")
        protected <T, ID extends Serializable> JpaRepository<?, ?> getTargetRepository(final RepositoryMetadata metadata, final EntityManager entityManager) {
            final JpaEntityInformation<T, Serializable> entityInformation = (JpaEntityInformation<T, Serializable>) getEntityInformation(metadata
                    .getDomainType());
            final Class<?> repositoryInterface = metadata.getRepositoryInterface();
            SimpleJpaRepository<T, ID> repository;
            if (!ClassUtils.isAssignable(SRepository.class, repositoryInterface)) {
                repository = (SimpleJpaRepository<T, ID>) new SBasicRepositoryImpl<>(entityInformation, entityManager);
            } else {
                repository = (SimpleJpaRepository<T, ID>) new SRepositoryImpl<>(entityInformation, revisionEntityInformation, entityManager);
            }
            repository.setLockMetadataProvider(this.lockModePostProcessor.getLockMetadataProvider());
            return repository;
        }

        @Override
        protected Class<?> getRepositoryBaseClass(final RepositoryMetadata metadata) {
            final Class<?> repositoryInterface = metadata.getRepositoryInterface();
            if (!SRepository.class.isAssignableFrom(repositoryInterface)) {
                return SBasicRepositoryImpl.class;
            }
            return SRepositoryImpl.class;
        }
    }
}
