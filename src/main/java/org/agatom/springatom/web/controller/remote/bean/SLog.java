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

package org.agatom.springatom.web.controller.remote.bean;

import java.io.Serializable;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class SLog
        implements Serializable {
    private String  msg;
    private String  appender;
    private String  level;
    private String  exception;
    private boolean sent;

    public SLog() {
    }

    public SLog(final String msg, final String appender, final String level, final String exception, final boolean sent) {
        this.msg = msg;
        this.appender = appender;
        this.level = level;
        this.sent = sent;
        this.exception = exception;
    }

    public String getMsg() {
        return msg;
    }

    public String getAppender() {
        return appender;
    }

    public String getLevel() {
        return level;
    }

    public String getException() {
        return exception;
    }

    public boolean isSent() {
        return sent;
    }

    @Override
    public String toString() {
        return "SLog{" +
                "msg='" + msg + '\'' +
                ", appender='" + appender + '\'' +
                ", level='" + level + '\'' +
                ", sent=" + sent +
                "} " + super.toString();
    }
}
