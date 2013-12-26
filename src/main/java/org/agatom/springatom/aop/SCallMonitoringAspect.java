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

package org.agatom.springatom.aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.util.StopWatch;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Aspect(value = "callMonitor")
@ManagedResource("springatom:type=CallMonitor")
public class SCallMonitoringAspect {
    private static final Logger  LOGGER              = Logger.getLogger(SCallMonitoringAspect.class);
    private              boolean enabled             = true;
    private              int     callCount           = 0;
    private              long    accumulatedCallTime = 0;

    @ManagedAttribute
    public boolean isEnabled() {
        return enabled;
    }

    @ManagedAttribute
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @ManagedOperation
    public void reset() {
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("Resetting...");
        }
        this.callCount = 0;
        this.accumulatedCallTime = 0;
    }

    @ManagedAttribute
    public int getCallCount() {
        return callCount;
    }

    @ManagedAttribute
    public long getCallTime() {
        return (this.callCount > 0 ? this.accumulatedCallTime / this.callCount : 0);
    }

    @Around("within(@org.springframework.stereotype.Service *)")
    public Object invoke(ProceedingJoinPoint joinPoint) throws Throwable {
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace(String
                    .format("%s has status enabled=%s", SCallMonitoringAspect.class.getSimpleName(), Boolean
                            .toString(this.enabled)));
        }
        if (this.enabled) {
            StopWatch sw = new StopWatch(joinPoint.toShortString());

            sw.start("invoke");
            try {
                return joinPoint.proceed();
            } finally {
                sw.stop();
                synchronized (this) {
                    this.callCount++;
                    this.accumulatedCallTime += sw.getTotalTimeMillis();
                }
            }
        } else {
            return joinPoint.proceed();
        }
    }

}
