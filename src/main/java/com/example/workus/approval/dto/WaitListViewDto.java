package com.example.workus.approval.dto;

import lombok.*;
import org.apache.ibatis.type.Alias;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Alias("waitListViewDto")
public class WaitListViewDto {
    private Long no;
    private String commonText;
    private Date fromDate;
    private Date toDate;
    private String title;
    private Date createdDate;
    private int categoryNo;
    private String categoryName;
    private Long reqUserNo;
    private String reason;
    private String status;
    private Long apvUserNo;

    private String textName;
    private String textValue;
}
