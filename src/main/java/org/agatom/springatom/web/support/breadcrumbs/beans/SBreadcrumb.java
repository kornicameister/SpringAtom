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

package org.agatom.springatom.web.support.breadcrumbs.beans;

import com.google.common.base.Objects;
import com.google.common.collect.Sets;

import java.util.Arrays;
import java.util.Set;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class SBreadcrumb {
    private final String           crumb;
    private final String           uri;
    private final SBreadcrumb      nextCrumb;
    private final SBreadcrumb      previousCrumb;
    private       Set<SBreadcrumb> predecessors;
    private       Set<SBreadcrumb> successors;

    public SBreadcrumb(final String crumb, final String uri) {
        this(crumb, uri, null, null);
    }

    public SBreadcrumb(final String crumb, final String uri, final SBreadcrumb nextCrumb, final SBreadcrumb previousCrumb) {
        this.crumb = crumb;
        this.uri = uri;
        this.nextCrumb = nextCrumb;
        this.previousCrumb = previousCrumb;
    }

    public String getUri() {
        return uri;
    }

    public String getCrumb() {
        return crumb;
    }

    public SBreadcrumb getNextCrumb() {
        return nextCrumb;
    }

    public SBreadcrumb getPreviousCrumb() {
        return previousCrumb;
    }

    public boolean addPredecessors(final SBreadcrumb... crumbs) {
        if (this.predecessors == null) {
            this.predecessors = Sets.newHashSet();
        }
        return predecessors.addAll(Arrays.asList(crumbs));
    }

    public boolean addSuccessors(final SBreadcrumb... crumbs) {
        if (this.successors == null) {
            this.successors = Sets.newHashSet();
        }
        return successors.addAll(Arrays.asList(crumbs));
    }

    public Set<SBreadcrumb> getPredecessors() {
        return predecessors;
    }

    public SBreadcrumb setPredecessors(final Set<SBreadcrumb> predecessors) {
        this.predecessors = predecessors;
        return this;
    }

    public Set<SBreadcrumb> getSuccessors() {
        return successors;
    }

    public SBreadcrumb setSuccessors(final Set<SBreadcrumb> successors) {
        this.successors = successors;
        return this;
    }

    public boolean hasNext() {
        return this.nextCrumb != null;
    }

    public boolean hasPrevious() {
        return this.previousCrumb != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SBreadcrumb that = (SBreadcrumb) o;

        return Objects.equal(this.crumb, that.crumb) &&
                Objects.equal(this.uri, that.uri);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(crumb, uri);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                      .addValue(crumb)
                      .addValue(uri)
                      .addValue(nextCrumb)
                      .addValue(previousCrumb)
                      .addValue(predecessors)
                      .addValue(successors)
                      .toString();
    }
}
