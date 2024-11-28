package com.example.workus.attendance.dto;

import lombok.*;
import org.apache.ibatis.type.Alias;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Alias("apvReqDto")
public class ApprovalRequestDto {
    private Long atdNo;
    private String status;
    private BigDecimal dayTotal;

    private BigDecimal unusedDate;
    private BigDecimal usedDate;
}