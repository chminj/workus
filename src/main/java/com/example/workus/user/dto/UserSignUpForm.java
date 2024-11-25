package com.example.workus.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserSignUpForm {

    @NotBlank(message = "사번은 필수 입력값입니다.")
    private Long no;

    @NotBlank(message = "ID는 필수 입력값입니다.")
    private String id;

    @NotBlank(message= "비밀번호는 필수 입력값입니다.")
    @Size(min = 6, message = "비밀번호는 6글자 이상이어야 합니다.")
    private String password;

    @NotBlank(message = "비밀번호 확인은 필수 입력값입니다.")
    @Size(min = 6, message= "비밀번호는 6글자 이상이어야 합니다.")
    private String passwordConfirm;

    @NotBlank(message = "연락처는 필수 입력값입니다.")
    @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "유효한 전화번호 형식이 아닙니다.")
    private String phone;

    @NotBlank
    private String address;

    @NotBlank(message = "주소는 필수 입력값입니다.")
    private String detailAddress;
}
