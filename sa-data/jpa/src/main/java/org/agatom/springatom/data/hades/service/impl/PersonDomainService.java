package org.agatom.springatom.data.hades.service.impl;

import org.agatom.springatom.data.hades.model.person.NPerson;
import org.agatom.springatom.data.hades.repo.repositories.person.NPersonRepository;
import org.agatom.springatom.data.hades.service.NPersonService;
import org.springframework.stereotype.Service;

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
class PersonDomainService
        extends AbstractDomainService<NPerson>
        implements NPersonService {

    private NPersonRepository repo() {
        return (NPersonRepository) this.repository;
    }

}
