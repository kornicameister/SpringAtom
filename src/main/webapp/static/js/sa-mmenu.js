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

$(document).ready(function () {
    var $nav = $('nav#menu');
    $nav.mmenu({
        isMenu       : true,
        counters     : true,
        searchfield  : true,
        configuration: {
            selectedClass: 'active'
        }
    });
    $nav.on("opening.mm", function () {
        $('.mm-search').fadeToggle();
        $('.login-embedded-form').fadeToggle();
        $('.logout-embedded-form').fadeToggle();
    });
    $nav.on('closing.mm', function () {
        // close all opened submenus
        $('nav#menu .mm-submenu').each(function () {
            $(this).trigger("close.mm");
        });
        $('.mm-search').fadeToggle();
        $('.login-embedded-form').fadeToggle();
        $('.logout-embedded-form').fadeToggle();
    });
});
