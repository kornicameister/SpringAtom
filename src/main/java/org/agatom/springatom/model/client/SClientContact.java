package org.agatom.springatom.model.client;

import com.google.common.base.Objects;
import org.agatom.springatom.model.PersistentObject;
import org.agatom.springatom.model.meta.SContactType;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

/**
 * @author kornicamaister
 * @version 0.0.1
 * @since 0.0.1
 */
@Entity(name = "SClientContact")
@Table(name = "SClientContact")
@AttributeOverride(
        name = "id",
        column = @Column(
                name = "idSClientContact",
                updatable = false,
                nullable = false)
)
public class SClientContact extends PersistentObject {

    @ManyToOne(optional = false)
    @JoinColumn(name = "client", referencedColumnName = "idSClient", updatable = true)
    private SClient client;

    @NaturalId(mutable = true)
    @ManyToOne(optional = false)
    @JoinColumn(name = "type", referencedColumnName = "idSMetaData", updatable = true)
    private SContactType type;

    @Column(name = "contact", length = 60)
    private String contact;

    public SClient getClient() {
        return client;
    }

    public void setClient(final SClient client) {
        this.client = client;
    }

    public SContactType getType() {
        return type;
    }

    public void setType(final SContactType type) {
        this.type = type;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(final String contact) {
        this.contact = contact;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + client.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + contact.hashCode();
        return result;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof SClientContact)) return false;
        if (!super.equals(o)) return false;

        SClientContact that = (SClientContact) o;

        return client.equals(that.client) &&
                contact.equals(that.contact) &&
                type.equals(that.type);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("client", client)
                .add("type", type)
                .add("contact", contact)
                .toString();
    }
}
