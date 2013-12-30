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

package org.agatom.springatom.server.model.beans.issue;

import org.agatom.springatom.server.model.beans.activity.SAssignedActivity;
import org.agatom.springatom.server.model.types.ReportableEntity;
import org.agatom.springatom.server.model.types.issue.Issue;
import org.agatom.springatom.server.model.types.issue.IssueType;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * {@code SIssue} is object which extends from {@link org.agatom.springatom.server.model.beans.activity.SActivity} which means
 * that issue can be reported and placed in the given date but additionally there is a possibility to specify the message
 * and the type of the issue.
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Entity(name = SIssue.ENTITY_NAME)
@Table(name = SIssue.TABLE_NAME)
@ReportableEntity
@AttributeOverride(name = "id", column = @Column(name = "idSIssue", nullable = false, insertable = true, updatable = false, length = 19, precision = 0))
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = SIssue.ISSUE_TYPE, discriminatorType = DiscriminatorType.STRING)
public class SIssue
        extends SAssignedActivity<Long>
        implements Issue {
    public static final    String ENTITY_NAME      = "SIssue";
    public static final    String TABLE_NAME       = "issues";
    protected static final String ISSUE_TYPE       = "sit";
    private static final   long   serialVersionUID = 9153732479122207895L;
    @NotNull
    @Column(name = "issue_msg", nullable = false)
    private String    message;
    @Type(type = "org.hibernate.type.EnumType")
    @Enumerated(value = EnumType.STRING)
    @Column(name = "issue_type", nullable = false)
    private IssueType type;

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public void setMessage(final String message) {
        this.message = message;
    }

    @Override
    public IssueType getType() {
        return this.type;
    }

    @Override
    public void setType(final IssueType type) {
        this.type = type;
    }
}
