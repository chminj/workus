package com.example.workus.community.vo;

import com.example.workus.user.vo.User;
import lombok.*;
import org.apache.ibatis.type.Alias;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Alias("Board")
public class Board {
    private long nO;
    private String title;
    private String content;
    private String mediaUrl;
    private String mediaType;
    private Date createDate;
    private Date updateDate;

    private User user;
}
