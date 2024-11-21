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

    // 공휴일 open api
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

    let locdateArray = []; // locdate를 저장할 배열
    function parseJSON(jsonText) {
        let jsonObj = JSON.parse(jsonText);
        let items = jsonObj.response.body.items.item; // items의 item 배열에 접근

        // 각 item에 대해 locdate를 추출하고 요일을 확인
        for (let item of items) {
            if (item && item.locdate) { // item과 locdate가 존재하는지 확인
                // locdate의 요일 확인
                let date = new Date(item.locdate.toString().replace(/(\d{4})(\d{2})(\d{2})/, '$1-$2-$3')); // locdate를 YYYY-MM-DD 형식으로 변환
                let dayOfWeek = date.getDay(); // 요일 숫자 (0: 일요일, 1: 월요일, ..., 6: 토요일)

                // 토요일(6)과 일요일(0) 제외
                if (dayOfWeek !== 0 && dayOfWeek !== 6) {
                    locdateArray.push(item.locdate); // locdate를 배열에 추가
                }
            }
        }
        return locdateArray; // 최종적으로 주중 날짜만 포함된 locdateArray 반환
    }

});