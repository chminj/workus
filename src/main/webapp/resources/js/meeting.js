
    $(document).ready(function() {
        // "일정 추가하기" 버튼 클릭 시 모달 열기
        $("#addScheduleBtn").on("click", function () {

            $("#meetingModalLabel").text("회의실 예약하기");

            $("#meetingModal #startDate").val("");
            $("#meetingModal #endDate").val("");
            $("#meetingModal #room").val("1");
            $("#meetingModal #content").val("");

            $("#calendarModal").data("eventId", null);

            $("#meetingModal").modal("show");
        });
    });

    const roomColors = {
        a: '#007bff', // 회의실 A
        b: '#28a745'  // 회의실 B
    };

    var calendarEl = $('#calendar')[0];

    // full-calendar 생성하기
    var calendar = new FullCalendar.Calendar(calendarEl, {
        schedulerLicenseKey: 'CC-Attribution-NonCommercial-NoDerivatives',
        timeZone: 'UTC',
        initialView: 'listMonth', // 초기 뷰 설정
        aspectRatio: 1.5,

        headerToolbar: {
            left: 'prev,next',
            center: 'title',
            right: 'today'
        },
        editable: true,
        resourceAreaHeaderContent: 'Rooms',
        initialDate: new Date().toISOString().slice(0, 10),
        height: '800px',
        locale: 'ko',

        // 시간 라벨 형식 설정
        slotLabelFormat: {
            hour: '2-digit',
            minute: '2-digit',
            hour12: false // 24시간 형식으로 표시
        },

        events: function(info, successCallback, failureCallback) {
            start = info.startStr.split("T")[0];
            end = info.endStr.split("T")[0];

            console.log(start, end);

            $.ajax({
                type: 'GET',
                url: '/meeting/events',
                data: {
                    start: info.startStr.split("T")[0],
                    end: info.endStr.split("T")[0],
                },
                success: function(meetings) {
                    let events = meetings.map(function(meeting) {
                        let event = {};
                        event.id = meeting.no;
                        event.title = meeting.content;
                        event.start = meeting.startDate;
                        event.end = meeting.endDate;
                        event.resourceId = meeting.room;
                        event.color = roomColors[meeting.room] || '#6c757d';
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

        dateClick: function (info){
            let startDate = info.dateStr;
            console.log(info);

            let localDate = new Date(startDate);
            let localDateString = localDate.toISOString().slice(0, 16);

            let endDate = new Date(localDate);
            endDate.setMinutes(localDate.getMinutes() + 30); // 30분 더함
            let endDateString = endDate.toISOString().slice(0, 16);

            $("#meetingModalLabel").text("회의실 예약하기");

            $("#meetingModal #startDate").val(localDateString);
            $("#meetingModal #endDate").val(endDateString);
            
            if (info.resource && info.resource.id === 'a') {
                $("#meetingModal #room").val("a"); // 회의실 A
            } else if (info.resource && info.resource.id === 'b') {
                $("#meetingModal #room").val("b"); // 회의실 B
            }
            $("#meetingModal #content").val("");

            $("#meetingModal").data("eventId", null);  // eventId 초기화

            $("#delete").hide();
            $("#save").text("예약");

            $("#meetingModal").modal("show");
            calendar.unselect();
        },

        // 리소스 설정
        resources: [
            {"id": "a", "title": "회의실 A"},
            {"id": "b", "title": "회의실 B", "eventColor": "green"},
        ],

        // 이벤트 설정
        // events: [
        //     {"resourceId": "a", "title": "event 1", "start": "2024-11-25", "end": "2024-11-26"},
        //     {"resourceId": "b", "title": "event 2", "start": "2024-11-25T12:00:00+00:00", "end": "2024-11-25T14:00:00+00:00"},
        // ]

    });

    $("#save").on("click", function () {
        var eventData = {
            startDate: new Date($("#startDate").val()).toISOString(),
            endDate: new Date($("#endDate").val()).toISOString(),
            room: $("#room").val() || "a",
            content: $("#content").val()
        };

        if (!eventData.startDate || !eventData.endDate || !eventData.content) {
            alert("입력하지 않은 값이 있습니다.");
            return;
        }

        if (new Date(eventData.startDate) > new Date(eventData.endDate)) {
            alert("끝나는 시간이 시작 시간보다 클 수 없습니다.");
            return;
        }

        var eventId = $("#meetingModal").data("eventId");

        console.log(eventId)

        $.ajax({
            type: "post",
            url: "/meeting/add",
            data: eventData,
            success: function (result) {
                let event = {
                    id: result.no,
                    start: result.startDate,
                    end: result.endDate,
                    resourceId: result.room,
                    extendedProps: {
                        room: result.room,
                        content: result.content,
                        userNo: result.userNo,
                    }
                };

                calendar.addEvent(event);

                $("#meetingModal").modal("hide");
                $("#name, #location, #startDate, #endDate, #division, #content").val("");

                console.log("일정이 추가되었습니다.");
            },
            error: function (xhr, status, error) {
                console.error("일정 추가 실패:", error);
            }
        });

    });

    // 캘린더 렌더링
    calendar.render();

    // 내 예약 현황 클릭 이벤트
    $('#myReservation').on('click', function () {
        calendar.changeView('listMonth'); // 'listMonth' 뷰로 변경
        calendar.setOption('height', '800px');
        calendar.setOption('resourceAreaWidth', '30%');
    });

    // 회의실 클릭 이벤트
    $('#meetingRoom').on('click', function () {
        calendar.changeView('resourceTimeline'); // 'resourceTimeline' 뷰로 변경
        calendar.scrollToTime('00:00:00'); // 선택한 뷰로 스크롤 이동
        calendar.setOption('resources', [
            {"id": "a", "title": "회의실 A"},
            {"id": "b", "title": "회의실 B"}
        ]);
        calendar.setOption('height', 'auto'); // 캘린더 높이를 320px로 설정
        calendar.setOption('resourceAreaWidth', '7.5%');
    });

    // 회의실 A 클릭 이벤트
    $('#meetingRoomA').on('click', function () {
        calendar.changeView('resourceTimeGridWeek'); // 'resourceTimeGridDay' 뷰로 변경
        calendar.scrollToTime('00:00:00'); // 선택한 뷰로 스크롤 이동
        calendar.setOption('resources', [
            {"id": "a", "title": "회의실 A"} // 회의실 A만 표시
        ]);
        calendar.setOption('height', '800px');
        calendar.setOption('resourceAreaWidth', '30%');

    });

    // 회의실 B 클릭 이벤트
    $('#meetingRoomB').on('click', function () {
        calendar.changeView('resourceTimeGridWeek'); // 'resourceTimeGridDay' 뷰로 변경
        calendar.scrollToTime('00:00:00'); // 선택한 뷰로 스크롤 이동
        calendar.setOption('resources', [
            {"id": "b", "title": "회의실 B"} // 회의실 B만 표시
        ]);
        calendar.setOption('height', '800px');
        calendar.setOption('resourceAreaWidth', '30%');

    });
