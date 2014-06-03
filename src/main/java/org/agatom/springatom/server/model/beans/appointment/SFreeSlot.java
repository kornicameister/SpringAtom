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
 * <p>SFreeSlot class.</p>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class SFreeSlot
		implements Serializable,
		Comparable<SFreeSlot> {
	private static final long serialVersionUID = 7645856029174566375L;
	private final long     sideA;
	private final long     sideB;
	private final Duration duration;
	private final Slot     slot;

	/**
	 * <p>Constructor for SFreeSlot.</p>
	 *
	 * @param sideA    a long.
	 * @param sideB    a long.
	 * @param duration a {@link org.joda.time.Duration} object.
	 * @param slot     a {@link org.agatom.springatom.server.model.beans.appointment.SFreeSlot.Slot} object.
	 */
	public SFreeSlot(final long sideA, final long sideB, final Duration duration, final Slot slot) {
		this.sideA = sideA;
		this.sideB = sideB;
		this.duration = duration;
		this.slot = slot;
	}

	/**
	 * <p>Getter for the field <code>sideA</code>.</p>
	 *
	 * @return a long.
	 */
	public long getSideA() {
		return this.sideA;
	}

	/**
	 * <p>Getter for the field <code>sideB</code>.</p>
	 *
	 * @return a long.
	 */
	public long getSideB() {
		return this.sideB;
	}

	/**
	 * <p>Getter for the field <code>duration</code>.</p>
	 *
	 * @return a {@link org.joda.time.Duration} object.
	 */
	public Duration getDuration() {
		return duration;
	}

	/**
	 * <p>Getter for the field <code>slot</code>.</p>
	 *
	 * @return a {@link org.agatom.springatom.server.model.beans.appointment.SFreeSlot.Slot} object.
	 */
	public Slot getSlot() {
		return slot;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "SFreeSlot{" +
				"sideA=" + sideA +
				", sideB=" + sideB +
				", duration=" + duration +
				", slot=" + slot +
				"} " + super.toString();
	}

	/** {@inheritDoc} */
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
