package org.agatom.springatom.data.services;

import org.agatom.springatom.data.support.rupdate.RecentUpdateBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-11-25</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
public interface SRecentlyUpdatedService {

    Collection<RecentUpdateBean> getRecentlyUpdated();

    Page<RecentUpdateBean> getRecentlyUpdated(final Pageable pageable);

}
