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
        let nowFromDate = $(this).val();
       $('#atdToDate').focus().attr('min', nowFromDate);
    });

    $('#atdToDate').on("change", function() {
        let fromDateStr = $('#atdFromDate').val();
        let toDateStr = $('#atdToDate').val();

        // Date 객체를 한 번만 생성
        let fromDate = new Date(fromDateStr);
        let toDate = new Date(toDateStr);

        let totalTime = 0;

        // 주중과 주말을 구분하기 위한 상수
        const WEEKDAY_START = 1; // 월요일
        const WEEKDAY_END = 5;   // 금요일

        // 날짜 계산을 위한 변수
        let currentDate = new Date(fromDate);

        // 날짜 범위 내 주중 날짜 계산
        while (currentDate <= toDate) {
            let dayOfWeek = currentDate.getDay();

            // 주말 제외
            if (dayOfWeek >= WEEKDAY_START && dayOfWeek <= WEEKDAY_END) {
                totalTime++;
            }

            // 다음 날짜로 이동
            currentDate.setDate(currentDate.getDate() + 1);
        }

        $('.dayTotal').html(totalTime);
    });

    const serviceKey = 'A7%2FHaEQe1yP2IrM2iyJqZyrLs9SFLwZ788isUppVC7woA1J7J0n316aNTU7RL7B1GJUhjQDHpXhwBq7ud7u14A%3D%3D'
    /* Javascript 샘플 코드 */

    var xhr = new XMLHttpRequest();
    var url = 'http://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService/getRestDeInfo'; /*URL*/
    var queryParams = '?' + encodeURIComponent('serviceKey') + '='+ serviceKey; /*Service Key*/
    queryParams += '&' + encodeURIComponent('numOfRows') + '=' + encodeURIComponent('30'); /* 임의의 수 */
    queryParams += '&' + encodeURIComponent('solYear') + '=' + encodeURIComponent('2024'); /**/
    queryParams += '&' + encodeURIComponent('_type') + '=' + encodeURIComponent('json'); /**/
    xhr.open('GET', url + queryParams);
    xhr.onreadystatechange = function () {
        if (this.readyState == 4) {
            parseJSON(this.responseText);
        }
    };

    xhr.send('');

    function parseJSON(jsonText) {
        let jsonObj = JSON.parse(jsonText);
        let items = jsonObj.response.body.items.item; // items의 item 배열에 접근

        let locdateArray = []; // locdate를 저장할 배열

        // 각 item에 대해 locdate를 추출하고 요일을 확인
        for (let item of items) {
            if (item && item.locdate) { // item과 locdate가 존재하는지 확인
                locdateArray.push(item.locdate); // locdate를 배열에 추가

                // locdate의 요일 확인
                let date = new Date(item.locdate.toString().replace(/(\d{4})(\d{2})(\d{2})/, '$1-$2-$3')); // locdate를 YYYY-MM-DD 형식으로 변환
                let dayOfWeek = date.getDay(); // 요일 숫자 (0: 일요일, 1: 월요일, ..., 6: 토요일)

                // 토요일(6)과 일요일(0) 제외
                if (dayOfWeek !== 0 && dayOfWeek !== 6) {
                    console.log(`locdate: ${item.locdate}, 요일: ${date.toLocaleDateString('ko-KR', { weekday: 'long' })}`); // locdate와 요일 출력
                }
            }
        }

        console.log(locdateArray); // locdate 배열 출력
    }
    /* // attendance */

});