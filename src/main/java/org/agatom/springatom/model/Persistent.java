package org.agatom.springatom.model;

import javax.persistence.MappedSuperclass;
import javax.persistence.Table;

/**
 * @author kornicamaister
 * @version 0.0.1
 * @since 0.0.1
 */
@MappedSuperclass
abstract class Persistent implements Persistable {

    @Override
    public String getEntityName() {
        return this.getClass().getAnnotation(Table.class).name();
    }
}
