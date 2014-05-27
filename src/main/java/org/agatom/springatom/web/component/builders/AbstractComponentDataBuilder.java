/**************************************************************************************************
 * This file is part of [SpringAtom] Copyright [kornicameister@gmail.com][2014]                   *
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

package org.agatom.springatom.web.component.builders;

import org.agatom.springatom.web.component.builders.exception.ComponentException;
import org.agatom.springatom.web.component.data.ComponentDataRequest;
import org.agatom.springatom.web.component.data.ComponentDataResponse;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import java.util.concurrent.TimeUnit;

/**
 * {@code AbstractComponentDataBuilder} is a <b>component builder</b> that can be extended
 * for builder returning nothing but data.
 * <p/>
 * <small>Class is a part of <b>SpringAtom</b> and was created at 27.05.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
abstract public class AbstractComponentDataBuilder
		extends AbstractBuilder
		implements ComponentDataBuilder {

	@Override
	public final ComponentDataResponse<?> getData(final ComponentDataRequest dataRequest) throws ComponentException {
		this.logger.debug(String.format("getData(dataRequest=%s)", dataRequest));
		try {
			Assert.notNull(dataRequest, "DataRequest can not be null");

			final long startTime = System.nanoTime();
			final ComponentDataResponse<?> response = this.buildData(dataRequest);

			if (response == null) {
				this.logger.warn(String.format("For request=%s, builder returned null response", dataRequest));
			} else {
				this.logger.trace(String.format("For request=%s, builder returned response=%s", dataRequest, response));
			}

			this.logger.info(String.format("getData(dataRequest=%s) executed in %dms", dataRequest, TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime)));

			return response;
		} catch (Exception exp) {
			this.logger.fatal(String.format("getData(dataRequest=%s) failed...", dataRequest), exp);
			if (ClassUtils.isAssignableValue(ComponentException.class, exp)) {
				throw exp;
			}
			throw new ComponentException(exp);
		}
	}

	/**
	 * Implement this method to return {@link org.agatom.springatom.web.component.data.ComponentDataResponse} of this <b>component builder</b>
	 *
	 * @param dataRequest request to work with
	 *
	 * @return response for this builder
	 *
	 * @throws ComponentException if any
	 */
	protected abstract ComponentDataResponse<?> buildData(final ComponentDataRequest dataRequest) throws ComponentException;
}
