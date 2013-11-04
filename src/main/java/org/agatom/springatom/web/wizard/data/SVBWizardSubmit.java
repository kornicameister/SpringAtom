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

package org.agatom.springatom.web.wizard.data;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import org.agatom.springatom.web.beans.WebBean;

import java.util.Iterator;
import java.util.List;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class SVBWizardSubmit
        implements WebBean,
                   Iterable<SVStep> {
    private transient static final String       BEAN_ID          = "wizardSubmit";
    private transient static final long         serialVersionUID = 2668910316370364323L;
    private                        List<SVStep> data             = Lists.newArrayList();

    public SVBWizardSubmit() {
    }

    public List<SVStep> getData() {
        return data;
    }

    public SVBWizardSubmit setData(final List<SVStep> data) {
        this.data = data;
        return this;
    }

    @Override
    public String getBeanId() {
        return BEAN_ID;
    }

    @Override
    public Iterator<SVStep> iterator() {
        return this.data.iterator();
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                      .addValue(data)
                      .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SVBWizardSubmit that = (SVBWizardSubmit) o;

        return Objects.equal(this.data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(data);
    }
}
