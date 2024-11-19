$(function(){

    // fullcalendar (근태 페이지 기준)
    let atdCalendarEl = document.getElementById('fullCalendarInAtd');
    let atdCalendar = new FullCalendar.Calendar(atdCalendarEl, {
        schedulerLicenseKey: 'CC-Attribution-NonCommercial-NoDerivatives',
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
        // googleCalendarApiKey: 'AIzaSyDcnW6WejpTOCffshGDDb4neIrXVUA1EAE',
        // eventSources:[
        //     {
        //         googleCalendarId : 'ko.south_korea.official#holiday@group.v.calendar.google.com',
        //         className : 'official-holiday'
        //     }
        // ],
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

    // today default setting in modal
    let todayDate = new Date().toISOString().substring(0,10);
    $('#atdFromDate').val(todayDate);
    $('#atdToDate').val(todayDate);

    // dayTotal count in modal
    $('#atdFromDate').on("change", function(){
       $('#atdToDate').focus();
    });

    $('#atdToDate').on("change", function(){
        let fromDate = $('#atdFromDate').val();
        let toDate = $('#atdToDate').val();
        toDate = new Date(toDate);
        fromDate = new Date(fromDate);

        let totalTime = 0;
        let dates = getDates(fromDate, toDate);

        for (let value of dates) {
            let date = new Date(value);
            let dayOfWeek = date.getDay();

            if (dayOfWeek >= 1 && dayOfWeek <= 5) {
                totalTime++;
            }
        }

        $('.dayTotal').html(totalTime);
    });

    // 두 날짜 사이에 존재하는 날짜를 배열로 반환한다.
    function getDates(startDate, endDate) {
        let dates = [];

        while (startDate <= endDate) {
            // 날짜에서 'yyyy-MM-dd'만 배열에 저장한다.
            dates.push(startDate.toISOString().split('T')[0]);

            startDate.setDate(startDate.getDate() + 1);
        }
        return dates;
    }
    /* // attendance */
});