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

package org.agatom.springatom.web.controller.remote;

import org.agatom.springatom.web.controller.remote.bean.SLog;
import org.agatom.springatom.web.controller.remote.bean.SLogs;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
@Controller
@RequestMapping(value = "/app/remote")
public class SRemoteLogController {
    private static final Logger LOGGER = Logger.getLogger(SRemoteLogController.class);

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @RequestMapping(
            value = "/log",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public void log(@RequestBody final SLogs logs) {
        for (final SLog log : logs) {
            final Logger logger = Logger.getLogger(log.getAppender());
            switch (log.getLevel()) {
                case "debug":
                    if (logger.isDebugEnabled()) {
                        logger.debug(log.getMsg());
                    }
                    break;
                case "trace":
                    if (logger.isTraceEnabled()) {
                        logger.trace(log.getMsg());
                    }
                    break;
                case "error":
                    logger.error(log.getMsg(), new SRemoteException(log.getClazz(), log.getMethod(), log
                            .getArguments()));
                    break;
                default:
                    if (logger.isInfoEnabled()) {
                        logger.info(log.getMsg());
                    }
                    break;
            }
        }
    }

}
