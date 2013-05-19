package org.agatom.springatom.model;

import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author kornicamaister
 * @version 0.0.1
 * @since 0.0.1
 */
@MappedSuperclass
abstract class Persistent implements Persistable {
    @Transient
    protected String idColumnName;

    protected Persistent() {
        this.resolveIdColumnName();
    }

    protected abstract void resolveIdColumnName();

    @Override
    public String getEntityName() {
        Table annotation = this.getClass().getAnnotation(Table.class);
        if (annotation == null) {
            annotation = this.getClass().getSuperclass().getAnnotation(Table.class);
        }
        return annotation.name();
    }

    @Override
    public String getIdColumnName() {
        return this.idColumnName;
    }
}
