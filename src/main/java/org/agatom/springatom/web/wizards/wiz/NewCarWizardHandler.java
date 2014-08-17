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

package org.agatom.springatom.web.wizards.wiz;

import org.agatom.springatom.server.model.beans.car.SCar;
import org.agatom.springatom.server.service.domain.SCarMasterService;
import org.agatom.springatom.server.service.domain.SCarService;
import org.agatom.springatom.web.locale.SMessageSource;
import org.agatom.springatom.web.wizards.Wizard;
import org.agatom.springatom.web.wizards.core.AbstractWizardHandler;
import org.agatom.springatom.web.wizards.data.InitStepData;
import org.agatom.springatom.web.wizards.data.SubmitStepData;
import org.agatom.springatom.web.wizards.data.WizardDescriptor;
import org.agatom.springatom.web.wizards.data.WizardStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ClassUtils;

import java.util.Locale;

/**
 * <small>Class is a part of <b>SpringAtom</b> and was created at 2014-08-17</small>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@Wizard("newCar")
public class NewCarWizardHandler
        extends AbstractWizardHandler<SCar> {

    @Autowired
    private SCarService       carService       = null;
    @Autowired
    private SCarMasterService carMasterService = null;
    @Autowired
    private SMessageSource    messageSource    = null;

    @Override
    protected SubmitStepData getStepSubmit(final WizardStep step) {
        return null;
    }

    @Override
    protected InitStepData getStepInit(final WizardStep step) {
        return null;
    }

    @Override
    protected WizardDescriptor getDescriptor(final Locale locale) {
        final WizardDescriptor descriptor = new WizardDescriptor();

        descriptor.setLabel(this.messageSource.getMessage("scar", locale));
        descriptor.addStep((WizardStep) new WizardStep()
                .setIndex((short) 0)
                .setStep("vin")
                .setRequired(true)
                .addLabel("vinNumber", this.messageSource.getMessage("scar.vinnumber", locale))
                .setLabel(this.messageSource.getMessage("wizard.NewCarWizard.setupVin.desc", locale)));
        descriptor.addStep((WizardStep) new WizardStep()
                .setIndex((short) 1)
                .setStep("car")
                .setRequired(true)
                .addLabel("brand", this.messageSource.getMessage("scarmastermanufacturingdata.brand", locale))
                .addLabel("model", this.messageSource.getMessage("scarmastermanufacturingdata.model", locale))
                .addLabel("carMaster", this.messageSource.getMessage("scar.carmaster", locale))
                .addLabel("licencePlate", this.messageSource.getMessage("scar.licenceplate", locale))
                .addLabel("fuelType", this.messageSource.getMessage("scar.fueltype", locale))
                .addLabel("year", this.messageSource.getMessage("scar.yearofproduction", locale))
                .addLabel("newBrandModel", this.messageSource.getMessage("scarmastermanufacturingdata.newBrandAndModel", locale))
                .setLabel(this.messageSource.getMessage("wizard.NewCarWizard.setupCar.desc", locale)));
        descriptor.addStep((WizardStep) new WizardStep()
                .setIndex((short) 2)
                .setStep("owner")
                .setRequired(true)
                .addLabel("owner", this.messageSource.getMessage("scar.owner", locale))
                .setLabel(this.messageSource.getMessage("wizard.NewCarWizard.setupOwner.desc", locale)));

        descriptor.addDynamicProperty("handler", ClassUtils.getShortName(this.getClass()));

        return descriptor;
    }

    @Override
    public SCar submit() {
        return null;
    }
}
