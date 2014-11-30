package org.agatom.springatom.cmp.wizards.repository;

import org.agatom.springatom.cmp.wizards.WizardProcessor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-10-02</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@Validated
public interface WizardsRepository {
    @NotNull
    @Cacheable(value = "wizardRepository", unless = "#result==null")
    WizardProcessor get(@NotNull final String key) throws WizardProcessorNotFoundException;
}
