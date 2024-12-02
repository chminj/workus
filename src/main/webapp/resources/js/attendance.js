$(function () {
    // BigDecimal 조건 처리
    function formatBigDecimal(value) {
        const numberValue = Number(value);

        // 소수점 이하가 있는 경우
        if (numberValue % 1 !== 0) {
            // 소수점 두 자리로 변환
            let formattedValue = numberValue.toFixed(2);

            // 마지막 0 제거
            if (formattedValue.endsWith('.00')) {
                return formattedValue.slice(0, -3); // .00 제거
            } else if (formattedValue.endsWith('0')) {
                return formattedValue.slice(0, -1); // 마지막 0 제거
            }
            return formattedValue; // 두 자리 소수점
        } else {
            return numberValue.toString(); // 정수인 경우
        }
    }

    let usableAnnualLeaveElement = $(".holiday .usable");
    let usableAnnualLeave = usableAnnualLeaveElement.text().trim(); // 텍스트를 가져오고 공백 제거
    let formattedUsableAnnualLeave = formatBigDecimal(usableAnnualLeave);
    let totalAnnualLeaveElement = $(".holiday .total");
    let totalAnnualLeave = totalAnnualLeaveElement.text().trim();
    let formattedTotalAnnualLeave = formatBigDecimal(totalAnnualLeave);

    // 포맷팅된 값을 다시 설정
    usableAnnualLeaveElement.text(formattedUsableAnnualLeave);
    totalAnnualLeaveElement.text(formattedTotalAnnualLeave);

    // ajax 호출
    async function atdForm() {
        let responseUser = await fetch("/attendance/atdFormInUser");
        let resultUser = await responseUser.json();

        for (let i = 0; i < resultUser.length; i++) {

            let content = `
                <li class="d-flex">
                    <input type="checkbox" id="${resultUser[i].no}" class="mgr5">
                    <label for="${resultUser[i].no}" data-sort="${i}">
                      <span class="name mgr5">${resultUser[i].name}</span>
                    </label>
                    <span class="gray">
                          <span class="dept">${resultUser[i].deptName}</span>
                          <span>l</span>
                          <span class="position">${resultUser[i].positionName}</span>
                    </span>
                </li>
            `;
            $("#atdFormUser").append(content);
        }

        let responseCtgr = await fetch("/attendance/atdFormInCtgr");
        let resultCtgr = await responseCtgr.json();
        for (let i = 0; i < resultCtgr.length; i++) {

            // 연차, 반차, 반반차 (radio)
            if (resultCtgr[i].no < 25) {
                let content = `
                    <label for="day${resultCtgr[i].no}">
                      <input type="radio" name="categoryOpt2" value=${resultCtgr[i].no} id="day${resultCtgr[i].no}">
                      <span class="mgr10 mgl5">${resultCtgr[i].name}</span>
                    </label>
                `;

                $(".annualLeaveOnly").append(content);
            }

            // 연차, 병가 (select)
            if (resultCtgr[i].count === 1) {
                let content = `
                    <option value="${resultCtgr[i].no}">${resultCtgr[i].name}</option>
                `;

                $("#atdCtgr").append(content);
            }
        }
    }

    atdForm();

    // dialog modal
    let atdDialog = document.getElementById('atdRequestForm');
    let openButton = document.getElementById('atdApplyBtnForModal');

    // dialog open
    openButton.addEventListener('click', () => {
        atdDialog.showModal();
        $('html, body').css('overflow', 'hidden');
    })

    // dialog close
    $("#atdRequestForm button.closeBtn").on('click', function () {
        atdDialog.close();
        $('html, body').css('overflow', 'auto');
    })

    // today default setting in modal
    let todayDate = new Date().toISOString().substring(0, 10);
    $('#atdFromDate').val(todayDate);
    $('#atdToDate').val(todayDate);

    // 공휴일 open api
    const serviceKey = 'A7%2FHaEQe1yP2IrM2iyJqZyrLs9SFLwZ788isUppVC7woA1J7J0n316aNTU7RL7B1GJUhjQDHpXhwBq7ud7u14A%3D%3D'
    /* Javascript 샘플 코드 */

    var xhr = new XMLHttpRequest();
    var url = 'http://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService/getRestDeInfo'; /*URL*/
    var queryParams = '?' + encodeURIComponent('serviceKey') + '=' + serviceKey; /*Service Key*/
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

    // dayTotal count in modal
    let totalTime = 0;
    $('#atdFromDate').on("change", function () {
        totalTime = 0;
        let nowFromDate = $(this).val();
        $('#atdToDate').attr('min', nowFromDate).val(nowFromDate);
    });

    $('#atdToDate').on("change", function () {
        totalTime = 0;
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


        if (totalTime > 1) {
            $("#dayTotal").val(totalTime);
        }
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
            }

            // 다음 날짜로 이동
            currentDate.setDate(currentDate.getDate() + 1);
        }
        return totalTime; // 총 주중 날짜 수 반환
    }

    // 결재 및 참조선
    let target = '';
    let apvUserList = [];
    let refUserList = [];

    $("#apvLineAdd, #refLineAdd").on("click", function (e) {
        target = e.currentTarget.id;
        let targetId = target.substring(0, 3);

        atdUserMove("atdFormUser", targetId);
    });

    $("#apvLineCancel, #refLineCancel").on("click", function (e) {
        target = e.currentTarget.id;
        let targetId = target.substring(0, 3);

        atdUserMove(targetId, "atdFormUser");
        sortReset(targetId, "atdFormUser");
    });

    function atdUserMove(source, target) {
        let checkedLiTag = $("#" + source + " input[type='checkbox']:checked").parent();

        $("#" + target).append(checkedLiTag);
        $(checkedLiTag).children("input[type='checkbox']").prop("checked", false);

        sortReset(target, checkedLiTag);
    }

    $(".btnW button").on("click", function () {
        // 결재선의 label 수집
        $("#apv input[type='checkbox']").each(function () {
            var userId = $(this).attr("id");
            // 중복 체크 후 추가
            if (!apvUserList.includes(userId)) {
                apvUserList.push(userId);
            }
        });

        // 참조선의 label 수집
        $("#ref input[type='checkbox']").each(function () {
            var userId = $(this).attr("id");
            // 중복 체크 후 추가
            if (!refUserList.includes(userId)) {
                refUserList.push(userId);
            }
        });

        // 배열을 콤마로 구분된 문자열로 변환하여 설정
        $("#apvUserList").val(apvUserList.join(","));
        $("#refUserList").val(refUserList.join(","));
    })

    // 재정렬 로직
    function sortReset(targetId, checkedLiTag) {
        let labels = $("#" + targetId).find("label");
        // labels 오름차순 정렬
        labels.sort(function (one, two) {
            let oneSort = parseInt($(one).data("sort"));
            let twoSort = parseInt($(two).data("sort"));
            return oneSort - twoSort;
        });

        $("#" + targetId).empty(); // reset
        labels.each(function () {
            $("#" + targetId).append($(this).parent()); // li 태그 추가
        });
    }

    let categoryNo = $("#categoryNo");

    // 연차 옵션 toggle
    $("#atdCtgr").on("click", function () {
        let opt = $(this).children("option:selected").val();

        if (opt === '25') {
            $(".annualLeaveOnly").hide();
            $(".partOpt").addClass("d-none");
            categoryNo.val(opt);
        } else if (opt === '10') {
            $(".annualLeaveOnly").show();
            $(".annualLeaveOnly input[type='radio'][value='10']").prop("checked", true);
        } else {
            $(".partOpt").removeClass("d-none");
        }
    });

    $(document).on("click", ".annualLeaveOnly input[type='radio']", function (e) {
        let val = e.target.value;

        // 라디오 버튼의 값에 따라 categoryNo 설정 및 .partOpt 클래스 조정
        if (val === '10') {
            $(".partOpt").addClass("d-none");
        } else {
            $(".partOpt").removeClass("d-none");
        }

        let selectedRadioValue = $("input[name='categoryOpt2']:checked").val();
        categoryNo.val(selectedRadioValue);
    });

    // confirm
    let confirmBtn = document.getElementById('confirmModalBtn');
    let atdConfirmDialog = document.getElementById('apvUserList');

    $("#atdApproval").on("submit", function (e) {
        e.preventDefault();
        if ($("#apvUserList").val().trim() === "" || $("#refUserList").val().trim() === "") {
            alert("결재선/참조선 목록이 비어 있습니다. 사용자를 선택해 주세요.");
        } else {
            $("#collapseExample").stop().fadeIn(500);
        }

        // 폼 제출
        let dayTotal = $("#dayTotal").val();
        if (dayTotal === "") {
            $("#dayTotal").val(0); // 또는 원하는 기본값으로 설정
        }
    });

    $("#confirmModalBtn").on("click", function () {
        if ($("#collapseExample").is(':visible')) {
            $("#atdApproval").off("submit").submit();
            // submit 이벤트를 제거하고 폼 제출
        }
    });
})