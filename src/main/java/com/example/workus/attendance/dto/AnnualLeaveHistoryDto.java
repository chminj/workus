package com.example.workus.attendance.dto;

import lombok.*;
import org.apache.ibatis.type.Alias;

import java.time.LocalDate;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Alias("CalDto")
public class AnnualLeaveHistoryDto {
    private Long userNo;
    private String userName;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String time;
    private Long deptNo;
    private String deptName;
    private String categoryName;
    private int totalDay;
}
