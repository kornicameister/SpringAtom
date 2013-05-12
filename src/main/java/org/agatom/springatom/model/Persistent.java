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
        Table annotation = this.getClass().getAnnotation(Table.class);
        if (annotation == null) {
            annotation = this.getClass().getSuperclass().getAnnotation(Table.class);
        }
        return annotation.name();
    }
}
