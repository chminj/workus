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
    // dialog close2 - outside click for close dialog (다시 해보기)
    // atdDialog.addEventListener('click', function (event) {
    //     const target = event.target;
    //     const rect = target.getBoundingClientRect();
    //     if (rect.left > event.clientX || rect.right < event.clientX ||
    //         rect.top > event.clientY || rect.bottom < event.clientY) {
    //         atdDialog.close();
    //     }
    // })
});