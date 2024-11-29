$(function () {
    // fullcalendar (근태 페이지 기준)
    let atdCalendarEl = document.getElementById('fullCalendarInAtd');
    let finalEvents = [];
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
        eventLimit: true,
        eventLimitText: '더 보기',
        buttonText: {
            today: '오늘',
            month: '월별',
            week: '주별',
            day: '일별',
        },
        aspectRatio: 2,
        displayEventTime: false,
        googleCalendarApiKey: 'AIzaSyDIvWEKCFDKbJPdBUzOMSBSbf_cCtdRRTk',
        eventSources: [
            {
                googleCalendarId: 'ko.south_korea.official#holiday@group.v.calendar.google.com',
                className: 'official-holiday'
            },
            {
                events: function (fetchInfo, successCallback, failureCallback) {
                    $.ajax({
                        url: '/api/attendances/annualLeaveHistory', // 데이터 가져올 API 엔드포인트
                        type: 'POST',
                        success: function (data) {
                            // 데이터에서 이벤트 형식으로 변환
                            const events = data.map(leave => {
                                const startDate = new Date(leave.fromDate);
                                const endDate = new Date(leave.toDate);
                                const isMultiDay = startDate.toISOString().split('T')[0] !== endDate.toISOString().split('T')[0];

                                return {
                                    title: leave.userName,
                                    start: startDate,
                                    end: endDate,
                                    category: leave.categoryName,
                                    time: leave.time,
                                    deptNo: leave.deptNo,
                                    deptName: leave.deptName,
                                    allDay: true,
                                    isMultiDay: isMultiDay // 멀티데이 여부 추가
                                };
                            });

                            // 날짜와 부서별로 이벤트를 그룹화
                            let groupedEvents = {};
                            events.forEach(event => {
                                const date = event.start.toISOString().split('T')[0];
                                const deptKey = event.deptNo; // 부서 번호로 그룹화

                                if (!groupedEvents[date]) {
                                    groupedEvents[date] = {};
                                }
                                if (!groupedEvents[date][deptKey]) {
                                    groupedEvents[date][deptKey] = [];
                                }
                                groupedEvents[date][deptKey].push(event);
                            });

                            // 최종 이벤트 배열 생성
                            finalEvents = [];
                            for (let date in groupedEvents) {
                                for (let dept in groupedEvents[date]) {
                                    const deptEvents = groupedEvents[date][dept];
                                    finalEvents.push(...deptEvents.length > 3 ? [{
                                        title: `${deptEvents.length}개의 일정이 있습니다.`,
                                        start: date,
                                        allDay: true,
                                        extendedProps: {
                                            events: deptEvents,
                                            deptNo: deptEvents[0].deptNo, // 부서 번호 추가
                                            deptName: deptEvents[0].deptName // 부서 이름 추가
                                        }
                                    }] : deptEvents.map(event => ({
                                        ...event,
                                        extendedProps: {
                                            deptNo: event.deptNo,
                                            deptName: event.deptName
                                        }
                                    })));
                                }
                            }
                            successCallback(finalEvents);

                            // 셀 높이 조정 로직
                            for (let date in groupedEvents) {
                                const dayCell = atdCalendarEl.querySelector(`.fc-day[data-date="${date}"]`);
                                const totalEvents = Object.values(groupedEvents[date]).flat().length;
                                if (totalEvents > 3) {
                                    dayCell.style.height = '130px';
                                }

                                // 멀티데이 이벤트에 클래스 추가
                                const eventsOnDate = groupedEvents[date];
                                for (let dept in eventsOnDate) {
                                    eventsOnDate[dept].forEach(event => {
                                        if (event.isMultiDay) {
                                            // 이벤트의 DOM 요소를 찾고 클래스 추가
                                            const eventEl = atdCalendarEl.querySelector(`.fc-event[data-event-id="${event.id}"]`); // event.id가 필요
                                            if (eventEl) {
                                                eventEl.classList.add('multi-day-event'); // 원하는 클래스 이름으로 변경
                                            }
                                        }
                                    });
                                }
                            }

                            console.log(finalEvents);
                            updateDepartmentSelect();
                        },
                        error: function () {
                            failureCallback();
                        }
                    });
                },
                className: 'annual-leave',
            },
        ],
        // 이벤트가 렌더링될 때 호출
        eventDidMount: function (info) {
            const {category, time} = info.event.extendedProps; // categoryNo, time 추출
            info.el.classList.add(category === '연차' ? 'wholeDay' : 'notWholeDay');
            if (category !== '병가' && category != '연차') {
                info.el.setAttribute('data-time', time + ' ' + category);
            }
        },
        eventClick: function (info) {
            //클릭시 구글캘린더 url로 가는 것 막음
            info.jsEvent.stopPropagation();
            info.jsEvent.preventDefault();
        }
    });
    atdCalendar.render();

    // Select box 추가
    const selectBox = $('<select id="deptSelect" class="form-select"><option value="">Select a department</option></select>')
        .appendTo($('.fc-toolbar .fc-toolbar-chunk:first-child')) // 툴바 오른쪽에 추가
        .on('change', function () {
            const selectedValue = $(this).val();
            console.log('Selected Department:', selectedValue);
            // 선택된 부서에 따라 이벤트 필터링 로직 추가
            filterEventsByDepartment(selectedValue);
        });

    // 부서 목록 업데이트 함수
    function updateDepartmentSelect() {
        const deptSelect = [];
        // finalEvents에서 부서 번호와 이름을 추출하여 deptSelect에 추가
        finalEvents.forEach(event => {
            const deptNo = event.extendedProps.deptNo;
            const deptName = event.extendedProps.deptName; // 부서 이름을 event에서 가져옴

            // 중복 부서명 방지
            if (!deptSelect.some(dept => dept.id === deptNo)) {
                deptSelect.push({id: deptNo, name: deptName});
            }
        });

        // select box에 부서 추가
        selectBox.empty().append('<option value="">부서별 조회</option>'); // 초기화
        deptSelect.forEach(dept => {
            selectBox.append(`<option value="${dept.id}">${dept.name}</option>`);
        });
    }

    atdCalendar.updateSize();

    // 부서별 필터링 로직
    function filterEventsByDepartment(deptId) {
        const filteredEvents = deptId ? finalEvents.filter(event => event.extendedProps.deptNo == deptId) : finalEvents;

        // 기존 이벤트 제거
        atdCalendar.removeAllEvents();

        // 필터링된 이벤트 추가
        atdCalendar.addEventSource(filteredEvents);

        console.log('Filtered Events:', filteredEvents);
    }
});