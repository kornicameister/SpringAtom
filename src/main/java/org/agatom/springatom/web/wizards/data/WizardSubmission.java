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

package org.agatom.springatom.web.wizards.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.agatom.springatom.web.wizards.core.Submission;
import org.agatom.springatom.webmvc.data.DataResource;

/**
 * <small>Class is a part of <b>SpringAtom</b> and was created at 2014-08-18</small>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
public class WizardSubmission
        extends DataResource<Object> {
    private static final long serialVersionUID = -8894514112808006590L;
    private final        long timestamp        = System.currentTimeMillis();

    public WizardSubmission(final Object content, final Submission submission) {
        super(new SubmissionWrapper(content, submission));
    }

    public Submission getSubmission() {
        return ((SubmissionWrapper) super.getContent()).getSubmission();
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    @JsonIgnore
    public Object getData() {
        return ((SubmissionWrapper) super.getContent()).getContent();
    }

    private static class SubmissionWrapper {
        private Object     content    = null;
        private Submission submission = null;

        private SubmissionWrapper(final Object data, final Submission submission) {
            this.content = data;
            this.submission = submission;
        }

        public Object getContent() {
            return content;
        }

        public Submission getSubmission() {
            return submission;
        }
    }
}
