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

package org.agatom.springatom.server.model.beans.activity;

import org.agatom.springatom.server.model.beans.PersistentObject;
import org.agatom.springatom.server.model.beans.user.SUser;
import org.agatom.springatom.server.model.types.activity.Activity;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.io.Serializable;

/**
 * {@code SActivity} is a generic abstract class for all sorts of activities.
 * Is is an <strong>embeddable</strong> object which means that properties specified within does not lie in external
 * table but are embedded within the table which class embeds the {@link org.agatom.springatom.server.model.beans.activity.SActivity}
 *
 * @author kornicamaister
 * @version 0.0.1
 * @since 0.0.1
 */
@MappedSuperclass
abstract public class SActivity<PK extends Serializable>
        extends PersistentObject<PK>
        implements Activity {
    private static final String DATE_TIME_TYPE   = "org.jadira.usertype.dateandtime.joda.PersistentDateTime";
    private static final long   serialVersionUID = 2584800898826870084L;
    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "activity_reporter", referencedColumnName = "idSUser", updatable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    protected SUser    reporter;
    @Past
    @Type(type = DATE_TIME_TYPE)
    @Column(name = "activity_date", nullable = false)
    protected DateTime assigned;

    public SActivity() {
        this.assigned = DateTime.now();
    }

    @Override
    public SUser getReporter() {
        if (this.reporter == null) {
            this.reporter = new SUser();
        }
        return reporter;
    }

    @Override
    public Activity setReporter(final SUser user) {
        this.reporter = user;
        return this;
    }

    @Override
    public DateTime getAssigned() {
        return null == this.assigned ? null : new DateTime(this.assigned);
    }

    @Override
    public Activity setAssigned(final DateTime assigned) {
        this.assigned = assigned;
        return this;
    }

}
