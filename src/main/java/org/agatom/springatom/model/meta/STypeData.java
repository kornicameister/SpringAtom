package org.agatom.springatom.model.meta;

import org.agatom.springatom.model.PersistentObject;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalId;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@MappedSuperclass
@org.hibernate.annotations.Cache(
        region = "invoiceproduct_cache",
        usage = CacheConcurrencyStrategy.READ_ONLY
)
@Cacheable(
        value = true
)
@BatchSize(
        size = 20
)
abstract public class STypeData extends PersistentObject {

    @NaturalId
    @Column(nullable = false, length = 20, unique = true, updatable = false, insertable = true, name = "type")
    private String type;

    protected STypeData() {
        super();
    }

    public STypeData(final String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("STypeData");
        sb.append("{super =").append(super.toString());
        sb.append("},");
        sb.append("{type='").append(type).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
