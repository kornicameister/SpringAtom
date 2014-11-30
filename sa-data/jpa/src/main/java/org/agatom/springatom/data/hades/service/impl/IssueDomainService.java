package org.agatom.springatom.data.hades.service.impl;

import org.agatom.springatom.data.hades.model.issue.NIssue;
import org.agatom.springatom.data.hades.model.user.NUser;
import org.agatom.springatom.data.hades.repo.repositories.issue.NIssueRepository;
import org.agatom.springatom.data.hades.service.NIssueService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;

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
class IssueDomainService
        extends AbstractDomainService<NIssue>
        implements NIssueService {

    private NIssueRepository localRepository = null;

    @PostConstruct
    private void initRepo() {
        this.localRepository = (NIssueRepository) this.repository;
    }

    @Override
    public Iterable<NIssue> findForAssignee(@NotNull final NUser owner) {
        return this.localRepository.findByAssignee(owner);
    }

}
