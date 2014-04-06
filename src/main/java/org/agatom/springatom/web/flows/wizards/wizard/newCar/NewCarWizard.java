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

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.agatom.springatom.server.model.beans.car.QSCar;
import org.agatom.springatom.server.model.beans.car.SCar;
import org.agatom.springatom.server.model.beans.car.SCarMaster;
import org.agatom.springatom.server.service.domain.SCarMasterService;
import org.agatom.springatom.server.service.domain.SCarService;
import org.agatom.springatom.web.flows.wizards.actions.WizardAction;
import org.agatom.springatom.web.flows.wizards.wizard.WizardFormAction;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.webflow.core.collection.MutableAttributeMap;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.*;

/**
 * <small>Class is a part of <b>SpringAtom</b> and was created at 06.04.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@WizardAction("newCarWizard")
public class NewCarWizard
		extends WizardFormAction<SCar> {
	private static final Logger             LOGGER           = Logger.getLogger(NewCarWizard.class);
	private static final String             FORM_OBJECT_NAME = "car";
	private static final String             OWNERS_PARAM_KEY = "owners";
	private static final Comparator<String> COMPARATOR       = new Comparator<String>() {
		@Override
		public int compare(final String o1, final String o2) {
			return o1.compareTo(o2);
		}
	};
	private static final String             BRANDS           = "brands";
	private static final String             MODELS           = "models";
	private static final String[]           REQUIRED_FIELDS  = new String[]{
			QSCar.sCar.carMaster.manufacturingData.brand.getMetadata().getName(),
			QSCar.sCar.carMaster.manufacturingData.model.getMetadata().getName(),
			QSCar.sCar.vinNumber.getMetadata().getName(),
			QSCar.sCar.licencePlate.getMetadata().getName(),
			QSCar.sCar.owner.getMetadata().getName()
	};
	@Autowired
	private              SCarService        carService       = null;
	@Autowired
	private              SCarMasterService  carMasterService = null;

	public NewCarWizard() {
		super();
		this.setFormObjectName(FORM_OBJECT_NAME);
	}

	public SCar getNewCar(final RequestContext context) throws Exception {
		return this.getCommandBean(context);
	}

	@Override
	protected WebDataBinder doInitBinder(final WebDataBinder binder, final FormattingConversionService conversionService) {
		binder.setRequiredFields(REQUIRED_FIELDS);
		return super.doInitBinder(binder, conversionService);
	}

	@Override
	public Event setupForm(final RequestContext context) throws Exception {
		final Event event = super.setupForm(context);
		try {
			if (this.isSuccessEvent(event)) {
				final SCar commandBean = this.getCommandBean(context);
				commandBean.setCarMaster(new SCarMaster());
			}
			final MutableAttributeMap<Object> viewScope = context.getViewScope();

			viewScope.put(OWNERS_PARAM_KEY, this.convertToView(this.carService.getOwners()));

			final List<SCarMaster> all = this.carMasterService.findAll();
			viewScope.put(BRANDS, this.extractBrands(all));
			viewScope.put(MODELS, this.extractModels(all));

		} catch (Exception exp) {
			LOGGER.fatal(String.format("setupForm(context=%s) failed", context), exp);
			return error(exp);
		}
		return event;
	}

	private List<OwnerBean> convertToView(final Collection<SCarService.Owner> owners) {
		return FluentIterable
				.from(owners)
				.transform(new Function<SCarService.Owner, OwnerBean>() {
					@Nullable
					@Override
					public OwnerBean apply(@Nullable final SCarService.Owner input) {
						if (input == null) {
							return null;
						}
						final OwnerBean ownerBean = new OwnerBean().setOwnerId(input.getOwner().getId()).setOwnerIdentity(input.getOwner().getPerson().getIdentity());
						final List<Map<String, Object>> maps = Lists.newArrayListWithExpectedSize(input.getCars().size());
						for (SCar car : input.getCars()) {
							final Map<String, Object> map = Maps.newHashMapWithExpectedSize(2);
							map.put("licencePlate", car.getLicencePlate());
							map.put("brandModel", car.getCarMaster().getManufacturingData().getIdentity());
							maps.add(map);
						}
						return ownerBean.setCarsMap(maps);
					}
				})
				.toList();
	}

	private Set<String> extractBrands(final List<SCarMaster> all) {
		return FluentIterable.from(all).transform(new Function<SCarMaster, String>() {
			@Nullable
			@Override
			public String apply(@Nullable final SCarMaster input) {
				assert input != null;
				return input.getBrand();
			}
		}).toSortedSet(COMPARATOR);
	}

	private Set<String> extractModels(final List<SCarMaster> all) {
		return FluentIterable.from(all).transform(new Function<SCarMaster, String>() {
			@Nullable
			@Override
			public String apply(@Nullable final SCarMaster input) {
				assert input != null;
				return input.getModel();
			}
		}).toSortedSet(COMPARATOR);
	}

	@Override
	public Event bindAndValidate(final RequestContext context) throws Exception {
		final Event event = super.bindAndValidate(context);
		if (this.isSuccessEvent(event)) {
			SCar car = this.getCommandBean(context);
			try {
				// to enable all validation to be called
				car = this.carService.newCar(
						StringUtils.capitalize(car.getBrand()),
						StringUtils.capitalize(car.getModel()),
						car.getLicencePlate().toUpperCase(),
						car.getVinNumber().toUpperCase(),
						car.getOwner().getId()
				);
				context.getFlowScope().put(FORM_OBJECT_NAME, car);
			} catch (Exception exp) {
				final MessageContext messageContext = context.getMessageContext();
				messageContext.addMessage(
						new MessageBuilder()
								.source(car)
								.error()
								.code("newCar.saveFailed")
								.args(car.getLicencePlate())
								.build()
				);
				return error(exp);
			}
		}
		return event;
	}

	private class OwnerBean implements Serializable {
		private static final long                      serialVersionUID = 1349406708171914877L;
		private              String                    ownerIdentity    = null;
		private              Long                      ownerId          = null;
		private              List<Map<String, Object>> carsMap          = Lists.newArrayList();

		public String getOwnerIdentity() {
			return ownerIdentity;
		}

		public OwnerBean setOwnerIdentity(final String ownerIdentity) {
			this.ownerIdentity = ownerIdentity;
			return this;
		}

		public Long getOwnerId() {
			return ownerId;
		}

		public OwnerBean setOwnerId(final Long ownerId) {
			this.ownerId = ownerId;
			return this;
		}

		public List<Map<String, Object>> getCarsMap() {
			return carsMap;
		}

		public OwnerBean setCarsMap(final List<Map<String, Object>> carsMap) {
			this.carsMap = carsMap;
			return this;
		}
	}

}
