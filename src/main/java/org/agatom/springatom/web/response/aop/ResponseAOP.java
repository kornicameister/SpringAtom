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

package org.agatom.springatom.web.response.aop;

import org.agatom.springatom.web.response.Response;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.joda.time.DateTime;
import org.joor.Reflect;

import java.util.concurrent.TimeUnit;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Aspect
public class ResponseAOP {

    @Pointcut("(target(org.agatom.springatom.web.response.Response) && getResponseCallMethod())")
    protected void responsePointcut() {
    }

    @Pointcut(value = "execution(* call*(..))")
    protected void asyncResponseCallMethod() {
    }

    @Pointcut(value = "execution(* getResponse(..))")
    protected void getResponseCallMethod() {
    }

    @Around("responsePointcut()")
    public Object doFillResponse(final ProceedingJoinPoint point) throws Throwable {
        Object retVal = null;
        boolean success = true;
        String msg = "Request execution normal";

        final long startTime = System.nanoTime();
        try {
            retVal = point.proceed();
        } catch (Throwable throwable) {
            success = false;
            msg = throwable.getMessage();
        }
        final long endTime = System.nanoTime() - startTime;


        Response response = null;
        if (retVal instanceof Response) {
            response = (Response) retVal;
        }

        if (response == null) {
            return retVal;
        }

        Reflect.on(response).set("timestamp", DateTime.now());
        Reflect.on(response).set("success", success);
        Reflect.on(response).set("message", msg);
        final Reflect reflect = Reflect.on(response).set("time", TimeUnit.NANOSECONDS.toMillis(endTime));

        return reflect.get();
    }

}
