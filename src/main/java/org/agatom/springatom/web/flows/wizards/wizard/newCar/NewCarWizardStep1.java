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

package org.agatom.springatom.web.flows.wizards.wizard.newCar;

import org.agatom.springatom.server.model.beans.car.QSCar;
import org.agatom.springatom.server.model.beans.car.SCar;
import org.agatom.springatom.server.service.vinNumber.decoder.VinDecoder;
import org.agatom.springatom.server.service.vinNumber.model.VinNumberData;
import org.agatom.springatom.web.flows.wizards.actions.WizardAction;
import org.agatom.springatom.web.flows.wizards.wizard.WizardFormAction;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.util.Assert;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.webflow.core.collection.MutableAttributeMap;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

/**
 * <small>Class is a part of <b>SpringAtom</b> and was created at 06.04.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@WizardAction("newCarWizard1")
public class NewCarWizardStep1
		extends WizardFormAction<SCar> {
	private static final Logger   LOGGER           = Logger.getLogger(NewCarWizardStep1.class);
	private static final String   FORM_OBJECT_NAME = "car";
	private static final String   VIN_NUMBER_DATA  = "vinNumberData";
	private static final String[] REQUIRED_FIELDS  = new String[]{
			QSCar.sCar.vinNumber.getMetadata().getName()
	};
	@Autowired
	private              VinDecoder vinDecoder       = null;

	public NewCarWizardStep1() {
		super();
		this.setFormObjectName(FORM_OBJECT_NAME);
	}

	public SCar getNewCar(final RequestContext context) throws Exception {
		return this.getCommandBean(context);
	}

	@Override
	protected WebDataBinder doInitBinder(final WebDataBinder binder, final FormattingConversionService conversionService) {
		binder.setRequiredFields(REQUIRED_FIELDS);
		binder.setAllowedFields(REQUIRED_FIELDS);
		binder.setIgnoreUnknownFields(true);
		return super.doInitBinder(binder, conversionService);
	}

	@Override
	public Event bindAndValidate(final RequestContext context) throws Exception {
		final Event event = super.bindAndValidate(context);
		if (this.isSuccessEvent(event)) {
			final MutableAttributeMap<Object> flowScope = context.getFlowScope();

			LOGGER.trace(String.format("VinDecoding in progress.."));

			SCar car = this.getCommandBean(context);
			try {
				final VinNumberData vinNumberData = this.vinDecoder.decode(car.getVinNumber());
				Assert.notNull(vinNumberData);
				flowScope.put(VIN_NUMBER_DATA, vinNumberData);
			} catch (Exception exp) {
				return error(exp);
			}
			LOGGER.trace(String.format("VinDecoding is over..."));
		}
		return event;
	}

}
