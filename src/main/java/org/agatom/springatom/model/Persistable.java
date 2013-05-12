package org.agatom.springatom.model;

import java.io.Serializable;

/**
 * @author kornicamaister
 * @version 0.0.1
 * @since 0.0.1
 */
public interface Persistable extends Serializable, Comparable<Persistable> {
    Long getId();

    String getEntityName();
}
