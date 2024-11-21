package com.example.workus.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Alias("DeptInChatroomDto")
public class DeptInChatroomDto {
    private Long deptNo;
    private String deptName;
}
