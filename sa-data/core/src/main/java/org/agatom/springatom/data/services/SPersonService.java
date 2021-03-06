package org.agatom.springatom.data.services;

import org.agatom.springatom.data.types.person.Person;
import org.springframework.data.domain.Persistable;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-09-29</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
public interface SPersonService<T extends Person & Persistable<Long>>
        extends SDomainService<T> {

}
