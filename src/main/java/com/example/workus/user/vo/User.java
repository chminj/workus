package com.example.workus.user.vo;

import lombok.*;
import org.apache.ibatis.type.Alias;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Alias("User")
public class User {
    private long no;
    private String id;
    private String name;
    private String password;
    private String email;
    private String address;
    private Date birthday;
    private Date hireDate;
    private Date quitDate;
    private String status;
    private String profileSrc;
    private String pr;
    private String phone;
    private double unusedAnnualLeave;
    private long positionNo;
    private long deptNo;
    private long roleNo;
}
