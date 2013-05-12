package org.agatom.springatom.model.links;

import com.google.common.base.Objects;
import org.agatom.springatom.model.PersistentObject;
import org.agatom.springatom.model.car.SCar;
import org.agatom.springatom.model.client.SClient;
import org.hibernate.annotations.Where;

import javax.persistence.*;

/**
 * @author kornicamaister
 * @version 0.0.1
 * @since 0.0.1
 */
@Entity(name = "SCarClientLink")
@Table(name = "SCarClientLink")
@AttributeOverride(
        name = "id",
        column = @Column(
                name = "idSCarClientLink",
                updatable = false,
                nullable = false)
)
public class SCarClientLink extends PersistentObject {

    @ManyToOne(optional = false)
    @JoinColumns(value = {
            @JoinColumn(name = "client", referencedColumnName = "idSClient", updatable = false),
            @JoinColumn(name = "clientVersion", referencedColumnName = "version", updatable = false)
    })
    private SClient client;

    @ManyToOne(optional = false)
    @JoinColumns(value = {
            @JoinColumn(name = "car", referencedColumnName = "idScar"),
            @JoinColumn(name = "carVersion", referencedColumnName = "version")
    })
    @Where(clause = "car.pk.version=carRevision")
    private SCar car;

    public SClient getClient() {
        return client;
    }

    public void setClient(final SClient client) {
        this.client = client;
    }

    public SCar getCar() {
        return car;
    }

    public void setCar(final SCar car) {
        this.car = car;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + client.hashCode();
        result = 31 * result + car.hashCode();
        return result;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof SCarClientLink)) return false;
        if (!super.equals(o)) return false;

        SCarClientLink that = (SCarClientLink) o;

        return car.equals(that.car) &&
                client.equals(that.client);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("client", client)
                .add("car", car)
                .toString();
    }
}
