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

package org.agatom.springatom.web.action;

import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Sets;
import org.agatom.springatom.web.action.model.Action;
import org.agatom.springatom.web.action.model.ActionModel;
import org.agatom.springatom.web.action.model.actions.LinkAction;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.springframework.http.MediaType;
import org.springframework.security.taglibs.TagLibConfig;
import org.springframework.security.taglibs.authz.JspAuthorizeTag;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.jsp.JspException;
import java.io.IOException;
import java.util.Set;

/**
 * {@code SecurityEvaluationActionModelTag} takes {@link org.agatom.springatom.web.action.model.ActionModel} and saves permitted
 * entries directly to the {@link javax.servlet.jsp.JspWriter}.
 *
 * <small>Class is a part of <b>SpringAtom</b> and was created at 15.07.14</small>
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class SecurityEvaluationActionModelTag
		extends JspAuthorizeTag {
	private static final Logger      LOGGER      = Logger.getLogger(SecurityEvaluationActionModelTag.class);
	private              ActionModel actionModel = null;
	private              String      var         = null;

	public ActionModel getActionModel() {
		return actionModel;
	}

	public void setActionModel(final ActionModel actionModel) {
		this.actionModel = actionModel;
	}

	@Override
	public int doStartTag() throws JspException {
		try {
			final Set<Action> filtered = FluentIterable
					.from(this.actionModel.getContent())
					.filter(new Predicate<Action>() {
						@Override
						public boolean apply(final Action input) {
							return authorize(input);
						}
					}).toSet();

			if (filtered.isEmpty() && TagLibConfig.isUiSecurityDisabled()) {
				this.pageContext.getOut().write(TagLibConfig.getSecuredUiPrefix());
			} else {
				if (this.getVar() != null) {
					LOGGER.trace(String.format("var(var=%s) is set, therefore putting back to pageContext", this.getVar()));
					this.pageContext.setAttribute(this.getVar(), filtered);
				} else {
					final String toString = new JSONArray(filtered).toString();
					this.pageContext.getResponse().setContentType(MediaType.APPLICATION_JSON_VALUE);
					this.pageContext.getOut().write(toString);
					this.pageContext.getOut().flush();
				}
			}

			return TagLibConfig.evalOrSkip(!filtered.isEmpty());

		} catch (IOException e) {
			throw new JspException(e);
		}
	}

	public boolean authorize(final Action action) {
		LOGGER.debug(String.format("authorize(action=%s)", action));
		boolean enabled = true;

		if (ClassUtils.isAssignableValue(LinkAction.class, action)) {
			final LinkAction linkAction = (LinkAction) action;
			if (linkAction.getSecurity().isEnabled()) {
				final String url = StringUtils.hasText(linkAction.getSecurity().getPattern()) ? linkAction.getSecurity().getPattern() : linkAction.getUrl();
				final Set<String> roles = linkAction.getSecurity().getRoles();
				try {
					if (CollectionUtils.isEmpty(roles)) {
						this.setUrl(url);
					} else {
						this.setIfAnyGranted(StringUtils.collectionToDelimitedString(roles, ","));
					}
					enabled = this.authorize();
				} catch (Exception exp) {
					enabled = false;
					this.setUrl(null);
					this.setIfAnyGranted(null);
				}
			}
		} else if (ClassUtils.isAssignableValue(ActionModel.class, action)) {
			final ActionModel actionModel = (ActionModel) action;
			final Set<Action> content = actionModel.getContent();
			final Set<Action> contentFiltered = Sets.newTreeSet();
			for (final Action localAction : content) {
				if (this.authorize(localAction)) {
					contentFiltered.add(localAction);
				} else {
					LOGGER.trace(String.format("ActionModel(id=%s)/Action(id=%s) is not authorized", actionModel.getId(), action.getId()));
				}
			}
			if (contentFiltered.isEmpty()) {
				LOGGER.trace(String.format("For ActionModel(id=%s) all actions are not authorized, excluding the model", actionModel.getId()));
				enabled = false;
			} else {
				actionModel.setContent(contentFiltered);
			}
		}

		// by default enable, because if there not no SecurityCheck it means action is globally available
		return enabled;
	}

}
