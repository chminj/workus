
    $(document).ready(function() {
        // "일정 추가하기" 버튼 클릭 시 모달 열기
        $("#addScheduleBtn").on("click", function () {

            $("#calendarModal #name").val("");
            $("#calendarModal #location").val("");
            $("#calendarModal #startDate").val("");
            $("#calendarModal #endDate").val("");
            $("#calendarModal #division").val("1");
            $("#calendarModal #content").val("");

            $("#calendarModal").modal("show");
        });
    });

    const divisionColors = {
        1: '#007bff', // 내 캘린더
        0: '#28a745'  // 팀 캘린더
    };

    let start, end;

    var calendarEl = $('#calendar')[0];

    // FullCalendar 생성
    var calendar = new FullCalendar.Calendar(calendarEl, {
        schedulerLicenseKey: 'CC-Attribution-NonCommercial-NoDerivatives',
        height: '700px',             // 캘린더 높이
        expandRows: true,            // 화면에 맞게 높이 조절
        slotMinTime: '08:00',        // Day 캘린더 시작 시간
        slotMaxTime: '20:00',        // Day 캘린더 종료 시간
        headerToolbar: {             // 헤더 툴바 설정
            left: 'prev,next today',
            center: 'title',
            right: 'dayGridMonth,timeGridWeek,timeGridDay,listWeek'
        },
        initialView: 'dayGridMonth', // 초기 보이는 화면
        initialDate: new Date(),   // 초기 날짜 설정
        navLinks: true,              // 날짜 선택 시 Day/Week 뷰로 이동 가능
        editable: true,              // 일정 수정 가능
        selectable: true,            // 드래그로 일정 생성 가능
        nowIndicator: true,          // 현재 시간 표시
        dayMaxEvents: true,          // 일정 초과 시 "+N개" 형식으로 표시
        locale: 'ko',                // 한국어 설정
        eventClick: function(info) {
            var eventId = info.event.id;

            $.ajax({
                type: 'post',
                url: '/calendar/detail',
                data: { id: eventId },
                success: function (response) {
                    $("#calendarModal #name").val(response.name);
                    $("#calendarModal #location").val(response.location);
                    $("#calendarModal #startDate").val(response.startDate);
                    $("#calendarModal #endDate").val(response.endDate);
                    $("#calendarModal #division").val(response.division);
                    $("#calendarModal #content").val(response.content);

                    $("#calendarModalLabel").text("일정 상세정보");

                    $("#save").text("저장");

                    $("#calendarModal").modal("show");
                },
                error: function(xhr, status, error) {
                    console.error("일정 상세 정보 가져오기 실패:", error);
                }
            });

        },

        events: function(info, successCallback, failureCallback) {
            start = info.startStr.split("T")[0];
            end = info.endStr.split("T")[0];

            $.ajax({
                type: 'GET',
                url: '/calendar/events',
                data: {
                    start: info.startStr.split("T")[0],
                    end: info.endStr.split("T")[0],
                    division: 1
                },
                success: function(calendars) {
                    let events = calendars.map(function(calendar) {
                        let event = {};
                        event.id = calendar.no;
                        event.title = calendar.name;
                        event.start = calendar.startDate;
                        event.end = calendar.endDate;
                        event.color = divisionColors[calendar.division] || '#6c757d';
                        event.extendedProps = {
                            //deptNo: calendar.deptNo
                        };
                        return event;
                    });

                    console.log(events);
                    successCallback(events); // FullCalendar에 이벤트 렌더링
                },
                error: function(xhr, status, error) {
                    console.error("일정 불러오기 실패:", error);
                    failureCallback(error);
                }
            });
        },

        // 이벤트가 추가될 때
        eventAdd: function (obj) {
            console.log('Event Added:', obj);
        },
        // 이벤트가 수정될 때
        eventChange: function (obj) {
            console.log('Event Changed:', obj);
        },
        // 이벤트가 삭제될 때
        eventRemove: function (obj) {
            console.log('Event Removed:', obj);
        },
        select: function (arg) {

            let startDate = arg.startStr;

            let now = new Date();
            let koreaTimeOffset = 9 * 60 * 60 * 1000; // UTC+9 시간차 (밀리초 단위)
            let koreaTime = new Date(now.getTime() + koreaTimeOffset);

            let currentTime = koreaTime.toISOString().split("T")[1].substring(0, 5);

            let startDateParts = startDate.split("T");

            $("#calendarModal #name").val("");
            $("#calendarModal #location").val("");
            $("#calendarModal #endDate").val("");
            $("#calendarModal #division").val("1");
            $("#calendarModal #content").val("");

            $("#calendarModal #startDate").val(`${startDateParts[0]}T${currentTime}`);

            $("#calendarModal").modal("show");
            calendar.unselect();
        }
    });

    $("#save").on("click", function () {
        var eventData = {
            name: $("#name").val(),
            location: $("#location").val(),
            startDate: new Date($("#startDate").val()).toISOString(),
            endDate: new Date($("#endDate").val()).toISOString(),
            division: $("#division").val() || "1",
            content: $("#content").val()
        };

        if (!eventData.name || !eventData.startDate || !eventData.endDate) {
            alert("입력하지 않은 값이 있습니다.");
            return;
        }

        if (new Date(eventData.startDate) > new Date(eventData.endDate)) {
            alert("끝나는 날짜는 시작 날짜보다 클 수 없습니다.");
            return;
        }

        $.ajax({
            type: "post",
            url: "/calendar/add",
            data: eventData,
            success: function (result) {
                calendar.addEvent({
                    id: result.no,
                    title: result.name,
                    start: result.startDate,
                    end: result.endDate,
                    color: divisionColors[result.division] || '#6c757d',
                    extendedProps: {
                        location: result.location,
                        division: result.division,
                        content: result.content,
                        userNo: result.no,
                        deptNo: result.deptNo
                    }
                });

                $("#calendarModal").modal("hide");
                $("#name, #location, #startDate, #endDate, #division, #content").val("");
                console.log("일정이 추가되었습니다.");
            },
            error: function (xhr, status, error) {
                console.error("일정 추가 실패:", error);
            }
        });
    });

    // FullCalendar 렌더링
    calendar.render();

    function refreshCalendar() {
        let el1 = document.querySelector("input[id=division1]");
        let el2 = document.querySelector("input[id=division0]");

        let values = [];
        if (el1.checked) {
            values.push(el1.value);
        }

        if (el2.checked) {
            values.push(el2.value);
        }

        // 기존 이벤트 소스 제거
        calendar.getEventSources().forEach(function(source) {
            source.remove();
        });


        if (values.length > 0) {
            let queryString = values.map(function (value) {
                return "division=" + value;
            }).join("&");
            queryString += "&start=" + start + "&end=" + end;

            $.ajax({
                type: 'GET',
                url: '/calendar/events?' + queryString,

                success: function (calendars) {
                    let events = calendars.map(function (calendar) {
                        let event = {};
                        event.id = calendar.no;
                        event.title = calendar.name;
                        event.start = calendar.startDate;
                        event.end = calendar.endDate;
                        event.color = divisionColors[calendar.division] || '#6c757d';
                        event.extendedProps = {
                            deptNo: calendar.deptNo
                        };
                        return event;
                    });

                    calendar.addEventSource(events);
                    calendar.refetchEvents();
                }
            });
        }
    }



























