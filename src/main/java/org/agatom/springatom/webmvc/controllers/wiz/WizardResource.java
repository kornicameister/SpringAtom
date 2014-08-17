/**************************************************************************************************
 * This file is part of [SpringAtom] Copyright [kornicameister@gmail.com][2014]                   *
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

package org.agatom.springatom.webmvc.controllers.wiz;

import org.agatom.springatom.web.wizards.data.WizardDescriptor;
import org.agatom.springatom.webmvc.data.DataResource;

import java.io.Serializable;

/**
 * <small>Class is a part of <b>SpringAtom</b> and was created at 2014-08-17</small>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
class WizardResource
        extends DataResource<Object> {
    private static final long serialVersionUID = 353757626270977516L;

    WizardResource(final Object content) {
        super(content);
    }

    static DataResource<?> newWizardDescriptorResource(final WizardDescriptor descriptor) {
        return new WizardResource(new Wrapper().setContent(descriptor)).setSuccess(true).setSize(1);
    }

    static DataResource<?> newErrorResource(final Exception exp) {
        return new WizardResource(new Wrapper().setContent(exp.getCause())).setError(exp).setSize(1).setMessage(exp.getMessage());
    }

    private static class Wrapper
            implements Serializable {
        private static final long   serialVersionUID = -2123182003004399496L;
        private              Object content          = null;

        public Object getContent() {
            return content;
        }

        public Wrapper setContent(final Object content) {
            this.content = content;
            return this;
        }
    }
}
