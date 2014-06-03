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

package org.agatom.springatom.web.locale.aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.Arrays;

/**
 * <p>MessageSourceAOP class.</p>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
// TODO not working currently -> investigate
@Aspect(value = "messageSourceValidator")
public class MessageSourceAOP {

	private static final Logger LOGGER = Logger.getLogger(MessageSourceAOP.class);

	@Pointcut(value = "execution(* get*(..)) && bean(messageSource)")
	private void mainPointcut() {
	}

	@Pointcut(value = "args(keys,..)")
	private void keysAsArray(final String[] keys) {
	}

	@Pointcut(value = "args(key,..)")
	private void keyAsSingle(final String key) {
	}

	/**
	 * <p>validateMultipleKeys.</p>
	 *
	 * @param pjp  a {@link org.aspectj.lang.ProceedingJoinPoint} object.
	 * @param keys an array of {@link java.lang.String} objects.
	 *
	 * @return a {@link java.lang.Object} object.
	 *
	 * @throws java.lang.Throwable if any.
	 */
	@Around(value = "mainPointcut() && keysAsArray(keys)", argNames = "pjp,keys")
	public Object validateMultipleKeys(final ProceedingJoinPoint pjp, final String[] keys) throws Throwable {
		LOGGER.trace(String.format("/validateMultipleKeys %s [%s]", pjp.getSignature().getName(), Arrays.toString(keys)));
		return pjp.proceed();
	}

	/**
	 * <p>validateSingleKey.</p>
	 *
	 * @param pjp a {@link org.aspectj.lang.ProceedingJoinPoint} object.
	 * @param key a {@link java.lang.String} object.
	 *
	 * @return a {@link java.lang.Object} object.
	 *
	 * @throws java.lang.Throwable if any.
	 */
	@Around(value = "mainPointcut() && keyAsSingle(key)", argNames = "pjp,key")
	public Object validateSingleKey(final ProceedingJoinPoint pjp, final String key) throws Throwable {
		LOGGER.trace(String.format("/validateSingleKey %s [%s]", pjp.getSignature().getName(), key));
		return pjp.proceed();
	}
}
