package com.example.workus.attendance.dto;

import lombok.*;
import org.apache.ibatis.type.Alias;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Alias("approvalRequestDto")
public class ApprovalRequestDto {
    private List<Long> atdNo;
    private String status;
    private int dayTotal;
}