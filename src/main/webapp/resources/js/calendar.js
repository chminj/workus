
$(document).ready(function() {
    // "일정 추가하기" 버튼 클릭 시 모달 열기
    $("#addScheduleBtn").on("click", function () {
        $("#calendarModal").modal("show");
    });
});

(function () {
    $(function () {
        // 캘린더 엘리먼트 선택
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
                $("#calendarModal").modal("show");

                calendar.unselect();
            },

            // 기본 이벤트 목록
            events: [
                {title: 'All Day Event', start: '2024-11-01'},
                {title: 'Long Event', start: '2024-11-03', end: '2024-11-05'},
                {groupId: 999, title: 'Repeating Event', start: '2024-11-11T16:00:00'},
                {groupId: 999, title: 'Repeating Event', start: '2024-11-18T16:00:00'},
                {title: 'Conference', start: '2021-07-11', end: '2021-07-13'},
                {title: 'Meeting', start: '2021-07-12T10:30:00', end: '2021-07-12T12:30:00'},
                {title: 'Lunch', start: '2021-07-12T12:00:00'},
                {title: 'Meeting', start: '2021-07-12T14:30:00'},
                {title: 'Happy Hour', start: '2021-07-12T17:30:00'},
                {title: 'Dinner', start: '2021-07-12T20:00:00'},
                {title: 'Birthday Party', start: '2021-07-13T07:00:00'},
                {title: 'Click for Google', url: 'http://google.com/', start: '2021-07-28'}
            ]
        });

        $("#save").on("click", function () {
            var eventData = {
                name: $("#name").val(),
                location: $("#location").val(),
                startDate: new Date($("#startDate").val()).toISOString(),
                endDate: new Date($("#endDate").val()).toISOString(),
                division: $("#division").val(),
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
                        extendedProps: {
                            location: result.location,
                            division: result.division,
                            content: result.content,
                            userNo: result.userNo,
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

    });
})();