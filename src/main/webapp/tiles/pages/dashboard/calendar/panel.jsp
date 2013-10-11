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
</section>
<script>
    $(document).ready(function () {
        function showSpinner() {
            spinner.spin(document.getElementById('page'));
        }

        function hideSpinner() {
            spinner.stop();
        }

        function loadUI() {
            var options = {
                'monthNames'     : 'date.months',
                'dayNames'       : 'date.days',
                'dayNamesShort'  : 'date.days.short',
                'monthNamesShort': 'date.months.short'
            };
            var initCalendar = function (opt) {
                var $calendar = $('#calendar'),
                        parent = $calendar.parent('.x-calendar'),
                        pHeight = parent.height();

                opt = $.extend({
                    header     : {
                        left  : 'prev,next today',
                        center: 'title',
                        right : 'month,agendaWeek,agendaDay'
                    },
                    weekends   : false,
                    weekNumbers: true,
                    editable   : true,
                    firstDay   : 1,
                    height     : pHeight,
                    aspectRatio: 2.2
                }, opt);

                $calendar.fullCalendar(opt);
                hideSpinner();
            };

            $.ajax({
                url        : '/data/lang/read',
                type       : 'POST',
                contentType: "application/json",
                data       : JSON.stringify({
                    keys: (function () {
                        var tmp = [];
                        $.each(options, function (key, value) {
                            tmp.push(value);
                        });
                        return tmp;
                    }())
                }),
                dataType   : 'json',
                success    : function (data) {
                    var preferences = data['preferences'];
                    $.each(preferences, function (index, pref) {
                        var _key = pref['key'], _message = pref['message'];
                        $.each(options, function (key, value) {
                            if (value === _key) {
                                options[key] = _message.split(',');
                            }
                        });
                    });
                    initCalendar(options);
                }
            });
        }

        function loadCalendar() {
            showSpinner();
            loadUI();
        }

        var opts = {
            lines    : 11,
            length   : 28,
            width    : 8,
            radius   : 24,
            corners  : 1,
            rotate   : 90,
            direction: 1,
            color    : '#246485', // #rgb or #rrggbb or array of colors
            speed    : 0.7, // Rounds per second
            trail    : 100, // Afterglow percentage
            shadow   : true, // Whether to render a shadow
            hwaccel  : true, // Whether to use hardware acceleration
            zIndex   : 2e9, // The z-index (defaults to 2000000000)
            className: 'spinner', // CSS class to assign to the element
            top      : 'auto',          // center vertically
            left     : 'auto',         // center horizontally
            position : 'relative'  // element position
        };
        var spinner = new Spinner(opts);
        loadCalendar();
    });
</script>