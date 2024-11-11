package com.example.workus.chat.vo;

import lombok.*;
import org.apache.ibatis.type.Alias;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Alias("Chatroom")
public class Chatroom {
    private int no;
    private String title;
    private int userNo;
}
