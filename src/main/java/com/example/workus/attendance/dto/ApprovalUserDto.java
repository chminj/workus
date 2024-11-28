package com.example.workus.attendance.dto;

import com.example.workus.user.vo.User;
import lombok.*;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ApprovalUserDto {
    private Long no;
    private String status;
    private int sequence;
    private Long userNo;
    private Long formNo;
}
