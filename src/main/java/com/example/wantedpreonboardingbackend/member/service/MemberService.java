package com.example.wantedpreonboardingbackend.member.service;

import com.example.wantedpreonboardingbackend.exception.BusinessLogicException;
import com.example.wantedpreonboardingbackend.exception.ExceptionCode;
import com.example.wantedpreonboardingbackend.member.entity.Member;
import com.example.wantedpreonboardingbackend.member.repository.MemberRepository;
import com.example.wantedpreonboardingbackend.security.CustomAuthorityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomAuthorityUtils customAuthorityUtils;

    public Member signUp(Member member) {
        Optional<Member> findMember = memberRepository.findByEmail(member.getEmail());
        if(findMember.isPresent()) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_EXISTS);
        }
        String encryptedPassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(encryptedPassword);
        member.setRoles(customAuthorityUtils.createRoles(member.getEmail()));
        Member saveMember = memberRepository.save(member);
        return saveMember;
    }

    public Member login(Member member) {
        Member findMember = verifyExistsEmail(member.getEmail());
        if(!passwordEncoder.matches(member.getPassword(), findMember.getPassword())) {
            throw new UsernameNotFoundException("비밀번호를 다시 확인해주세요.");
        }
        return findMember;
    }

    public Member verifyLogInMemberMatchesMember(long userId, long memberId) {
        Member findMember = findVerifiedMember(memberId);
        Member userMember = findVerifiedMember(userId);

        if(!findMember.equals(userMember)){
            throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND);
        }
        return findMember;
    }

    @Transactional(readOnly = true)
    public Member findVerifiedMember(long memberId){
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        Member findMember = optionalMember.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
        return findMember;
    }

    private Member verifyExistsEmail(String email) {
        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        return optionalMember.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.MEMBER_EXISTS));
    }
}

