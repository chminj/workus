package com.example.workus.community.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CommentForm {
    private String comment;
    private Long feedNo;
    private Long userNo;
}
