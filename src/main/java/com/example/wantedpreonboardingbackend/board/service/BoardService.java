package com.example.wantedpreonboardingbackend.board.service;

import com.example.wantedpreonboardingbackend.board.entity.Board;
import com.example.wantedpreonboardingbackend.board.repository.BoardRepository;
import com.example.wantedpreonboardingbackend.exception.BusinessLogicException;
import com.example.wantedpreonboardingbackend.exception.ExceptionCode;
import com.example.wantedpreonboardingbackend.member.entity.Member;
import com.example.wantedpreonboardingbackend.member.repository.MemberRepository;
import com.example.wantedpreonboardingbackend.member.service.MemberService;
import com.example.wantedpreonboardingbackend.security.UserDetailsImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final MemberService memberService;

    public BoardService(BoardRepository boardRepository, MemberRepository memberRepository, MemberService memberService) {
        this.boardRepository = boardRepository;
        this.memberRepository = memberRepository;
        this.memberService = memberService;
    }



    public Board createBoard(Board board, UserDetailsImpl user) {
        verifyExistsTitle(board.getTitle());
        Optional<Member> findUserName = memberRepository.findByEmail(user.getUsername());
        board.setMember(findUserName.get());

        return boardRepository.save(board);

    }

    public Board updateBoard(UserDetailsImpl userDetails, Board board) {
        Board findBoard = findVerifiedBoard(board.getBoardId());

        Optional.ofNullable(board.getTitle())
                .ifPresent(title -> findBoard.setTitle(title));
        Optional.ofNullable(board.getContent())
                .ifPresent(content -> findBoard.setContent(content));

        return boardRepository.save(findBoard);
    }

    public Board findBoard(long boardId) {
        return findVerifiedBoard(boardId);
    }

    public Page<Board> getBoards(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }

    public void deleteBoard(UserDetailsImpl userDetails, long boardId) {
        Board findBoard = findVerifiedBoard(boardId);

        memberService.verifyLogInMemberMatchesMember(userDetails.getUserId(), findBoard.getMember().getMemberId());

        boardRepository.delete(findBoard);

    }

    public  Board findVerifiedBoard(long boardId) {
        Optional<Board> optionalBoard =
                boardRepository.findById(boardId);
        Board findBoard =
                optionalBoard.orElseThrow(() ->
                        new BusinessLogicException(ExceptionCode.BOARD_NOT_FOUND));
        return findBoard;
    }

    private void verifyExistsTitle(String title) {
        Optional<Board> board = boardRepository.findByTitle(title);
        if(board.isPresent())
            throw new BusinessLogicException(ExceptionCode.BOARD_EXISTS);
    }

}
