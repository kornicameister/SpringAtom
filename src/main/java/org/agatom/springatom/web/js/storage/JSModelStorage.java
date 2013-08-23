/**************************************************************************************************
 * This file is part of [SpringAtom] Copyright [kornicameister@gmail.com][2013]                   *
 *                                                                                                *
 * [SpringAtom] is free software: you can redistribute it and/or modify                           *
 * it under the terms of the GNU General Public License as published by                           *
 * the Free Software Foundation, either version 3 of the License, or                              *
 * (at your option) any later version.                                                            *
 *                                                                                                *
 * [SpringAtom] is distributed in the hope that it will be useful,                                *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of                                 *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the                                  *
 * GNU General Public License for more details.                                                   *
 *                                                                                                *
 * You should have received a copy of the GNU General Public License                              *
 * along with [SpringAtom].  If not, see <http://www.gnu.org/licenses/gpl.html>.                  *
 **************************************************************************************************/

package org.agatom.springatom.web.js.storage;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Maps;
import org.agatom.springatom.web.js.meta.JSMetaModel;

import javax.annotation.Nullable;
import java.util.Map;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class JSModelStorage {
    private final static Map<String, JSMetaModel> META_MODELS = Maps.newConcurrentMap();

    public static JSMetaModel storeModel(final JSMetaModel jsMetaModel) {
        return META_MODELS.put(jsMetaModel.getModelName(), jsMetaModel);
    }

    public static JSMetaModel getModel(final String modelName) {
        return META_MODELS.get(modelName);
    }

    public static boolean hasModel(final String modelName) {
        return META_MODELS.containsKey(modelName);
    }

    public static boolean hasModelForClass(final Class<?> aClass) {
        return Collections2.filter(META_MODELS.values(), new Predicate<JSMetaModel>() {
            @Override
            public boolean apply(@Nullable final JSMetaModel input) {
                assert input != null;
                return input.getBean().equals(aClass);
            }
        }).size() > 0;
    }
}
