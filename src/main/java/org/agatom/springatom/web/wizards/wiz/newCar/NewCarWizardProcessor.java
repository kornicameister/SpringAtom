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

package org.agatom.springatom.web.wizards.wiz.newCar;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Function;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mysema.query.types.Path;
import com.neovisionaries.i18n.CountryCode;
import org.agatom.springatom.server.model.OID;
import org.agatom.springatom.server.model.beans.car.QSCar;
import org.agatom.springatom.server.model.beans.car.SCar;
import org.agatom.springatom.server.model.beans.car.SCarMaster;
import org.agatom.springatom.server.model.types.car.FuelType;
import org.agatom.springatom.server.service.domain.SCarMasterService;
import org.agatom.springatom.server.service.domain.SCarService;
import org.agatom.springatom.web.flows.wizards.wizard.LocalizedEnumHolder;
import org.agatom.springatom.web.locale.SMessageSource;
import org.agatom.springatom.web.wizards.Wizard;
import org.agatom.springatom.web.wizards.core.AbstractWizardProcessor;
import org.agatom.springatom.web.wizards.data.WizardDescriptor;
import org.agatom.springatom.web.wizards.data.WizardStepDescriptor;
import org.agatom.springatom.web.wizards.data.result.FeedbackMessage;
import org.agatom.springatom.web.wizards.data.result.WizardResult;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Persistable;
import org.springframework.ui.ModelMap;
import org.springframework.util.ClassUtils;
import org.springframework.validation.DataBinder;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.*;

