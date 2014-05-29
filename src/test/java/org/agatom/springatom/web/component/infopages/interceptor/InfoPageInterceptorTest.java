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

package org.agatom.springatom.web.component.infopages.interceptor;

import org.agatom.springatom.AbstractSpringTestCase;
import org.agatom.springatom.web.component.infopages.InfoPageConstants;
import org.agatom.springatom.web.component.infopages.elements.InfoPageComponent;
import org.agatom.springatom.web.component.infopages.link.InfoPageRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.hateoas.Link;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.util.ClassUtils;
import org.springframework.web.servlet.ModelAndView;

/**
 * <small>Class is a part of <b>SpringAtom</b> and was created at 18.05.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class InfoPageInterceptorTest
		extends AbstractSpringTestCase {

	private MockHttpServletResponse response = null;
	private MockHttpServletRequest  request  = null;
	private ModelAndView            mav      = null;

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();
		this.request = this.initializeRequest(new MockHttpServletRequest());
		this.response = new MockHttpServletResponse();
		this.mav = new ModelAndView(ClassUtils.getShortName(this.getClass()));
	}

	private MockHttpServletRequest initializeRequest(final MockHttpServletRequest request) {
		request.setRequestURI("/app/cmp/ip/appointment/1");
		return request;
	}

	@Test
	@Repeat(2)
	public void testPreHandle() throws Exception {
		final InfoPageInterceptor ipi = (InfoPageInterceptor) this.wac.getBean("infoPageInterceptor");
		Assert.assertTrue(ipi.preHandle(this.request, this.response, null));
	}

	@Test
	@Repeat(2)
	public void testPostHandle() throws Exception {
		final InfoPageInterceptor ipi = (InfoPageInterceptor) this.wac.getBean("infoPageInterceptor");
		ipi.postHandle(this.request, this.response, null, this.mav);
		this.validate();
	}

	private void validate() {
		ModelAndViewAssert.assertAndReturnModelAttributeOfType(this.mav, InfoPageConstants.INFOPAGE_DS, Link.class);
		ModelAndViewAssert.assertAndReturnModelAttributeOfType(this.mav, InfoPageConstants.INFOPAGE_PAGE, InfoPageComponent.class);
		ModelAndViewAssert.assertAndReturnModelAttributeOfType(this.mav, InfoPageConstants.INFOPAGE_REQUEST, InfoPageRequest.class);
		ModelAndViewAssert.assertModelAttributeValue(this.mav, InfoPageConstants.INFOPAGE_AVAILABLE, true);
	}
}
