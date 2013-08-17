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

package org.agatom.springatom.server.model.beans.appointment;

import com.google.common.collect.ComparisonChain;
import org.joda.time.Duration;

import java.io.Serializable;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class SFreeSlot
        implements Serializable,
                   Comparable<SFreeSlot> {
    private final long     sideA;
    private final long     sideB;
    private final Duration duration;
    private final Slot     slot;

    public SFreeSlot(final long sideA, final long sideB, final Duration duration, final Slot slot) {
        this.sideA = sideA;
        this.sideB = sideB;
        this.duration = duration;
        this.slot = slot;
    }

    public long getSideA() {
        return this.sideA;
    }

    public long getSideB() {
        return this.sideB;
    }

    public Duration getDuration() {
        return duration;
    }

    public Slot getSlot() {
        return slot;
    }

    @Override
    public String toString() {
        return "SFreeSlot{" +
                "sideA=" + sideA +
                ", sideB=" + sideB +
                ", duration=" + duration +
                ", slot=" + slot +
                "} " + super.toString();
    }

    @Override
    public int compareTo(final SFreeSlot sfs) {
        assert sfs != null;
        return ComparisonChain.start()
                              .compare(this.sideA, sfs.sideA)
                              .compare(this.sideB, sfs.sideB)
                              .compare(this.duration, sfs.duration)
                              .compare(this.slot, sfs.slot)
                              .result();
    }

    public static enum Slot {
        BEFORE,
        AFTER,
        INTERSECT
    }
}
