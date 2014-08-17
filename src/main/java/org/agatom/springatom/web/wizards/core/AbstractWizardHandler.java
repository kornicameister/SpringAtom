/**************************************************************************************************
 * This file is part of [SpringAtom] Copyright [kornicameister@gmail.com][2014]                   *
 *                                                                                                *
 * [SpringAtom] is free software: you can redistribute it and/or modify                           *
 * it under the terms of the GNU General Public License as published by                           *
 * the Free Software Foundation, either version 3 of the License, or                              *
 * (at your option) any later version.                                                            *
 *                                                                                                *
 * [SpringAtom] is distributed in the hope that it will be useful,                                *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of                                 *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the                                  *
 * GNU General Public License for more details.                                                   *
 *                                                                                                *
 * You should have received a copy of the GNU General Public License                              *
 * along with [SpringAtom].  If not, see <http://www.gnu.org/licenses/gpl.html>.                  *
 **************************************************************************************************/

package org.agatom.springatom.web.wizards.core;

import org.agatom.springatom.web.wizards.WizardHandler;
import org.agatom.springatom.web.wizards.data.InitStepData;
import org.agatom.springatom.web.wizards.data.SubmitStepData;
import org.agatom.springatom.web.wizards.data.WizardDescriptor;
import org.agatom.springatom.web.wizards.data.WizardStep;
import org.joor.Reflect;
import org.springframework.core.GenericTypeResolver;

import java.util.Locale;

/**
 * <small>Class is a part of <b>SpringAtom</b> and was created at 2014-08-17</small>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
abstract public class AbstractWizardHandler<T>
        implements WizardHandler<T> {
    protected T                contextObject = null;
    private   Class<?>         contextClazz  = GenericTypeResolver.resolveTypeArgument(getClass(), WizardHandler.class);
    private   WizardDescriptor descriptor    = null;

    @Override
    public final WizardDescriptor initialize(final Locale locale) {
        this.contextObject = Reflect.on(this.contextClazz).get();
        return (this.descriptor = this.getDescriptor(locale));
    }

    @Override
    public final InitStepData initStep(final String step) {
        return (this.getStepInit(this.descriptor.getStep(step)));
    }

    @Override
    public SubmitStepData submitStep(final String step) {
        return (this.getStepSubmit(this.descriptor.getStep(step)));
    }

    @Override
    public T getContextObject() {
        return this.contextObject;
    }

    protected abstract SubmitStepData getStepSubmit(final WizardStep step);

    protected abstract InitStepData getStepInit(final WizardStep step);

    protected abstract WizardDescriptor getDescriptor(final Locale locale);
}
