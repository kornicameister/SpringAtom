package org.agatom.springatom.data.hades.repo;

import org.joda.time.DateTime;
import org.springframework.data.envers.repository.support.EnversRevisionRepository;
import org.springframework.data.history.Revision;
import org.springframework.data.history.Revisions;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-10-09</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
public interface NVersionedRepository<T>
        extends NRepository<T>, EnversRevisionRepository<T, Long, Long> {

    /**
     * {@code findInRevision} returns {@link org.springframework.data.history.Revision} of the target underlying target entity in the given revision
     * described in {@code revision} param
     *
     * @param id       id of the entity {@link org.springframework.data.domain.Persistable#getId()}
     * @param revision the revision number
     *
     * @return {@link org.springframework.data.history.Revision} for passed arguments
     *
     * @see NVersionedRepository#findInRevisions(Long, Long...)
     */
    Revision<Long, T> findInRevision(final Long id, final Long revision);

    /**
     * {@code findInRevisions} does exactly the same job but for multiple possible {@code revisions}.
     *
     * @param id        id of the entity {@link org.springframework.data.domain.Persistable#getId()}
     * @param revisions varargs with revision numbers
     *
     * @return {@link org.springframework.data.history.Revisions}
     */
    Revisions<Long, T> findInRevisions(final Long id, final Long... revisions);

    /**
     * <p>findRevisions.</p>
     *
     * @param id       a ID object.
     * @param dateTime a {@link org.joda.time.DateTime} object.
     * @param before   a Operators object.
     *
     * @return a {@link org.springframework.data.history.Revisions} object.
     */
    Revisions<Long, T> findRevisions(final Long id, final DateTime dateTime, final Operators before);

    /**
     * Returns how many revisions exists for given {@link org.springframework.data.domain.Persistable#getId()} instance
     *
     * @param id the id
     *
     * @return revisions amount
     */
    long countRevisions(Long id);

    /**
     * Custom operators to be used when constructing the queries
     */
    public static enum Operators {
        BEFORE,
        AFTER,
        EQ
    }
}
