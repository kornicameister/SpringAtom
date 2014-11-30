package org.agatom.springatom.data.services.enumeration;

import org.agatom.springatom.data.services.SDomainService;
import org.agatom.springatom.data.types.enumeration.Enumeration;
import org.agatom.springatom.data.types.enumeration.EnumerationEntry;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Persistable;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.InputStream;
import java.util.Collection;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-09-29</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@Validated
public interface SEnumerationService<T extends Enumeration<EE> & Persistable<Long>, EE extends EnumerationEntry>
        extends SDomainService<T> {

    String CACHE_NAME = "enumerations";

    @CacheEvict(value = CACHE_NAME, allEntries = true, beforeInvocation = false)
    @Transactional(rollbackFor = EnumerationServiceException.class)
    T getEnumeration(final String name) throws EnumerationServiceException;

    @CacheEvict(value = CACHE_NAME, allEntries = true, beforeInvocation = false)
    @Transactional(rollbackFor = EnumerationServiceException.class)
    T newEnumeration(final String name, final File file) throws EnumerationServiceException;

    @CacheEvict(value = CACHE_NAME, allEntries = true, beforeInvocation = false)
    @Transactional(rollbackFor = EnumerationServiceException.class)
    T newEnumeration(final String name, final InputStream stream) throws EnumerationServiceException;

    @CacheEvict(value = CACHE_NAME, allEntries = true, beforeInvocation = false)
    @Transactional(rollbackFor = EnumerationServiceException.class)
    T newEnumeration(final String name, final Collection<EE> entries) throws EnumerationServiceException;

    @NotNull
    @CacheEvict(value = CACHE_NAME, allEntries = true, beforeInvocation = false)
    @Transactional(rollbackFor = EnumerationServiceException.class)
    Iterable<T> newEnumerations(@NotNull final File stream) throws EnumerationServiceException;

    @NotNull
    @CacheEvict(value = CACHE_NAME, allEntries = true, beforeInvocation = false)
    @Transactional(rollbackFor = EnumerationServiceException.class)
    Iterable<T> newEnumerations(@NotNull final InputStream stream) throws EnumerationServiceException;

    @Cacheable(value = CACHE_NAME)
    @Transactional(readOnly = true, rollbackFor = EnumerationServiceException.class, isolation = Isolation.READ_COMMITTED)
    EE getEnumerationEntry(String name, String entryKey) throws EnumerationServiceException;

    /**
     * Looks for {@link org.agatom.springatom.data.types.enumeration.Enumeration} by given {@code name} and if found
     * tries to resolve {@link org.agatom.springatom.data.types.enumeration.EnumerationEntry} for {@code entryKey}.
     * This method works similar to {@link #getEnumerationEntry(String, String)}. Difference is that it returns
     * a {@link org.agatom.springatom.data.types.enumeration.EnumerationEntry#getKey()}
     *
     * @param name     {@link org.agatom.springatom.data.types.enumeration.Enumeration#getName()}
     * @param entryKey {@link org.agatom.springatom.data.types.enumeration.EnumerationEntry#getKey()}
     *
     * @return the value
     *
     * @throws EnumerationServiceException
     */
    @Cacheable(value = CACHE_NAME)
    @Transactional(readOnly = true, rollbackFor = EnumerationServiceException.class, isolation = Isolation.READ_COMMITTED)
    String getEnumeratedValue(@NotNull String name, @NotNull String entryKey) throws EnumerationServiceException;

}
