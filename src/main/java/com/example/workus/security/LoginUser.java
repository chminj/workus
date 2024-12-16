package com.example.workus.security;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class LoginUser {
    private Long no;
    private String id;
    private String name;
}
