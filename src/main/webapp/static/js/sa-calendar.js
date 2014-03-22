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
		};

	SA.calendar.successLoad = function (args) {
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
	};
	SA.calendar.eventDataTransform = function (eventData) {
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
			allDay: false,
			url   : urlInfopage,
			links : eventData['links'],
			title : getEventTitle.apply(this, [urlCar, urlInfopage])
		}
	};
	SA.calendar.launchWizardOnSelect = function (start, end, allDay, jsEvent, view) {
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
	}

}(window.SA = window.SA || {}, jQuery));