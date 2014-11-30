package org.agatom.springatom.cmp.component.infopages.provider;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import org.agatom.springatom.cmp.component.infopages.InfoPageNotFoundException;
import org.agatom.springatom.cmp.component.infopages.mapping.InfoPageMapping;
import org.agatom.springatom.cmp.component.infopages.provider.structure.InfoPage;
import org.agatom.springatom.data.hades.model.component.NInfoPageData;
import org.agatom.springatom.data.hades.model.component.QNInfoPageData;
import org.agatom.springatom.data.hades.repo.repositories.component.NInfoPageDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Role;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Persistable;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-10-17</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@Primary
@Component
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Scope(BeanDefinition.SCOPE_SINGLETON)
@Description("InfoPageProviderService implementation designed to load InfoPages from JPA")
class JPAInfoPageProviderService
        extends AbstractInfoPageProvider
        implements InfoPageProviderService {
    private static final Logger                  LOGGER     = LoggerFactory.getLogger(JPAInfoPageProviderService.class);
    @Autowired
    private              NInfoPageDataRepository repository = null;

    @Override
    public <T extends Persistable<?>> InfoPage getInfoPage(final Class<T> persistableClass) throws Exception {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("getInfoPage(persistableClass=%s)", persistableClass));
        }
        final QNInfoPageData data = QNInfoPageData.nInfoPageData;
        final NInfoPageData one = this.repository.findOne(data.pageDomain.eq(persistableClass));
        if (one == null) {
            throw new InfoPageNotFoundException(persistableClass);
        }
        final InfoPage infoPage = (InfoPage) one.getPageObject();
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("For %s retrieved infoPage=%s", persistableClass, infoPage));
        }
        return infoPage;
    }

    @Override
    public Collection<InfoPageMapping> getRegisteredInfoPages() {
        final List<NInfoPageData> all = this.repository.findAll();
        return FluentIterable.from(all).transform(new Function<NInfoPageData, InfoPageMapping>() {

            @Nullable
            @Override
            @SuppressWarnings("unchecked")
            public InfoPageMapping apply(final NInfoPageData input) {
                final InfoPageMapping mapping = new InfoPageMapping();
                mapping.setType((Class<? extends Persistable<?>>) input.getPageDomain());
                mapping.setRel(input.getName());
                return mapping;
            }

        }).toSet();
    }
}
