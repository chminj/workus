package com.example.workus.attendance.dto;

import lombok.*;
import org.apache.ibatis.type.Alias;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Alias("atdCategoryDto")
public class AttendanceCategoryDto {
    private Long no;
    private String name;
    private double count;

}
