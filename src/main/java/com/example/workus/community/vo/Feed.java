package com.example.workus.community.vo;

import com.example.workus.user.vo.User;
import lombok.*;
import org.apache.ibatis.type.Alias;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Alias("Feed")
@Builder
public class Feed {
    private Long no;
    private String title;
    private String content;
    private String mediaUrl;
    private String mediaType;
    private Date createDate;
    private Date updateDate;

    private User user;

    private List<HashTag> hashTags;

    private Reply reply;

}