/**
 * <small>Class is a part of <b>SpringAtom</b> and was created at 2014-08-17</small>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@Wizard("newCar")
class NewCarWizardProcessor
        extends AbstractWizardProcessor<SCar> {
    private static final Logger             LOGGER           = Logger.getLogger(NewCarWizardProcessor.class);
    private static final String             FORM_OBJECT_NAME = "car";
    private static final String             BRANDS           = "brands";
    private static final String             MODELS           = "models";
    private static final String             CAR_MASTERS      = "carMasters";
    private static final String             FUEL_TYPES       = "fuelTypes";
    private static final String             OWNERS_PARAM_KEY = "owners";
    private static final Comparator<String> COMPARATOR       = new Comparator<String>() {
        @Override
        public int compare(final String o1, final String o2) {
            return o1.compareTo(o2);
        }
    };
    private static final String[]           REQUIRED_FIELDS  = NewCarWizardProcessor.getRequiredFields();
    private static final String[]           ALLOWED_FIELDS   = NewCarWizardProcessor.getAllowedFields();
    @Autowired
    private              SMessageSource     messageSource    = null;
    @Autowired
    private              SCarService        carService       = null;
    @Autowired
    private              SCarMasterService  carMasterService = null;

    /**
     * Returns array of allowed fields for {@link org.springframework.validation.DataBinder}
     *
     * @return allowed fields
     */
    private static String[] getAllowedFields() {
        final QSCar car = QSCar.sCar;
        return new String[]{
                getPropertyName(car.carMaster),
                getPropertyName(car.carMaster.manufacturingData.brand),
                getPropertyName(car.carMaster.manufacturingData.model)
        };
    }

    private static String getPropertyName(final Path<?> path) {
        return path.getMetadata().getName();
    }

    /**
     * Returns array of required fields for {@link org.springframework.validation.DataBinder}
     *
     * @return required fields
     */
    private static String[] getRequiredFields() {
        final QSCar car = QSCar.sCar;
        return new String[]{
                getPropertyName(car.licencePlate),
                getPropertyName(car.owner),
                getPropertyName(car.fuelType),
                getPropertyName(car.vinNumber),
                getPropertyName(car.yearOfProduction),
                getPropertyName(car.licencePlate),
                getPropertyName(car.licencePlate)
        };
    }

    @Override
    protected SCar getContextObject() throws Exception {
        final SCar car = super.getContextObject();
        car.setCarMaster(new SCarMaster());
        return car;
    }

    @Override
    protected WizardResult submitWizard(SCar contextObject, final Map<String, Object> stepData, final Locale locale) throws Exception {
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
    public String getContextObjectName() {
        return FORM_OBJECT_NAME;
    }

    @Override
    protected WizardDescriptor getDescriptor(final Locale locale) {
        LOGGER.debug(String.format("getDescriptor(locale=%s)", locale));
        final WizardDescriptor descriptor = new WizardDescriptor();

        descriptor.setLabel(this.messageSource.getMessage("scar", locale));
        descriptor.addStep((WizardStepDescriptor) new WizardStepDescriptor()
                .setStep("vin")
                .setRequired(true)
                .addLabel("vinNumber", this.messageSource.getMessage("scar.vinnumber", locale))
                .setLabel(this.messageSource.getMessage("wizard.NewCarWizard.setupVin.desc", locale)));
        descriptor.addStep((WizardStepDescriptor) new WizardStepDescriptor()
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
        descriptor.addStep((WizardStepDescriptor) new WizardStepDescriptor()
                .setStep("owner")
                .setRequired(true)
                .addLabel("owner", this.messageSource.getMessage("scar.owner", locale))
                .setLabel(this.messageSource.getMessage("wizard.NewCarWizard.setupOwner.desc", locale)));

        descriptor.addDynamicProperty("handler", ClassUtils.getShortName(this.getClass()));

        return descriptor;
    }

    private OID getOID(final SCar contextObject) {
        final OID oid = new OID();
        oid.setObjectClass(ClassUtils.getUserClass(contextObject.getClass()).getSimpleName());
        oid.setPrefix("M");
        if (ClassUtils.isAssignableValue(Persistable.class, contextObject)) {
            oid.setId((Long) ((Persistable<?>) contextObject).getId());
        } else {
            oid.setId(System.nanoTime());
        }
        return oid;
    }

    @Override
    protected DataBinder createBinder(final Object contextObject, final String contextObjectName) {
        final DataBinder binder = super.createBinder(contextObject, contextObjectName);

        final List<String> list = Lists.newArrayList(REQUIRED_FIELDS);
        Collections.addAll(list, ALLOWED_FIELDS);

        binder.setAllowedFields(list.toArray(new String[list.size()]));
        binder.setRequiredFields(REQUIRED_FIELDS);
        return binder;
    }

    @Override
    public WizardResult initializeStep(final String step, final Locale locale) {
        final ModelMap modelMap = new ModelMap();

        final WizardResult result = new WizardResult()
                .setStepId(step)
                .setWizardId("newCar");

        switch (step) {
            case "car":
                modelMap.addAllAttributes(this.initializeCarStep(locale));
                break;
            case "owner":
                modelMap.addAllAttributes(this.initializeOwnerStep());
        }

        return result.addWizardData(modelMap);
    }

    private Map<String, Object> initializeCarStep(final Locale locale) {
        final Map<String, Object> viewScope = Maps.newHashMap();

        final List<SCarMaster> all = this.carMasterService.findAll();

        viewScope.put(BRANDS, this.extractBrands(all));
        viewScope.put(MODELS, this.extractModels(all));
        viewScope.put(CAR_MASTERS, this.convertToView(all));
        viewScope.put(FUEL_TYPES, this.getFuelTypes(locale));

        return viewScope;
    }

    private Map<String, Object> initializeOwnerStep() {
        final Map<String, Object> viewScope = Maps.newHashMap();
        viewScope.put(OWNERS_PARAM_KEY, this.convertToView(this.carService.getOwners()));
        return viewScope;
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

    private Collection<CarMasterBean> convertToView(final List<SCarMaster> all) {
        return FluentIterable
                .from(all)
                .transform(new Function<SCarMaster, CarMasterBean>() {
                    @Nullable
                    @Override
                    public CarMasterBean apply(@Nullable final SCarMaster input) {
                        if (input == null) {
                            return null;
                        }
                        return new CarMasterBean()
                                .setLabel(input.getIdentity())
                                .setValue(input.getId())
                                .setTooltip(input.getIdentity())
                                .setCompareValue(input);
                    }
                })
                .toSortedSet(new Comparator<CarMasterBean>() {
                    @Override
                    public int compare(@Nonnull final CarMasterBean o1, @Nonnull final CarMasterBean o2) {
                        return ComparisonChain.start()
                                .compare(o1.getCompareValue().getBrand(), o2.getCompareValue().getBrand())
                                .compare(o1.getCompareValue().getModel(), o2.getCompareValue().getModel())
                                .result();
                    }
                });
    }

    private List<LocalizedEnumHolder<FuelType>> getFuelTypes(final Locale locale) {
        LOGGER.debug(String.format("getFuelTypes(locale=%s)", locale));
        final ArrayList<FuelType> types = Lists.newArrayList(FuelType.values());
        return FluentIterable.from(types)
                .transform(new Function<FuelType, LocalizedEnumHolder<FuelType>>() {
                    @Nullable
                    @Override
                    @SuppressWarnings("unchecked")
                    public LocalizedEnumHolder<FuelType> apply(@Nullable final FuelType input) {
                        assert input != null;
                        return new LocalizedEnumHolder<FuelType>().setValue(input).setLabel(messageSource.getMessage(input.name(), locale));
                    }
                }).toList();
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

    private class CarMasterBean
            implements Serializable {
        private static final long   serialVersionUID = 2416206557929038223L;
        private              String label            = null;
        private              Long   value            = null;
        private              String tooltip          = null;
        private SCarMaster compareValue;

        public String getLabel() {
            return label;
        }

        public CarMasterBean setLabel(final String label) {
            this.label = label;
            return this;
        }

        public Long getValue() {
            return value;
        }

        public CarMasterBean setValue(final Long value) {
            this.value = value;
            return this;
        }

        public String getTooltip() {
            return tooltip;
        }

        public CarMasterBean setTooltip(final String tooltip) {
            this.tooltip = tooltip;
            return this;
        }

        @JsonIgnore
        public SCarMaster getCompareValue() {
            return compareValue;
        }

        @JsonIgnore
        public CarMasterBean setCompareValue(final SCarMaster compareValue) {
            this.compareValue = compareValue;
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
}
