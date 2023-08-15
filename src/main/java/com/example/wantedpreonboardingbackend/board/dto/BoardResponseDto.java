package com.example.wantedpreonboardingbackend.board.dto;

import com.example.wantedpreonboardingbackend.member.dto.MemberResponseDto;
import com.example.wantedpreonboardingbackend.member.mapper.MemberMapper;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoardResponseDto {
    private long boardId;
    private String title;
    private String content;
    private MemberResponseDto member;

}
