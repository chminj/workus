$(function () {
    // fullcalendar (근태 페이지 기준)
    let atdCalendarEl = document.getElementById('fullCalendarInAtd');
    let roleNo = 0; // 사용자 역할 저장
    let finalEvents = []; // 전체 이벤트 저장
    let selectedDeptNo; // 선택된 부서 번호
    const selectBox = $('<select id="deptSelect" class="form-select"><option value="">Select a department</option></select>');

// 페이지 로드 시 사용자 역할 및 연차 기록, 부서 목록 조회
    $.ajax({
        url: '/api/attendances/annualLeaveHistory',
        type: 'POST',
        success: function (data) {
            console.log(data); // data의 내용을 확인

            roleNo = data.roleNo; // roleNo 값 저장
            finalEvents = data.events.map(leave => {
                const startDate = new Date(leave.fromDate);
                const endDate = new Date(leave.toDate);
                return {
                    title: leave.userName,
                    start: startDate,
                    end: endDate,
                    category: leave.categoryName,
                    time: leave.time,
                    deptNo: leave.deptNo,
                    deptName: leave.deptName,
                    allDay: true,
                    extendedProps: { // extendedProps에 deptNo 추가
                        deptNo: leave.deptNo, // 부서 번호 추가
                        deptName: leave.deptName // 부서 이름 추가
                    }
                };
            }); // 연차 기록 저장
            loadDepartmentList(data.depts); // 부서 목록 로드

            // 이벤트 필터링 (기본적으로 자신의 부서로 설정)
            if (roleNo !== 100) {
                selectedDeptNo = data.depts[0].deptNo; // 자신의 부서 ID 저장
                filterEventsByDepartment(selectedDeptNo);
            } else {
                selectBox.appendTo($('.fc-toolbar .fc-toolbar-chunk:first-child'))
                    .on('change', function () {
                        selectedDeptNo = $(this).val();

                        filterEventsByDepartment(selectedDeptNo);
                    });
            }
        },
        error: function () {
            console.error('연차 기록 및 부서 목록을 가져오는 데 실패했습니다.');
        }
    });

    // 부서 select option 추가
    function populateDepartmentSelect(deptSelect) {
        selectBox.empty().append('<option value="">부서별 조회</option>'); // 초기화
        deptSelect.forEach(dept => {
            selectBox.append(`<option value="${dept.deptNo}">${dept.deptName}</option>`);
        });
    }

    // 부서 목록을 가져오는 함수
    function loadDepartmentList(depts) {
        populateDepartmentSelect(depts); // 부서 선택 옵션 추가

        // 역할에 따라 부서 선택 박스 이벤트 추가
        if (roleNo === 100) { // 관리자일 경우
            selectBox.on('change', function () {
                selectedDeptNo = $(this).val();
                // 선택된 부서에 따라 이벤트 필터링
                filterEventsByDepartment(selectedDeptNo);
            });
        } else { // 일반 사용자일 경우
            selectedDeptNo = depts[0].deptNo; // 자신의 부서 ID 저장
            filterEventsByDepartment(selectedDeptNo); // 해당 부서로 필터링
        }
    }

    let atdCalendar = new FullCalendar.Calendar(atdCalendarEl, {
        schedulerLicenseKey: 'CC-Attribution-NonCommercial-NoDerivatives',
        initialView: 'dayGridMonth',
        headerToolbar: {
            left: '',
            center: 'title',
            right: 'prev,next today' // select box 추가 위치
        },
        selectable: false,
        locale: 'ko',
        buttonText: {
            today: '오늘',
            month: '월별',
            week: '주별',
            day: '일별',
        },
        aspectRatio: 2,
        displayEventTime: false,
        eventSources: [
            {
                googleCalendarId: 'ko.south_korea.official#holiday@group.v.calendar.google.com',
                className: 'official-holiday'
            },
            {
                events: function (fetchInfo, successCallback, failureCallback) {
                    $.ajax({
                        url: '/api/attendances/annualLeaveHistory',
                        type: 'POST',
                        success: function (data) {
                            const events = data.events.map(leave => {
                                const startDate = new Date(leave.fromDate);
                                const endDate = new Date(leave.toDate);
                                return {
                                    title: leave.userName,
                                    start: startDate,
                                    end: endDate,
                                    category: leave.categoryName,
                                    time: leave.time,
                                    deptNo: leave.deptNo,
                                    deptName: leave.deptName,
                                    allDay: true,
                                    extendedProps: { // extendedProps에 deptNo 추가
                                        deptNo: leave.deptNo, // 부서 번호 추가
                                        deptName: leave.deptName // 부서 이름 추가
                                    }
                                };
                            });

                            // 최종 이벤트 배열 저장
                            finalEvents = events; // 전체 이벤트 저장

                            // 선택된 부서로 필터링된 이벤트 호출
                            const filteredEvents = filterEventsByDepartment(selectedDeptNo, true);
                            successCallback(filteredEvents); // 필터링된 이벤트 전달
                        },
                        error: function () {
                            failureCallback();
                        }
                    });
                },
                className: 'annual-leave',
                eventLimit: true, // 3개 이상이면 더보기 버튼 표시
                eventLimitText: '더 보기',
            },
        ],
        views: {
            dayGrid: {
                dayMaxEvents: 3 // 3개 이상이면 더보기 버튼 표시
            }
        },
        // 이벤트가 렌더링될 때 호출
        eventDidMount: function (info) {
            const {category, time} = info.event.extendedProps;
            if (category === '연차') {
                info.el.classList.add('wholeDay');
            }
            if (category !== '하계휴가' && category !== '연차') {
                info.el.classList.add("notWholeDay");
                info.el.setAttribute('data-time', time + ' ' + category);
            }
        },
        eventClick: function (info) {
            info.jsEvent.stopPropagation();
            info.jsEvent.preventDefault();
        },
    });

    atdCalendar.render();

    // 부서별 필터링 로직
    function filterEventsByDepartment(deptNo, returnFiltered = false) {
        // deptNo를 기준으로 필터링
        const filteredEvents = deptNo ? finalEvents.filter(event =>
            event.extendedProps && event.extendedProps.deptNo == deptNo
        ) : finalEvents;

        // 기존 이벤트 제거
        atdCalendar.removeAllEvents();

        // 필터링된 이벤트 추가
        filteredEvents.forEach(event => {
            atdCalendar.addEvent(event); // addEvent를 사용하여 필터링된 이벤트 추가
        });

        // 필터링된 이벤트를 반환할지 여부에 따라 반환
        if (returnFiltered) {
            return filteredEvents; // 필터링된 이벤트 반환
        }
    }
});