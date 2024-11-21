package com.example.workus.attendance.dto;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@Alias("atdCategoryDto")
public class AttendanceCategoryDto {

    private Long no;
    private String name;
    private double count;
    private String time;

}
