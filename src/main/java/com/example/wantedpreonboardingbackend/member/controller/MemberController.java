package com.example.wantedpreonboardingbackend.member.controller;

import com.example.wantedpreonboardingbackend.member.dto.MemberLoginDto;
import com.example.wantedpreonboardingbackend.member.dto.MemberPostDto;
import com.example.wantedpreonboardingbackend.member.entity.Member;
import com.example.wantedpreonboardingbackend.member.mapper.MemberMapper;
import com.example.wantedpreonboardingbackend.member.service.MemberService;
import com.example.wantedpreonboardingbackend.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Validated
@CrossOrigin
@RestController
@RequestMapping(path = "/members", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;
    private  final MemberMapper mapper;



    @PostMapping
    public ResponseEntity signUp(@Valid @RequestBody MemberPostDto memberPostDto) {
        Member member = mapper.memberPostDtoToMember(memberPostDto);
        Member response = memberService.signUp(member);
        return new ResponseEntity<>(mapper.memberToMemberResponseDto(response), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity loginMember(@Valid @RequestBody MemberLoginDto memberLoginDto) {
        Member member = mapper.memberLoginDtoToMember(memberLoginDto);
        Member response = memberService.login(member);
        String token = jwtTokenProvider.createToken(response.getEmail(),response.getRoles());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", token.trim());
        return new ResponseEntity(mapper.memberToMemberResponseDto(response),httpHeaders,HttpStatus.OK);
    }

}
