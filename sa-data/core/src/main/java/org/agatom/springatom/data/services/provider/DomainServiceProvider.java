package org.agatom.springatom.data.services.provider;

import org.agatom.springatom.data.services.SDomainService;
import org.springframework.data.domain.Persistable;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-09-28</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
public interface DomainServiceProvider {
    <T extends Persistable<Long>> SDomainService<T> getServiceFor(final Class<T> clazz);
}
