package org.agatom.springatom.mvc.model.provider;

import org.agatom.springatom.model.Persistable;

import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * @author kornicamaister
 * @version 0.0.1
 * @since 0.0.1
 */
public interface DataProvider<T extends Persistable> {
    T getById(@NotNull final Long id);

    Set<T> getAll();
}
