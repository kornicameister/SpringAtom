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

package org.agatom.springatom.web.wizards;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.collect.FluentIterable;
import org.agatom.springatom.cmp.component.ComponentCompilationException;
import org.agatom.springatom.cmp.component.select.SelectComponent;
import org.agatom.springatom.cmp.component.select.SelectOption;
import org.agatom.springatom.cmp.component.select.factory.SelectComponentFactory;
import org.agatom.springatom.cmp.wizards.StepHelper;
import org.agatom.springatom.cmp.wizards.Wizard;
import org.agatom.springatom.cmp.wizards.core.AbstractStepHelper;
import org.agatom.springatom.cmp.wizards.core.CreateObjectWizardProcessor;
import org.agatom.springatom.cmp.wizards.data.WizardDescriptor;
import org.agatom.springatom.cmp.wizards.data.WizardStepDescriptor;
import org.agatom.springatom.cmp.wizards.data.result.FeedbackMessage;
import org.agatom.springatom.cmp.wizards.data.result.WizardDebugDataKeys;
import org.agatom.springatom.cmp.wizards.data.result.WizardResult;
import org.agatom.springatom.core.locale.ms.SMessageSource;
import org.agatom.springatom.data.hades.model.car.NCar;
import org.agatom.springatom.data.hades.model.car.NCarMaster;
import org.agatom.springatom.data.hades.model.car.QNCar;
import org.agatom.springatom.data.hades.model.user.NUser;
import org.agatom.springatom.data.hades.service.NCarMasterService;
import org.agatom.springatom.data.hades.service.NCarService;
import org.agatom.springatom.data.hades.service.NUserService;
import org.agatom.springatom.data.vin.decoder.VinDecoder;
import org.agatom.springatom.data.vin.exception.VinDecodingException;
import org.agatom.springatom.data.vin.model.VinNumberData;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.validation.DataBinder;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * <small>Class is a part of <b>SpringAtom</b> and was created at 2014-08-17</small>
 *
 * @author trebskit
 * @version 0.0.2
 * @since 0.0.1
 */
