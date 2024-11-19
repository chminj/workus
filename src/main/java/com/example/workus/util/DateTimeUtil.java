package com.example.workus.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {

    private static final ZoneId DEFAULT_ZONE_ID = ZoneId.of("Asia/Seoul");

    /**
     * ISO 8601 포맷의 문자열을 LocalDateTime 객체로 변환
     *
     * @param value ISO 8601 형식의 날짜/시간 문자열
     * @return 변환된 LocalDateTime 객체
     */
    public static LocalDateTime getLocalDateTime(String value) {
        return LocalDateTime.from(
                Instant.from(
                        DateTimeFormatter.ISO_DATE_TIME.parse(value)
                ).atZone(DEFAULT_ZONE_ID)
        );
    }
}
