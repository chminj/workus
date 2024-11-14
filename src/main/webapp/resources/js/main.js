$(function(){

    // fullcalendar (근태 페이지 기준)
    var atdCalendarEl = document.getElementById('fullCalendarInAtd');

    var atdCalendar = new FullCalendar.Calendar(atdCalendarEl, {
        initialView: 'dayGridMonth',
        selectable: false,
        locale: 'ko',
        buttonText: {
            today:    '오늘',
            month:    '월별',
            week:     '주별',
            day:      '일별',
        },
        aspectRatio: 2
    });
    atdCalendar.render();
    atdCalendar.updateSize();

});