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

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.neovisionaries.i18n.CountryCode;
import org.agatom.springatom.server.model.beans.car.QSCar;
import org.agatom.springatom.server.model.beans.car.SCar;
import org.agatom.springatom.server.model.beans.car.SCarMaster;
import org.agatom.springatom.server.model.types.car.FuelType;
import org.agatom.springatom.server.service.domain.SCarMasterService;
import org.agatom.springatom.server.service.domain.SCarService;
import org.agatom.springatom.server.service.vinNumber.decoder.VinDecoder;
import org.agatom.springatom.server.service.vinNumber.exception.VinDecodingException;
import org.agatom.springatom.server.service.vinNumber.model.VinNumberData;
import org.agatom.springatom.web.component.ComponentCompilationException;
import org.agatom.springatom.web.component.select.SelectComponent;
import org.agatom.springatom.web.component.select.SelectOption;
import org.agatom.springatom.web.component.select.factory.SelectComponentFactory;
import org.agatom.springatom.web.locale.SMessageSource;
import org.agatom.springatom.web.wizards.StepHelper;
import org.agatom.springatom.web.wizards.Wizard;
import org.agatom.springatom.web.wizards.core.AbstractStepHelper;
import org.agatom.springatom.web.wizards.core.CreateObjectWizardProcessor;
import org.agatom.springatom.web.wizards.data.WizardDescriptor;
import org.agatom.springatom.web.wizards.data.WizardStepDescriptor;
import org.agatom.springatom.web.wizards.data.result.FeedbackMessage;
import org.agatom.springatom.web.wizards.data.result.WizardDebugDataKeys;
import org.agatom.springatom.web.wizards.data.result.WizardResult;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.validation.DataBinder;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.*;
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
        extends CreateObjectWizardProcessor<SCar> {
    private static final Logger                 LOGGER                 = Logger.getLogger(NewCarWizardProcessor.class);
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
    private              SCarService            carService             = null;
    @Autowired
    private              SCarMasterService      carMasterService       = null;
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
    protected SCar getContextObject() throws Exception {
        final SCar car = super.getContextObject();
        car.setCarMaster(new SCarMaster());
        return car;
    }

    @Override
    protected WizardResult submitStep(final SCar contextObject, final ModelMap stepData, final String step, final Locale locale) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("submitStep(contextObject=%s)", contextObject));
        }

        final long startTime = System.nanoTime();
        final WizardResult result = new WizardResult();
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

        return result;
    }

    @Override
    protected WizardResult submitWizard(SCar contextObject, final ModelMap stepData, final Locale locale) throws Exception {
        final SCarMaster carMaster = contextObject.getCarMaster();
        carMaster.setManufacturedIn(CountryCode.valueOf((String) stepData.get("manufacturedIn")));
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
                                        new Object[]{this.messageSource.getMessage("scar", locale)},
                                        locale
                                )
                        )
        );

        return result;
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

    private class StepsHolder {

        final QSCar car = QSCar.sCar;

        final StepHelper VIN = new AbstractStepHelper("vin") {
            @Override
            public WizardStepDescriptor getStepDescriptor(final Locale locale) {
                return (WizardStepDescriptor) super.getStepDescriptor(locale)
                        .setRequired(true)
                        .addLabel("vinNumber", messageSource.getMessage("scar.vinnumber", locale))
                        .setLabel(messageSource.getMessage("wizard.NewCarWizard.setupVin.desc", locale));
            }

            @Override
            public boolean isValidationEnabled() {
                return true;
            }


            @Override
            public void initializeBinder(final DataBinder binder) {
                binder.setRequiredFields(getPropertyName(car.vinNumber));
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

        final StepHelper CAR = new AbstractStepHelper("car") {
            final Logger logger = Logger.getLogger(this.getClass());

            @Override
            public WizardStepDescriptor getStepDescriptor(final Locale locale) {
                return (WizardStepDescriptor) super.getStepDescriptor(locale)
                        .setRequired(true)
                        .addLabel("brand", messageSource.getMessage("scarmastermanufacturingdata.brand", locale))
                        .addLabel("model", messageSource.getMessage("scarmastermanufacturingdata.model", locale))
                        .addLabel("carMaster", messageSource.getMessage("scar.carmaster", locale))
                        .addLabel("licencePlate", messageSource.getMessage("scar.licenceplate", locale))
                        .addLabel("fuelType", messageSource.getMessage("scar.fueltype", locale))
                        .addLabel("year", messageSource.getMessage("scar.yearofproduction", locale))
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
                final List<SCarMaster> all = carMasterService.findAll();

                map.put(BRANDS, this.extractBrands(all));
                map.put(MODELS, this.extractModels(all));
                map.put(CAR_MASTERS, this.convertToView(all));
                map.put(FUEL_TYPES, this.getFuelTypes(locale));

                return map;
            }

            private SelectComponent<FuelType, String> getFuelTypes(final Locale locale) throws ComponentCompilationException {
                this.logger.debug(String.format("getFuelTypes(locale=%s)", locale));

                return selectComponentFactory
                        .<FuelType, String, FuelType>newSelectComponent()
                        .from(carService.getFuelTypes())
                        .usingValueFunction(new Function<FuelType, FuelType>() {
                            @Nullable
                            @Override
                            public FuelType apply(@Nullable final FuelType input) {
                                return input;
                            }
                        })
                        .usingLabelFunction(new Function<FuelType, String>() {
                            @Nullable
                            @Override
                            public String apply(@Nullable final FuelType input) {
                                assert input != null;
                                return messageSource.getMessage(input.name(), locale);
                            }
                        })
                        .get();
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

            @SuppressWarnings("unchecked")
            private SelectComponent<Long, String> convertToView(final List<SCarMaster> all) {
                final Collection<SelectOption<Long, String>> beans = FluentIterable
                        .from(all)
                        .transform(new Function<SCarMaster, SelectOption<Long, String>>() {
                            @Nullable
                            @Override
                            public SelectOption<Long, String> apply(@Nullable final SCarMaster input) {
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


        final StepHelper OWNER = new AbstractStepHelper("owner") {
            @Override
            public WizardStepDescriptor getStepDescriptor(final Locale locale) {
                return (WizardStepDescriptor) super.getStepDescriptor(locale)
                        .setRequired(true)
                        .addLabel("owner", messageSource.getMessage("scar.owner", locale))
                        .setLabel(messageSource.getMessage("wizard.NewCarWizard.setupOwner.desc", locale));
            }

            @Override
            public void initializeBinder(final DataBinder binder) {
                binder.setRequiredFields(getPropertyName(car.owner));
            }

            @Override
            public ModelMap initialize(final Locale locale) throws Exception {
                final ModelMap map = super.initialize(locale);
                map.put(OWNERS_PARAM_KEY, this.convertToView(carService.getOwners()));
                return map;
            }

            private Collection<OwnerBean> convertToView(final Collection<SCarService.Owner> owners) {
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
                                    map.put("id", car.getId());
                                    maps.add(map);
                                }
                                return ownerBean.setCarsMap(maps);
                            }
                        })
                        .toSet();
            }
        };

    }

}
