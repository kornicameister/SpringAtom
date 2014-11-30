package org.agatom.springatom.cmp.component.infopages.persister;

import org.agatom.springatom.cmp.component.infopages.provider.structure.InfoPage;
import org.agatom.springatom.data.hades.model.component.NInfoPageData;
import org.agatom.springatom.data.hades.repo.repositories.component.NInfoPageDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-10-15</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@Service
class InfoPagePersistHandlerImpl
        implements InfoPagePersistHandler {

    @Autowired
    private NInfoPageDataRepository pageDataRepository = null;

    @Override
    public void saveToPersistentStorage(final InfoPage infoPage) {
        final NInfoPageData pageData = new NInfoPageData();

        pageData.setPageId(infoPage.getId());
        pageData.setPageName(infoPage.getRel());
        pageData.setPageVersion(infoPage.getVersion());
        pageData.setPageObject(infoPage);
        pageData.setPageDomain(infoPage.getDomain());

        this.pageDataRepository.save(pageData);
    }
}
