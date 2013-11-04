<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="s" uri="http://java.sun.com/jsp/jstl/core" %>
<%--~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  ~ This file is part of [SpringAtom] Copyright [kornicameister@gmail.com][2013]                 ~
  ~                                                                                              ~
  ~ [SpringAtom] is free software: you can redistribute it and/or modify                         ~
  ~ it under the terms of the GNU General Public License as published by                         ~
  ~ the Free Software Foundation, either version 3 of the License, or                            ~
  ~ (at your option) any later version.                                                          ~
  ~                                                                                              ~
  ~ [SpringAtom] is distributed in the hope that it will be useful,                              ~
  ~ but WITHOUT ANY WARRANTY; without even the implied warranty of                               ~
  ~ MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the                                ~
  ~ GNU General Public License for more details.                                                 ~
  ~                                                                                              ~
  ~ You should have received a copy of the GNU General Public License                            ~
  ~ along with [SpringAtom].  If not, see <http://www.gnu.org/licenses/gpl.html>.                ~
  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~--%>

<section class="x-calendar">
    <div id="calendar"></div>
    <div id="x-new-event" class="x-modal">
    </div>
</section>
<script>
    $(document).ready(function () {
        function onAjaxSuccess(opt) {
            var $calendar = $('#calendar'),
                    parent = $calendar.parent('.x-calendar'),
                    pHeight = parent.height();
            opt = $.extend({
                header      : {
                    left  : 'prev,next today',
                    center: 'title',
                    right : 'month,agendaWeek,agendaDay'
                },
                weekends    : false,
                weekNumbers : true,
                firstDay    : 1,
                height      : pHeight,
                aspectRatio : 2.2,
                firstHour   : 8,
                minTime     : 7,
                maxTime     : 21,
                selectHelper: true,
                selectable  : true,
                editable    : true,
                dayClick    : function (date, allDay, jsEvent, view) {
                    $.ajax({
                        url    : '/sa/wizard/NewAppointment',
                        type   : 'POST',
                        success: function (data) {
                            $('#x-new-event').html(data);
                            SA.core.openModal('#x-new-event');
                        }
                    });
                },
                eventClick  : function (calEvent, jsEvent, view) {
                    console.log('Event: ' + calEvent.title);
                    console.log('Coordinates: ' + jsEvent.pageX + ',' + jsEvent.pageY);
                    console.log('View: ' + view.name);
                }
            }, opt);
            $calendar.fullCalendar(opt);
        }

        function loadCalendarAjaxly() {
            SA.core.ajax.loadLocalizedPreferences({
                keys    : {
                    'monthNames'     : 'date.months',
                    'dayNames'       : 'date.days',
                    'dayNamesShort'  : 'date.days.short',
                    'monthNamesShort': 'date.months.short'
                },
                pattern : false,
                reversed: true,
                callback: onAjaxSuccess,
                scope   : this
            });
        }

        loadCalendarAjaxly();
    });
</script>