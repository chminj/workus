$(function(){

    async function atdForm() {
        let response = await fetch("/attendance/attendanceForm");
        let result = await response.json();

        for (let i=0; i<result.length; i++) {

            let content = `
                <li class="d-flex">
                    <input type="checkbox" id="checkingUser${result[i].no}" class="mgr5">
                    <label for="checkingUser${result[i].no}">
                      <span class="name mgr5">${result[i].name}</span>
                    </label>
                    <span class="gray">
                          <span class="dept">${result[i].deptName}</span>
                          <span>l</span>
                          <span class="position">${result[i].positionName}</span>
                    </span>
                </li>
            `;
            $("#atdFormUser").append(content);
        }
    }

    // dialog modal
    let atdDialog = document.getElementById('atdRequestForm');
    let openButton = document.getElementById('atdApplyBtnForModal');

    // dialog open
    openButton.addEventListener('click', () => {
        atdDialog.showModal();
        atdForm();
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

    let totalTime = 0;
    $('#atdToDate').on("change", function() {
        let fromDateStr = $('#atdFromDate').val();
        let toDateStr = $('#atdToDate').val();

        // Date 객체를 한 번만 생성
        let fromDate = new Date(fromDateStr);
        let toDate = new Date(toDateStr);

        // 주중과 주말을 구분하기 위한 상수
        const WEEKDAY_START = 1; // 월요일
        const WEEKDAY_END = 5;   // 금요일

        // 날짜 계산을 위한 변수
        let currentDate = new Date(fromDate);

        calculateWeekdays(fromDate, toDate, locdateArray);

        $('.dayTotal').html(totalTime);
    });

    // 날짜 범위 내 주중 날짜 계산
    function calculateWeekdays(startDate, endDate, locdateArray) {
        let currentDate = new Date(startDate);
        let toDate = new Date(endDate);

        // locdateArray를 Set으로 변환하여 조회 성능 향상
        let holidaySet = new Set(locdateArray.map(date => date.toString()));

        while (currentDate <= toDate) {
            let dayOfWeek = currentDate.getDay();

            // 주말 및 공휴일 제외
            let locdateString = currentDate.toISOString().slice(0, 10).replace(/-/g, ''); // YYYYMMDD 형식으로 변환
            let tf = holidaySet.has(locdateString);

            if (dayOfWeek >= 1 && dayOfWeek <= 5 && !holidaySet.has(locdateString)) { // 월~금 (1~5), 공휴일 제외
                totalTime++;
                console.log("html="+totalTime);
            }

            // 다음 날짜로 이동
            currentDate.setDate(currentDate.getDate() + 1);
        }
        return totalTime; // 총 주중 날짜 수 반환
    }

    // 이중 모달 구현 예정
    $("#confirmModalBtn").on("click",function (){
        confirm("해당 정보로 연차를 신청하겠습니까?");
    });
})