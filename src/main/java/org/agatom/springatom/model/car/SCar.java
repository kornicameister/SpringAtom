package org.agatom.springatom.model.car;

import com.google.common.base.Objects;
import org.agatom.springatom.model.PersistentVersionedObject;
import org.hibernate.annotations.NaturalId;
import org.hibernate.envers.Audited;

import javax.persistence.*;

/**
 * @author kornicamaister
 * @version 0.0.1
 * @since 0.0.1
 */
@Entity(name = "SCar")
@Table(name = "SCar")
@AttributeOverride(
        name = "id",
        column = @Column(
                name = "idSCar",
                updatable = false,
                nullable = false)
)
public class SCar extends PersistentVersionedObject {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "carMaster", referencedColumnName = "idSCarMaster", updatable = true)
    private SCarMaster carMaster;

    @Audited
    @NaturalId(mutable = true)
    @Column(nullable = false,
            length = 45,
            name = "licencePlate")
    private String licencePlate;

    @Audited
    @NaturalId(mutable = true)
    @Column(nullable = false,
            length = 45,
            name = "vinNumber")
    private String vinNumber;

    public SCarMaster getCarMaster() {
        return carMaster;
    }

    public void setCarMaster(final SCarMaster carMaster) {
        this.carMaster = carMaster;
    }

    public String getLicencePlate() {
        return licencePlate;
    }

    public void setLicencePlate(final String registrationNumber) {
        this.licencePlate = registrationNumber;
    }

    public String getVinNumber() {
        return vinNumber;
    }

    public void setVinNumber(final String vinNumber) {
        this.vinNumber = vinNumber;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (licencePlate != null ? licencePlate.hashCode() : 0);
        result = 31 * result + (vinNumber != null ? vinNumber.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof SCar)) return false;
        if (!super.equals(o)) return false;

        SCar sCar = (SCar) o;

        return !(licencePlate != null ? !licencePlate.equals(sCar.licencePlate) : sCar.licencePlate != null)
                && !(vinNumber != null ? !vinNumber.equals(sCar.vinNumber) : sCar.vinNumber != null);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("carMaster", carMaster)
                .add("registrationNumber", licencePlate)
                .add("vinNumber", vinNumber)
                .toString();
    }
}
