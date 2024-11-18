$(function(){

    // fullcalendar (근태 페이지 기준)
    let atdCalendarEl = document.getElementById('fullCalendarInAtd');
    let atdCalendar = new FullCalendar.Calendar(atdCalendarEl, {
        initialView: 'dayGridMonth',
        selectable: false,
        locale: 'ko',
        buttonText: {
            today:    '오늘',
            month:    '월별',
            week:     '주별',
            day:      '일별',
        },
        aspectRatio: 2,
        displayEventTime: false,
        googleCalendarApiKey: 'AIzaSyDcnW6WejpTOCffshGDDb4neIrXVUA1EAE',
        events: {
            googleCalendarId : 'ko.south_korea.official#holiday@group.v.calendar.google.com',
            className : 'official-holiday'
        },
        eventSources:[
            {
            }
        ],
        eventClick: function(info){
            //클릭시 구글캘린더 url로 가는 것 막음
            info.jsEvent.stopPropagation();
            info.jsEvent.preventDefault();
        }
    });
    atdCalendar.render();
    atdCalendar.updateSize();


    /* attendance */
    // dialog modal
    let atdDialog = document.getElementById('atdRequestForm');
    let openButton = document.getElementById('atdApplyBtnForModal')

    // dialog open
    openButton.addEventListener('click', () => {
        atdDialog.showModal();
    })

    // dialog close
    let closeButton = document.querySelector('#atdRequestForm button.close')
    closeButton.addEventListener('click', () => {
        atdDialog.close();
    })
    /* // attendance */
});