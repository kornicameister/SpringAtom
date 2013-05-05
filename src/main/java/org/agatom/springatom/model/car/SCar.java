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

    @Column(nullable = false,
            length = 45,
            name = "registrationNumber")
    private String registrationNumber;

    @Column(nullable = false,
            length = 45,
            name = "vinNumber")
    private String vinNumber;

    @Audited
    @NaturalId(mutable = true)
    @Column(nullable = false,
            name = "revision")
    private Integer revision;

    public SCarMaster getCarMaster() {
        return carMaster;
    }

    public void setCarMaster(final SCarMaster carMaster) {
        this.carMaster = carMaster;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(final String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getVinNumber() {
        return vinNumber;
    }

    public void setVinNumber(final String vinNumber) {
        this.vinNumber = vinNumber;
    }

    public Integer getRevision() {
        return revision;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (registrationNumber != null ? registrationNumber.hashCode() : 0);
        result = 31 * result + (vinNumber != null ? vinNumber.hashCode() : 0);
        result = 31 * result + (revision != null ? revision.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof SCar)) return false;
        if (!super.equals(o)) return false;

        SCar sCar = (SCar) o;

        return !(registrationNumber != null ? !registrationNumber.equals(sCar.registrationNumber) : sCar.registrationNumber != null)
                && !(vinNumber != null ? !vinNumber.equals(sCar.vinNumber) : sCar.vinNumber != null)
                && !(revision != null ? !revision.equals(sCar.revision) : sCar.revision != null);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("carMaster", carMaster)
                .add("revision", revision)
                .add("registrationNumber", registrationNumber)
                .add("vinNumber", vinNumber)
                .toString();
    }
}
