/*
 * This file is part of [SpringAtom] Copyright [kornicameister@gmail.com][2014]
 *
 * [SpringAtom] is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * [SpringAtom] is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with [SpringAtom].  If not, see <http://www.gnu.org/licenses/gpl.html>.
 */

/**
 * Created by kornicameister on 18.03.14.
 */

(function (SA, $) {

	if (!SA.calendar) {
		SA.calendar = {};
	}

	var wizardHref = '/app/wizard/NewAppointmentWizard',
		requiredProperties = ['_embedded'],
		mappingProperties = {
			_embedded: 'data',
			_links   : 'links'
		},
		propertiesToRemove = ['begin', 'end', 'messageKey', 'new'],
		rpHandlers = {
			_embedded: function (value) {
				if (jQuery.isPlainObject(value)) {
					if (value['appointments']) {
						value = value['appointments'];
					}
				}
				if (jQuery.isArray(value)) {
					var arr = [];
					jQuery.each(value, function (index, val) {
						val = propertiesRemover.apply(this, [val]);
						if (val) {
							arr.push(val);
						}
						if (val['_links']) {
							val[mappingProperties['_links']] = val['_links'];
							delete val['_links'];
						}
					});
					return arr;
				}
				return undefined;
			}
		},
		propertiesRemover = function (value) {
			if (jQuery.isPlainObject(value)) {
				jQuery.each(propertiesToRemove, function (index, prop) {
					if (value[prop]) {
						delete value[prop];
					}
				});
				return value;
			} else {
				throw new Error('Invalid argument exception [value=' + value + '] is not object');
			}
		},
		getAppointmentIdFromLink = function (infoPageLink) {
			var index = infoPageLink.lastIndexOf('/');
			infoPageLink = infoPageLink.substring(index + 1);
			return infoPageLink;
		},
		getEventTitle = function (carLink, infoPageLink) {
			var title = '???';
			$.ajax({
				url        : carLink,
				type       : 'get',
				contentType: "application/json",
				dataType   : 'json',
				async      : false,
				success    : function (data) {
					title = '[' + getAppointmentIdFromLink.apply(this, [infoPageLink]) + ' / ' + data['version'] + '] ' + data['licencePlate'];
				}
			});
			return title;
		},
		isValidResponse = function (eventData) {
			return typeof eventData['self'] === 'undefined';
		},
		successLoad = function (args) {
			if (jQuery.isPlainObject(args)) {
				// proceeding
				var ret = {};
				jQuery.each(args, function (key, value) {
					if (jQuery.inArray(key, requiredProperties) !== -1) {
						ret[mappingProperties[key]] = rpHandlers[key].apply(this, [value]);
					} else {
						console.log('Key => ' + key + ' not in ' + requiredProperties);
					}
				});
				if (!ret['data']) {
					return [];
				}
				return ret['data'];
			}
			return [];
		},
		eventDataTransform = function (eventData) {
			if (!isValidResponse(eventData)) {
				console.log('Invalid eventData...return');
				return undefined;
			}
			var begin = eventData['beginTs'],
				end = eventData['endTs'],
				urlInfopage = '',
				urlCar = '';

			$.each(eventData['links'], function (rel, value) {
				if (rel === 'infoPage') {
					urlInfopage = value['href'];
				}
				if (rel === 'car') {
					urlCar = value['href'];
				}
			});

			return {
				id    : eventData['id'],
				start : begin,
				end   : end,
				allDay: eventData['allDay'],
				url   : urlInfopage,
				links : eventData['links'],
				title : getEventTitle.apply(this, [urlCar, urlInfopage])
			}
		},
		launchWizardOnSelect = function (start, end, allDay, jsEvent, view) {
			start = moment.utc(start.toJSON()).valueOf();
			end = moment.utc(end.toJSON()).valueOf();

			Spring.remoting.getLinkedResource('calendarComponentHref', {
				begin   : start,
				end     : end,
				allDay  : allDay,
				view    : view['name'],
				popup   : true,
				mode    : 'embedded',
				action  : 'create',
				calendar: true
			}, true);
		},
		preProcessConfig = function (config, calendar) {
			if (config) {
				var wizardHref = config['wizardHref'];

				// create new a element
				var wizardHrefA = $('<a>_calendar_</a>');
				wizardHrefA.attr('href', wizardHref)
					.attr('style', 'visibility:hidden')
					.attr('id', 'calendarComponentHref');
				calendar.append(wizardHrefA);
				// create new a element

				delete config['wizardHref'];
			}
			return config;
		},
		postProcessConfig = function (config, defConfig) {
			var eventSources = defConfig['eventSources'];
			if (jQuery.isArray(eventSources)) {
				var tmp = eventSources[0],
					tmp2 = config['eventSources'][0];
				tmp = jQuery.extend(tmp, tmp2);
				delete config['eventSources'];
				eventSources[0] = tmp;
			}
			return jQuery.extend(defConfig, config);
		};

	SA.calendar.createCalendar = function (target, config) {
		var $calendar = $(target),
			pHeight = $calendar.height(),
			defConfig = {
				header             : {
					left  : 'prev,next today',
					center: 'title',
					right : 'month,agendaWeek,agendaDay'
				},
				eventSources       : [
					{
						cache             : true,
						data              : {
							sort: 'begin'
						},
						eventDataTransform: eventDataTransform,
						success           : successLoad
					}
				],
				select             : launchWizardOnSelect,
				defaultEventMinutes: 30,
				weekends           : false,
				weekNumbers        : true,
				firstDay           : 1,
				height             : pHeight,
				aspectRatio        : 2.2,
				firstHour          : 8,
				minTime            : 7,
				maxTime            : 21,
				selectHelper       : true,
				selectable         : true,
				editable           : true
			};
		config = preProcessConfig(config, $calendar);
		config = postProcessConfig(config, defConfig);
		$calendar.fullCalendar(config);
	}


}(window.SA = window.SA || {}, jQuery));