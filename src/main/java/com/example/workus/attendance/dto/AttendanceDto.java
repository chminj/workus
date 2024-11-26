package com.example.workus.attendance.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class AttendanceDto {
    private Long no;
    private int roleNo;
    private double unusedAnnualLeave;
    private double annualLeaveCount;

    private String currentAnnualLeave = String.format("%.2f", unusedAnnualLeave);
    private String totalAnnualLeave = String.format("%.2f", annualLeaveCount);
}
