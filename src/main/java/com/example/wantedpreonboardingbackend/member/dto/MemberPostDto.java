package com.example.wantedpreonboardingbackend.member.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberPostDto {

    @NotBlank(message = "내용을 입력해 주세요.")
    @Email(message = "이메일 형식으로 작성해 주세요.")
    private  String email;

    @Size(min = 8, message = "비밀번호는 8자 이상으로 입력해주세요.")
    private String password;

    //@NotBlank(message = "내용을 입력해 주세요.")
    //private String nick_name;

}
