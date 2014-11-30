package org.agatom.springatom.data.types.appointment;

import org.agatom.springatom.data.link.ObjectToObjectLink;
import org.agatom.springatom.data.types.calendar.Calendar;

import java.io.Serializable;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-09-29</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
public interface CalendarAppointment<A extends Calendar & Serializable, B extends Appointment & Serializable>
        extends ObjectToObjectLink<A, B> {
    A getCalendar();

    B getAppointment();
}
