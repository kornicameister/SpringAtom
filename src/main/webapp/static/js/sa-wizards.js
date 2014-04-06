/*
 * This file is part of [SpringAtom] Copyright [kornicameister@gmail.com][2013]
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

(function (SA, $) {

	if (!SA.wizard) {
		SA.wizard = {};
	}

	SA.wizard.Helpers = {};

	var helper = {};
	helper.setEntities = function (data) {
		console.log(data);
		helper.entities = (function () {
			var raw = jQuery.parseJSON(data);
			var parsedData = {};
			$.each(raw, function (it, value) {
				parsedData[value['master']] = value['children'];
			});
			return parsedData;
		}());
	};
	/**
	 * {@code onEntityPickRecalculateAssociation} is a handler for {@event dijit.form.MultiSelect#onChange}.
	 * Method hides entries from associated form select element that can not be
	 * linked (in further querying) with tables selected at the moment.
	 *
	 * @param selectedEntries - currently selected entities
	 */
	helper.onEntityPickRecalculateAssociation = function (selectedEntries) {

		/**
		 * Calculates map of children for selected entries, hence the options element in DOM
		 * that will remain visible
		 *
		 * @returns {{}}
		 */
		var getAssociations = function () {
			var entities = helper.entities,
				map = {};
			$.each(selectedEntries, function (index, value) {
				var tmp = entities[value];
				if (tmp) {
					map[value] = tmp;
				}
			});
			return map;
		};
		var getExpandedAssociations = function () {
			var array = [];
			$.each(associations, function (master, children) {
				$.map(children, function (value) {
					if (jQuery.inArray(array, value) === -1) {
						array.push(value);
					}
				})
			});
			return array;
		};
		/**
		 *  Returns true if given option element was just selected
		 * @returns {boolean} true if selected, false otherwise
		 */
		var getSelectedOptions = function () {
			var option = $(this),
				value = option.attr('value');
			return jQuery.inArray(value, selectedEntries) !== -1;
		};
		/**
		 * Method resolves whether or not selected entry can be associated with the rest of selected entries
		 *
		 * @returns {boolean} true if it possible, false otherwise
		 */
		var getNotInAssociation = function () {
			var option = $(this),
				value = option.attr('value');
			return jQuery.inArray(parseInt(value), expandedAssociations) === -1;
		};

		if (!jQuery.isArray(selectedEntries)) {
			selectedEntries = [selectedEntries];
		}

		console.log('onEntityPickRecalculateAssociation with selectedEntries >>>' + JSON.stringify(selectedEntries));
		var selectBox = $('#' + this.id),
			entries = selectBox.find('option'),
			associations = getAssociations(),
			expandedAssociations = getExpandedAssociations();

		var selected = entries.filter(getSelectedOptions),
			notSelected = entries.not(selected),
			notInAssociation = notSelected.filter(getNotInAssociation),
			inAssociation = notSelected.not(notInAssociation);

		// hide selected
		if (notInAssociation) {
			$.each(notInAssociation, function () {
				$(this).attr('disabled', 'disabled');
			});
		}
		// show notSelected
		if (inAssociation) {
			$.each(inAssociation, function () {
				$(this).removeAttr('disabled');
			});
		}

	};
	helper.onEntityPickResetAll = function () {
		var selectBox = $('#' + this.id),
			entries = selectBox.find('option');
		$.each(entries, function () {
			var entry = $(this);
			if (jQuery.type(entry.attr('disabled')) !== 'undefined') {
				entry.removeAttr('disabled');
			}
		})
	};
	helper.onColumnOrderChange = function (row, input, direction) {
		console.log('Row=', row);
		console.log('Input=', input);
		console.log('Direction=', direction);

		var allRows = row.parent().children('tr'),
			expectSelectedRows = allRows.not(row),
			rowId = row.attr('id'),
			allRowsIds = (function () {
				var tmpArray = [];
				$.each(allRows, function (key, item) {
					tmpArray.push($(item).attr('id'));
				})
				return tmpArray;
			}()),
			indexOfThisRow = jQuery.inArray(row.attr('id'), allRowsIds),
			canBeMovedInDirection = false,
			rowToSwapWith = undefined,
			rowToSwapWithInput = undefined;

		// depending on the given direction, recognize if its possible to move the row there
		if (direction === 'down') {
			canBeMovedInDirection = indexOfThisRow === 0 || indexOfThisRow < allRowsIds.length - 1;
		} else if (direction === 'up') {
			canBeMovedInDirection = indexOfThisRow === allRowsIds.length - 1 || indexOfThisRow > 0;
		} else {
			throw new Error('Invalid direction, null or undefined');
		}

		if (canBeMovedInDirection) {
			console.log('Row id=', rowId, ' will be moved in direction=', direction);
		} else {
			console.log('Row id=', rowId, ' cant be moved in direction=', direction);
			return;
		}

		// get nearest possible next tr to swap with
		if (direction === 'down') {
			rowToSwapWith = $('#' + allRowsIds[indexOfThisRow + 1]);
		} else {
			rowToSwapWith = $('#' + allRowsIds[indexOfThisRow - 1]);
		}


		console.log('Swapping with row with id=', $(rowToSwapWith).attr('id'));
		rowToSwapWithInput = rowToSwapWith.children().first().children('input');

		if (direction === 'down') {
			input.attr('value', indexOfThisRow + 1);
			var value = indexOfThisRow;
			if (value < -1) {
				value = 0;
			}
			rowToSwapWithInput.attr('value', value);
		} else {
			input.attr('value', indexOfThisRow - 1);
			value = indexOfThisRow + 1;
			rowToSwapWithInput.attr('value', value);
		}

		if (direction === 'down') {
			$(row).before($(rowToSwapWith));
		} else {
			$(rowToSwapWith).before($(row));
		}
	};
	SA.wizard.Helpers.NewReportWizard = helper;

	SA.wizard.Helpers.NewAppointmentWizard = {
		handleAddRemoveTask: function (tasks) {
			var buttons = tasks.find('a[href=#]'),
				it = 1;
			if (buttons) {
				buttons.each(function (index, button) {
					button = $(button);
					var id = button.attr('id'),
						handler = undefined,
						addHandler = function (event) {
							event.stopPropagation();
							var target = $(event.target);
							var cloned = target.parent('li').clone(true);
							if (!cloned) {
								throw new Error('Failed to get DOM to clone');
							}
							cloned.find('a#mv-add').remove();
							cloned.find('a#mv-remove').click(removeHandler);
							cloned.attr('id', it++);
							clear(cloned);
							tasks.append(cloned);
							recalculateInputIds(target.parents('ul'));
						},
						removeHandler = function (event) {
							event.stopPropagation();

							var target = $(event.target);
							target.click(undefined);

							var parent = target.parent('li');
							var ulElement = target.parents('ul');
							if (ulElement.find('li').length !== 1) {
								parent.remove();
							}
							recalculateInputIds(ulElement);
						},
						clear = function (cloned) {
							$(cloned).children().each(function () {
								var a = $(this);
								if (a.is('input') || a.is('textarea')) {
									a.val('');
								} else if (a.is('select')) {
									a.val(a.select('option:first').val());
								}
							})
						},
						recalculateInputIds = function (ulElement) {
							var liElements = ulElement.find('li'),
								id = 0;

							liElements.each(function (index, li) {
								li = $(li);
								var lis = li.find('.x-input'),
									incrementValue = lis.length;
								lis.each(function (index, input) {
									input = $(input);

									var prefixMatcher = new RegExp('^\\w+', 'gi'),
										suffixMatcher = new RegExp('\\.\\w+$', 'gi')

									var oldName = input.attr('name');

									var prefix = prefixMatcher.exec(oldName);
									var suffix = suffixMatcher.exec(oldName);

									var newName = prefix + '[' + Math.floor(((id++) / incrementValue)) + ']' + suffix;

									input.attr('name', newName);
								});
							});
						};

					if (id === 'mv-add') {
						handler = addHandler;
					} else if (id === 'mv-remove') {
						handler = removeHandler;
					}

					button.click(handler);
				})
			} else {
				console.log('Failed to obtain list of buttons');
			}
		}
	};

	SA.wizard.NewUserWizard = {};
	SA.wizard.NewUserWizard.onOwnerChange = function (event, owners) {
		if (!event) {
			console.log('Invalid argument supplied, returning');
		}
		var ownerFound = undefined;
		jQuery.each(owners, function (index, owner) {
			if (owner['ownerId'] === parseInt(event)) {
				ownerFound = owner;
				return false;
			}
		});
		if (ownerFound) {
			var cars = ownerFound['carsMap'],
				message = '';
			jQuery.each(cars, function (index, value) {
				message += '<p>' + value['brandModel'] + '[' + value['licencePlate'] + ']' + '</p>';
			});
		}
		if (message && message.length > 0) {
			alertify.log(message, '', 5000);
		}
	};

	$.valHooks.textarea = {
		get: function (elem) {
			return elem.value.replace(/\r?\n/g, "\r\n");
		}
	};

	/**
	 * //TODO missing jsDoc
	 * @param cfg
	 */
	SA.wizard.applyActionVisibility = function (cfg) {
		var actionsContainer = cfg['container'];
		var actionsElement = cfg['selector'] || 'button';
		var availableActions = cfg['actions'];
		var firstStep = cfg['first'] || false;
		var actions = {};

		$.each(availableActions, function (it, val) {
			actions[val] = true;
		});

		var buttons = $(actionsContainer).find(actionsElement);
		$.each(buttons, function (it, val) {
			var buttonAsJQ = $(val);
			var buttonId = buttonAsJQ.attr('id');
			if (actions[buttonId]) {
				buttonAsJQ.fadeIn();
			} else {
				if (!firstStep) {
					buttonAsJQ.fadeOut();
				} else {
					buttonAsJQ.hide();
				}
			}
		});
	};

	SA.wizard.applyDynamicActions = function (cfg) {
		var actionsContainer = cfg['container'];
		var availableActions = cfg['actions'];
		var formId = cfg['formId'];
		var actions = {};

		var buttons = $(actionsContainer);
		if (buttons.find('.x-wizard-dynamic-action').length > 1) {
			buttons.find('.x-wizard-dynamic-action').remove();
		}

		$.each(availableActions, function (it, val) {
			var button = $('<button></button>');
			button = button.html(val['labelName']);
			button = button
				.attr('id', val['eventName'])
				.attr('type', 'submit')
				.attr('name', val['eventName'])
				.attr('class', 'x-wizard-action x-wizard-dynamic-action');
			buttons.prepend(
				button
			);
			if (val['finish']) {
				applyFinishActionDecorator(button.attr('id'), formId);
			} else {
				applyActionDecorator(button.attr('id'), formId);
			}
		});
	};

	/**
	 * //TODO missing jsDoc
	 * @param cfg
	 */
	SA.wizard.applyStepsState = function (cfg) {
		var state = SA.wizard.genStepHeaderId(cfg['stateId']);
		var descriptor = cfg['descriptor'];
		var header = $(cfg['headerSelector']);

		var stateSpanNode = $(header.find('li').find('span#' + state));
		stateSpanNode.removeClass('disabled');
		stateSpanNode.removeClass('enabled');
		stateSpanNode.removeClass('done');
		stateSpanNode.removeClass('error');
		stateSpanNode.addClass('selected');

		$.each(descriptor['predecessors'], function (it, predecessorSpan) {
			var el = $('span#' + SA.wizard.genStepHeaderId(predecessorSpan));
			el.removeClass('selected');
			el.addClass('done');
		});
		$.each(descriptor['successors'], function (it, successorsSpan) {
			var el = $('span#' + SA.wizard.genStepHeaderId(successorsSpan));
			el.removeClass('disabled');
			el.addClass('enabled');
		});

	};

	SA.wizard.genStepHeaderId = function (val) {
		return SA.core.genId(val, 'wiz-step');
	};

	SA.wizard.applyWebFlowDecorators = function (el, formId) {
		console.log('Applying WF decorator over elements > ' + el);
		$.each(el, function (index, val) {
			console.log('Applying WF decorator over > ' + $('#' + val).attr('id'));
			applyFinishActionDecorator(val, formId);
		});
		var not = $('.x-wizard-actions').find('button').not(function (index) {
			var id = $(this).attr('id');
			var match = false;
			$.each(el, function (index, val) {
				if (id === val) {
					match = true;
					return false;
				}
				return true;
			});
			return match;
		});
		console.log('Applying AWF decorator over elements > ' + not);
		not.each(function (index, val) {
			console.log('Applying WF decorator over > ' + $(val).attr('id'));
			applyActionDecorator($(val).attr('id'), formId);
		})
	};

	SA.wizard.decorateWizard = function (cfg) {
		var wizardId = cfg['wizardId'];
		var wizardHeaderId = cfg['wizardHeaderId'];
		var wizardContentId = cfg['wizardContentId'];
		var wizardActionsId = cfg['wizardActionsId'];

		var decorations = [];

		decorations.push(new Spring.ElementDecoration({
			elementId  : wizardId,
			widgetType : "dijit.layout.BorderContainer",
			widgetAttrs: {
				title        : $('#' + wizardId).attr('title'),
				gutters      : true,
				liveSplitters: true
			}
		}));
		decorations.push(new Spring.ElementDecoration({
			elementId  : wizardHeaderId,
			widgetType : "dijit.layout.ContentPane",
			widgetAttrs: {
				region: 'leading'
			}
		}));
		decorations.push(new Spring.ElementDecoration({
			elementId  : wizardContentId,
			widgetType : "dijit.layout.ContentPane",
			widgetAttrs: {
				region: 'center'
			}
		}));
		decorations.push(new Spring.ElementDecoration({
			elementId  : wizardActionsId,
			widgetType : "dijit.layout.ContentPane",
			widgetAttrs: {
				region: 'bottom'
			}
		}));

		console.log(wizardId + ' with ' + decorations.length + ' decorations');

		$.each(decorations, function (index, val) {
			Spring.addDecoration(val);
		});
	};

	function applyActionDecorator(val, formId) {
		Spring.addDecoration(new Spring.AjaxEventDecoration({
			elementId: val,
			event    : 'onclick',
			formId   : formId,
			popup    : true,
			params   : {
				fragments: 'wiz.content',
				mode     : "embedded"
			}
		}));
	}

	function applyFinishActionDecorator(val, formId) {
		Spring.addDecoration(new Spring.AjaxEventDecoration({
			elementId: val,
			event    : 'onclick',
			formId   : formId
		}));
	}


	SA.wizard.renderNotification = function (cfg) {
		var stepHeader = $(cfg['stepHeader']),
			messages = cfg['messages'],
			errors = cfg['errors'] || [] ,
			severityIconMapping = cfg['severityIconMapping'],
			severityWeight = cfg['severityWeight'],
			hasError = false,
			promptMessage = '' || cfg['promptMessage'];

		jQuery.each(errors, function (it, value) {
			messages.push({
				severity     : 'ERROR',
				text         : value['defaultMessage'],
				rejectedValue: value['rejectedValue']
			});
		});

		if (messages && jQuery.isArray(messages) && messages.length > 0) {
			var formErrorsLi = $('<ul class="fa-ul">');

			jQuery.each(messages, function (it, value) {
				var cc = $('<li>')
					.append($('<i class="' + severityIconMapping[value['severity']] + '">'))
					.append(value['text'])
					.attr('severityWeight', severityWeight[value['severity']])
					.append(
					(function () {
						if (value['rejectedValue']) {
							return ' ' + value['rejectedValue'] + '<<< ';
						}
						return '';
					}())
				);
				formErrorsLi = formErrorsLi.append(cc);
				if (value['severity'] === 'ERROR' && !hasError) {
					hasError = true;
				}
			});

			if (hasError) {
				stepHeader.addClass('error');
			}

			var text = $('<p style="width:100%">');
			$(formErrorsLi)
				.sort(function (a, b) {
					a = $(a);
					b = $(b);
					return a.attr('severityWeight') > b.attr('severityWeight') ? 1 : -1;
				})
				.appendTo(text);
			if (text) {
				alertify.alert($('<p>').text(promptMessage).html());
				$('.alertify-message').append(text);
			}
		}
	};

}(window.SA = window.SA || {}, jQuery));


