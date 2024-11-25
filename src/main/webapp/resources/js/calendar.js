
    $(document).ready(function() {
        // "일정 추가하기" 버튼 클릭 시 모달 열기
        $("#addScheduleBtn").on("click", function () {

            $("#calendarModalLabel").text("일정 추가하기");

            $("#calendarModal #name").val("");
            $("#calendarModal #location").val("");
            $("#calendarModal #startDate").val("");
            $("#calendarModal #endDate").val("");
            $("#calendarModal #division").val("1");
            $("#calendarModal #content").val("");

            $("#save").text("추가");

            $("#calendarModal").data("eventId", null);

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
        height: '800px',             // 캘린더 높이
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
        googleCalendarApiKey: 'AIzaSyDIvWEKCFDKbJPdBUzOMSBSbf_cCtdRRTk',
        eventSources: [
            {
                googleCalendarId: 'ko.south_korea#holiday@group.v.calendar.google.com'
                , color: 'yellow'
                , textColor: 'black'
                , className : 'ko_event'
            }
        ],
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
                            division: calendar.division
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

        eventClick: function(info) {
            info.jsEvent.stopPropagation();
            info.jsEvent.preventDefault();

            var eventId = info.event.id;
            console.log(eventId);  // 여기서 확인

            if (!eventId) {
                console.error("이벤트 ID가 없습니다.");
                return;
            }

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
                    $("#delete").show();

                    $("#calendarModal").data("eventId", eventId);
                    $("#calendarModal").modal("show");

                },
                error: function(xhr, status, error) {
                    console.error("일정 상세 정보 가져오기 실패:", error);
                }
            });

        },
        eventDrop: function(eventDropInfo ) {
            let event = eventDropInfo.event;

            let updatedEventData = {
                id: event.id,
                name: event.title,
                location: event.extendedProps.location,
                startDate: event.start.toISOString(),
                endDate: event.end.toISOString(),
                division: event.extendedProps.division,
                content: event.extendedProps.content
            };

            $.ajax({
                type: 'post',
                url: '/calendar/update',
                data: updatedEventData,    // 업데이트된 이벤트 데이터

                error: function(xhr, status, error) {
                    console.error("이벤트 업데이트 실패:", error);
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
        dateClick: function (info) {
            let startDate = info.dateStr;
            console.log(info);

            let now = new Date();
            let koreaTimeOffset = 9 * 60 * 60 * 1000; // UTC+9 시간차 (밀리초 단위)
            let koreaTime = new Date(now.getTime() + koreaTimeOffset);

            let currentTime = koreaTime.toISOString().split("T")[1].substring(0, 5);

            let startDateParts = startDate.split("T");

            $("#calendarModalLabel").text("일정 추가하기");

            $("#calendarModal #name").val("");
            $("#calendarModal #location").val("");
            $("#calendarModal #division").val("1");
            $("#calendarModal #content").val("");

            $("#calendarModal #startDate").val(`${startDateParts[0]}T${currentTime}`);
            $("#calendarModal #endDate").val(""); // 종료 시간 초기화

            $("#calendarModal").data("eventId", null);  // eventId 초기화

            $("#delete").hide();
            $("#save").text("추가");

            $("#calendarModal").modal("show");
            calendar.unselect();
        },
        select: function (info) {

            let startDate = info.startStr;
            let endDate = info.endStr;
            console.log(info);

            let now = new Date();
            let koreaTimeOffset = 9 * 60 * 60 * 1000; // UTC+9 시간차 (밀리초 단위)
            let koreaTime = new Date(now.getTime() + koreaTimeOffset);

            let currentTime = koreaTime.toISOString().split("T")[1].substring(0, 5);

            let startDateParts = startDate.split("T");
            let correctedEndDate = getDate(new Date(endDate), -1);
            let endDateParts = correctedEndDate.split("T");

            $("#calendarModalLabel").text("일정 추가하기");

            $("#calendarModal #name").val("");
            $("#calendarModal #location").val("");
            $("#calendarModal #division").val("1");
            $("#calendarModal #content").val("");

            $("#calendarModal #startDate").val(`${startDateParts[0]}T${currentTime}`);
            $("#calendarModal #endDate").val(`${endDateParts[0]}T${currentTime}`);

            $("#calendarModal").data("eventId", null);  // eventId 초기화

            $("#delete").hide();
            $("#save").text("추가");
            
            $("#calendarModal").modal("show");
            calendar.unselect();
        }

    });

    function getDate(date, days) {
        days = days || 0;
        date.setTime(date.getTime() + (60*60*24*1000*days));
        let year = date.getFullYear();
        let month = date.getMonth() + 1;
        let day = date.getDate();

       return year +  '-' + (month < 10 ? '0' + month : month) + '-' +(day < 10 ? '0' + day : day);
    }

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

        var eventId = $("#calendarModal").data("eventId");

        console.log(eventId)

        if (eventId) {
            // 기존 일정을 수정하는 경우
            $.ajax({
                type: "post",
                url: "/calendar/update",
                data: { id: eventId, ...eventData },
                success: function (result) {
                    var event = calendar.getEventById(eventId);
                    event.setProp('title', result.name);
                    event.setStart(result.startDate);
                    event.setEnd(result.endDate);
                    event.setExtendedProp('content', result.content);
                    event.setExtendedProp('location', result.location);
                    event.setExtendedProp('division', result.division);

                    $("#save").text("저장");
                    $("#calendarModal").modal("hide");

                    $("#name, #location, #startDate, #endDate, #division, #content").val("");
                },
                error: function(xhr, status, error) {
                    console.error("일정 수정 실패:", error);
                }
            });
        } else {
            // 새로운 일정을 추가하는 경우
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
        }


    });

    $("#delete").on("click", function (){
        var eventId = $("#calendarModal").data("eventId");
        if (!eventId) {
            alert("삭제할 수 없습니다.");
            return;
        }

        // 삭제 확인 모달 열기
        $("#confirmDeleteModal").modal("show");

        $("#confirmDeleteModal .btn-danger").off("click").on("click", function () {
            $.ajax({
                type: "POST",
                url: "/calendar/delete",
                data: { id: eventId },
                success: function () {
                    var event = calendar.getEventById(eventId);
                    if (event) {
                        event.remove();
                    }

                    alert("일정이 삭제되었습니다.");
                    $("#calendarModal").data("eventId", null);

                    $("#calendarModal").modal("hide");
                    $("#confirmDeleteModal").modal("hide");  // 확인 모달 닫기
                },
                error: function (xhr, status, error) {
                    console.error("일정 삭제 실패:", error);
                    alert("일정 삭제에 실패했습니다. 다시 시도해주세요.");
                    $("#confirmDeleteModal").modal("hide");  // 확인 모달 닫기
                }
            });
        });

        // 취소 버튼 클릭 시 경고창 닫기
        $("#confirmDeleteModal .btn-secondary").off("click").on("click", function() {
            $("#confirmDeleteModal").modal("hide");
        });

    })

    // FullCalendar 렌더링
    calendar.render();

    function divisionAll(){
        let el1 = document.querySelector("input[id=division1]");
        let el2 = document.querySelector("input[id=division0]");
        let el3 = document.querySelector("input[id=divisionAll]");

        if (el3.checked) {
            el1.checked = true;
            el2.checked = true;
        } else {
            el1.checked = false;
            el2.checked = false;
        }

        refreshCalendar();
    }

    function refreshCalendar() {
        let el1 = document.querySelector("input[id=division1]");
        let el2 = document.querySelector("input[id=division0]");
        let el3 = document.querySelector("input[id=divisionAll]");

        let values = [];

        if (el1.checked) {
            values.push(1);
        }
        if (el2.checked) {
            values.push(0);
        }

        if (el1.checked === false || el2.checked === false) {
            el3.checked = false;
        } else {
            el3.checked = true;
        }

        // 기존 이벤트 소스 제거
        calendar.getEventSources().forEach(function(source) {
            source.remove();
        });

        if (values.length > 0) {
            let queryString = values.map(function (value) {
                return "division=" + value;
            }).join("&");

            let startDate = start || "2024-01-01"; // 기본 값 설정 (예시)
            let endDate = end || "2024-12-31"; // 기본 값 설정 (예시)
            queryString += `&start=${startDate}&end=${endDate}`;

            $.ajax({
                type: 'GET',
                url: '/calendar/events?' + queryString,
                success: function (calendars) {
                    let events = calendars.map(function (calendar) {
                        return {
                            id: calendar.no,
                            title: calendar.name,
                            start: calendar.startDate,
                            end: calendar.endDate,
                            color: divisionColors[calendar.division] || '#6c757d',
                            extendedProps: {
                                // deptNo: calendar.deptNo
                            }
                        };
                    });

                    calendar.addEventSource(events);
                    calendar.refetchEvents();
                },
                error: function (xhr, status, error) {
                    console.error("일정 데이터 로드 실패:", error);
                }
            });
        }
    }
