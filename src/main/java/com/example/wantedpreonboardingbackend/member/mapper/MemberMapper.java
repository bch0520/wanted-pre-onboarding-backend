package com.example.wantedpreonboardingbackend.member.mapper;

import com.example.wantedpreonboardingbackend.member.dto.MemberLoginDto;
import com.example.wantedpreonboardingbackend.member.dto.MemberPostDto;
import com.example.wantedpreonboardingbackend.member.dto.MemberResponseDto;
import com.example.wantedpreonboardingbackend.member.entity.Member;
import lombok.Builder;
import org.springframework.stereotype.Component;


@Builder
@Component
public class MemberMapper {
    public Member memberPostDtoToMember(MemberPostDto memberPostDto) {
       Member member = new Member();
       member.setEmail(memberPostDto.getEmail());
       member.setPassword(memberPostDto.getPassword());
       //member.setNick_name(member.getNick_name());
       return member;

    }

    public Member memberLoginDtoToMember(MemberLoginDto memberLoginDto) {
        Member member = new Member();
        member.setEmail(memberLoginDto.getEmail());
        member.setPassword(memberLoginDto.getPassword());
        return member;
    }

    public MemberResponseDto memberToMemberResponseDto(Member member) {
        MemberResponseDto memberResponseDto = new MemberResponseDto();
        memberResponseDto.setMemberId(member.getMemberId());
        memberResponseDto.setEmail(member.getEmail());
        //memberResponseDto.setPassword(member.getPassword());
        //memberResponseDto.setNick_name(member.getNick_name());
        return memberResponseDto;
    }
}
