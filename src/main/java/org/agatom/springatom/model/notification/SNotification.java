package org.agatom.springatom.model.notification;

import com.google.common.base.Objects;
import org.agatom.springatom.model.PersistentObject;
import org.agatom.springatom.model.meta.SNotificationType;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

/**
 * @author kornicamaister
 * @version 0.0.1
 * @since 0.0.1
 */
@Entity(name = "SNotification")
@Table(name = "SNotification")
@AttributeOverride(
        name = "id",
        column = @Column(
                name = "idSNotification",
                updatable = false,
                nullable = false)
)
public class SNotification extends PersistentObject {

    @NaturalId
    @ManyToOne(optional = false)
    @JoinColumn(name = "type",
            referencedColumnName = "idSMetaData", updatable = true)
    private SNotificationType type;

    @Column(name = "message", length = 1000)
    private String message;

    @Type(type = "timestamp")
    @Column(name = "sent")
    private Date sent;

    public SNotification() {
        super();
    }

    public SNotificationType getType() {
        return type;
    }

    public void setType(final SNotificationType type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String notification) {
        this.message = notification;
    }

    public Date getSent() {
        return sent;
    }

    public void setSent(final Date sent) {
        this.sent = sent;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (sent != null ? sent.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof SNotification)) return false;
        if (!super.equals(o)) return false;

        SNotification that = (SNotification) o;

        return !(message != null ? !message.equals(that.message) : that.message != null) &&
                !(sent != null ? !sent.equals(that.sent) : that.sent != null) &&
                !(type != null ? !type.equals(that.type) : that.type != null);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("type", type)
                .add("message", message)
                .add("sent", sent)
                .toString();
    }
}