@Wizard("newCar")
class NewCarWizardProcessor
        extends CreateObjectWizardProcessor<NCar> {
    private static final Logger                 LOGGER                 = LoggerFactory.getLogger(NewCarWizardProcessor.class);
    private static final String                 FORM_OBJECT_NAME       = "car";
    private static final String                 BRANDS                 = "brands";
    private static final String                 MODELS                 = "models";
    private static final String                 CAR_MASTERS            = "carMasters";
    private static final String                 FUEL_TYPES             = "fuelTypes";
    private static final String                 OWNERS_PARAM_KEY       = "owners";
    private static final Comparator<String>     COMPARATOR             = new Comparator<String>() {
        @Override
        public int compare(final String o1, final String o2) {
            return o1.compareTo(o2);
        }
    };
    private              StepsHolder            steps                  = new StepsHolder();
    @Autowired
    private              SMessageSource         messageSource          = null;
    @Autowired
    private              NCarService            carService             = null;
    @Autowired
    private              NCarMasterService      carMasterService       = null;
    @Autowired
    private              NUserService           userService            = null;
    @Autowired
    private              SelectComponentFactory selectComponentFactory = null;
    @Autowired
    private              VinDecoder             vinDecoder             = null;

    @Override
    public String getContextObjectName() {
        return FORM_OBJECT_NAME;
    }

    @Override
    protected StepHelper[] getStepHelpers() {
        return new StepHelper[]{
                this.steps.OWNER,
                this.steps.CAR,
                this.steps.VIN
        };
    }

    @Override
    protected WizardDescriptor initializeWizard(final Locale locale) {
        LOGGER.debug(String.format("initializeWizard(locale=%s)", locale));
        final WizardDescriptor descriptor = new WizardDescriptor();

        descriptor.setLabel(this.messageSource.getMessage("wizard.NewCarWizard.title", locale));
        descriptor.addStep(this.steps.VIN.getStepDescriptor(locale));
        descriptor.addStep(this.steps.CAR.getStepDescriptor(locale));
        descriptor.addStep(this.steps.OWNER.getStepDescriptor(locale));

        return descriptor;
    }

    @Override
    protected NCar getContextObject() throws Exception {
        final NCar car = super.getContextObject();
        car.setCarMaster(new NCarMaster());
        return car;
    }

    @Override
    protected WizardResult submitStep(final NCar contextObject, final ModelMap stepData, final String step, final Locale locale) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("submitStep(contextObject=%s)", contextObject));
        }

        final WizardResult result = new WizardResult();

        if (this.steps.VIN.getStep().equals(step)) {
            final long startTime = System.nanoTime();
            final String vinNumber = contextObject.getVinNumber();

            try {
                final VinNumberData decode = this.vinDecoder.decode(vinNumber);
                result.addWizardData("years", selectComponentFactory
                                .<Integer, Integer, Integer>newSelectComponent()
                                .from(decode.getYears())
                                .usingLabelFunction(Functions.<Integer>identity())
                                .usingValueFunction(Functions.<Integer>identity())
                                .get()
                                .getOptions()
                );
                result.addFormData("manufacturedIn", decode.getManufacturedIn());
                result.addStepData("vinNumberData", decode);

            } catch (VinDecodingException | ComponentCompilationException e) {
                LOGGER.error(String.format("Vin decoding failed, for vinNumber=%s", vinNumber), e);
                result.addError(e);
            }

            final long endTime = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(String.format("submitStep(contextObject=%s) took %d ms", contextObject, endTime));
            }

            result.addDebugData(WizardDebugDataKeys.SUBMISSION_TIME, endTime);
        }

        return result;
    }

    @Override
    protected WizardResult submitWizard(NCar contextObject, final ModelMap stepData, final Locale locale) throws Exception {
        contextObject = this.carService.save(contextObject);

        final WizardResult result = new WizardResult()
                .setWizardId("newCar");

        result.setOid(this.getOID(contextObject));
        result.addFeedbackMessage(
                FeedbackMessage
                        .newInfo()
                        .setMessage(
                                this.messageSource.getMessage(
                                        "sa.msg.objectCreated",
                                        new Object[]{this.messageSource.getMessage("NCar", locale)},
                                        locale
                                )
                        )
        );

        return result;
    }

    private class StepsHolder {

        final QNCar car = QNCar.nCar;

        final StepHelper VIN   = new AbstractStepHelper("vin") {
            @Override
            public WizardStepDescriptor getStepDescriptor(final Locale locale) {
                return (WizardStepDescriptor) super.getStepDescriptor(locale)
                        .setRequired(true)
                        .addLabel("vinNumber", messageSource.getMessage("NCar.vinnumber", locale))
                        .setLabel(messageSource.getMessage("wizard.NewCarWizard.setupVin.desc", locale));
            }

            @Override
            public boolean isValidationEnabled() {
                return true;
            }


            @Override
            public void initializeBinder(final DataBinder binder) {
                final String vinPropertyName = getPropertyName(car.vinNumber);
                binder.setRequiredFields(vinPropertyName);
                binder.setAllowedFields(vinPropertyName);
            }
        };
        final StepHelper CAR   = new AbstractStepHelper("car") {
            final Logger logger = LoggerFactory.getLogger(this.getClass());

            @Override
            public WizardStepDescriptor getStepDescriptor(final Locale locale) {
                return (WizardStepDescriptor) super.getStepDescriptor(locale)
                        .setRequired(true)
                        .addLabel("brand", messageSource.getMessage("scarmastermanufacturingdata.brand", locale))
                        .addLabel("model", messageSource.getMessage("scarmastermanufacturingdata.model", locale))
                        .addLabel("carMaster", messageSource.getMessage("NCar.carmaster", locale))
                        .addLabel("licencePlate", messageSource.getMessage("NCar.licenceplate", locale))
                        .addLabel("fuelType", messageSource.getMessage("NCar.fueltype", locale))
                        .addLabel("year", messageSource.getMessage("NCar.yearofproduction", locale))
                        .addLabel("newBrandModel", messageSource.getMessage("scarmastermanufacturingdata.newBrandAndModel", locale))
                        .setLabel(messageSource.getMessage("wizard.NewCarWizard.setupCar.desc", locale));
            }

            @Override
            public void initializeBinder(final DataBinder binder) {
                binder.setRequiredFields(this.getRequiredFields());
                binder.setAllowedFields(this.getAllowedFields());
            }

            private String[] getAllowedFields() {
                final String[] allowedFields = {
                        getPropertyName(car.carMaster),
                        getPropertyName(car.carMaster.manufacturingData.brand),
                        getPropertyName(car.carMaster.manufacturingData.model)
                };
                return ArrayUtils.addAll(this.getRequiredFields(), allowedFields);
            }

            private String[] getRequiredFields() {
                return new String[]{
                        getPropertyName(car.licencePlate),
                        getPropertyName(car.fuelType),
                        getPropertyName(car.yearOfProduction),
                        getPropertyName(car.licencePlate)
                };
            }

            @Override
            public ModelMap initialize(final Locale locale) throws Exception {

                final ModelMap map = super.initialize(locale);
                final Iterable<NCarMaster> all = carMasterService.findAll();

                map.put(BRANDS, this.extractBrands(all));
                map.put(MODELS, this.extractModels(all));
                map.put(CAR_MASTERS, this.convertToView(all));
                map.put(FUEL_TYPES, this.getFuelTypes(locale));

                return map;
            }

            private SelectComponent<String, String> getFuelTypes(final Locale locale) throws ComponentCompilationException {
                this.logger.debug(String.format("getFuelTypes(locale=%s)", locale));
                return selectComponentFactory.fromEnumeration("CAR_FUEL_TYPES", locale);
            }

            private Set<String> extractBrands(final Iterable<NCarMaster> all) {
                return FluentIterable.from(all).transform(new Function<NCarMaster, String>() {
                    @Nullable
                    @Override
                    public String apply(@Nullable final NCarMaster input) {
                        assert input != null;
                        return input.getBrand();
                    }
                }).toSortedSet(COMPARATOR);
            }

            private Set<String> extractModels(final Iterable<NCarMaster> all) {
                return FluentIterable.from(all).transform(new Function<NCarMaster, String>() {
                    @Nullable
                    @Override
                    public String apply(@Nullable final NCarMaster input) {
                        assert input != null;
                        return input.getModel();
                    }
                }).toSortedSet(COMPARATOR);
            }

            @SuppressWarnings("unchecked")
            private SelectComponent<Long, String> convertToView(final Iterable<NCarMaster> all) {
                final Collection<SelectOption<Long, String>> beans = FluentIterable
                        .from(all)
                        .transform(new Function<NCarMaster, SelectOption<Long, String>>() {
                            @Nullable
                            @Override
                            public SelectOption<Long, String> apply(@Nullable final NCarMaster input) {
                                if (input == null) {
                                    return null;
                                }
                                return new CarMasterBean()
                                        .setTooltip(input.getIdentity())
                                        .setLabel(input.getIdentity())
                                        .setValue(input.getId());
                            }
                        })
                        .toList();

                return new SelectComponent<Long, String>().setOptions(beans);
            }

        };
        final StepHelper OWNER = new AbstractStepHelper("owner", true) {
            @Override
            public WizardStepDescriptor getStepDescriptor(final Locale locale) {
                return (WizardStepDescriptor) super.getStepDescriptor(locale)
                        .setRequired(true)
                        .addLabel("owner", messageSource.getMessage("NCar.owner", locale))
                        .setLabel(messageSource.getMessage("wizard.NewCarWizard.setupOwner.desc", locale));
            }

            @Override
            public void initializeBinder(final DataBinder binder) {
                final String propertyName = getPropertyName(car.owner);
                binder.setRequiredFields(propertyName);
                binder.setAllowedFields(propertyName);
            }

            @Override
            public ModelMap initialize(final Locale locale) throws Exception {
                final ModelMap map = super.initialize(locale);
                map.put(OWNERS_PARAM_KEY, this.convertToView(carService.getOwners()));
                return map;
            }

            private Collection<OwnerBean> convertToView(final Collection<NUser> owners) {
                return FluentIterable
                        .from(owners)
                        .transform(new Function<NUser, OwnerBean>() {
                            @Nullable
                            @Override
                            public OwnerBean apply(@Nullable final NUser input) {
                                if (input == null) {
                                    return null;
                                }
                                return new OwnerBean()
                                        .setOwnerId(input.getId())
                                        .setOwnerIdentity(userService.getPerson(input).getIdentity());
                            }
                        })
                        .toSet();
            }
        };

        private class CarMasterBean
                extends SelectOption<Long, String> {
            private static final long   serialVersionUID = 2416206557929038223L;
            private              String tooltip          = null;

            public String getTooltip() {
                return tooltip;
            }

            public CarMasterBean setTooltip(final String tooltip) {
                this.tooltip = tooltip;
                return this;
            }
        }

        private class OwnerBean
                implements Serializable {
            private static final long   serialVersionUID = 1349406708171914877L;
            private              String ownerIdentity    = null;
            private              Long   ownerId          = null;

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
        }

    }

}
