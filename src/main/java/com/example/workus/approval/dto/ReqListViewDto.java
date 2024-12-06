package com.example.workus.approval.dto;

import lombok.*;
import org.apache.ibatis.type.Alias;

import java.util.Date;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Alias("reqListViewDto")
public class ReqListViewDto {
    private Long no;
    private String commonText;
    private Date fromDate;
    private Date toDate;
    private Date createdDate;
    private String title;
    private int categoryNo;
    private String categoryName;
    private Long reqUserNo;
    private String reason;
    private String status;
    private Long apvUserNo;

    private List<Map<String, String>> optionTexts;

    private String denyReason;// 반려 사유
}