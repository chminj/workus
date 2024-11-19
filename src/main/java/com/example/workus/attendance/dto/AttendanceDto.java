package com.example.workus.attendance.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttendanceDto {

    private Long no;
    private int roleNo;
    private double unusedAnnualLeave;
    private double annualLeaveCount;
}
