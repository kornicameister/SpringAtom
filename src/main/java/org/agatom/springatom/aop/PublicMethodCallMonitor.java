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

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * {@code SSystemArchitecture} is wrapper around any method call within particular relevant modules of the application.
 * What it does is simple intercept any method call, collect some data, and proceed to original method.
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Aspect
@SuppressWarnings("UnusedDeclaration")
public class PublicMethodCallMonitor {
    private static final Logger LOGGER = Logger.getLogger(PublicMethodCallMonitor.class);

	/**
	 * <p>publicMethodCall.</p>
	 *
	 * @param pjp a {@link org.aspectj.lang.ProceedingJoinPoint} object.
	 *
	 * @return a {@link java.lang.Object} object.
	 *
	 * @throws java.lang.Throwable if any.
	 */
	@Around("org.agatom.springatom.aop.SSharedPointcuts.inSpringAtom() " +
            "&& org.agatom.springatom.aop.SSharedPointcuts.publicMethod()")
    protected Object publicMethodCall(final ProceedingJoinPoint pjp) throws Throwable {
        return this.logMethodCallAround(pjp);
    }

    private Object logMethodCallAround(final ProceedingJoinPoint pjp) throws Throwable {
        long start = System.nanoTime();
        final Object retVal = pjp.proceed();
        long end = System.nanoTime() - start;

        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace(String.format(
                    "%s collected:\n\tsignature=%s,\n\targs=%s,\n\tkind=%s,\n\tsourceLocation=%s\n\tstaticPart=%s,\n\ttarget=%s,\n\treturns=%s,\n\ttime=%dms",
                    PublicMethodCallMonitor.class.getSimpleName(),
                    pjp.getSignature(),
                    Arrays.toString(pjp.getArgs()),
                    pjp.getKind(),
                    pjp.getSourceLocation(),
                    pjp.getStaticPart(),
                    pjp.getTarget(),
                    retVal,
                    TimeUnit.NANOSECONDS.toMillis(end)
            ));
        }

        return retVal;
    }
}
