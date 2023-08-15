package com.example.wantedpreonboardingbackend.board.mapper;

import com.example.wantedpreonboardingbackend.board.dto.BoardPatchDto;
import com.example.wantedpreonboardingbackend.board.dto.BoardPostDto;
import com.example.wantedpreonboardingbackend.board.dto.BoardResponseDto;
import com.example.wantedpreonboardingbackend.board.entity.Board;
import com.example.wantedpreonboardingbackend.member.mapper.MemberMapper;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Builder
@Component
@RequiredArgsConstructor
public class BoardMapper {

    private final MemberMapper memberMapper;

    public Board boardPostDtoToBoard(BoardPostDto boardPostDto) {
        Board board = new Board();
        board.setTitle(boardPostDto.getTitle());
        board.setContent(boardPostDto.getContent());
        return board;
    }

    public Board boardPatchDtoToBoard(BoardPatchDto boardPatchDto) {
        Board board = new Board();

        board.setBoardId(boardPatchDto.getBoardId());
        board.setTitle(boardPatchDto.getTitle());
        board.setContent(boardPatchDto.getContent());
        return board;
    }

    public BoardResponseDto boardToBoardResponseDto(Board board) {
        BoardResponseDto boardResponseDto = new BoardResponseDto();
        boardResponseDto.setBoardId(board.getBoardId());
        boardResponseDto.setTitle(board.getTitle());
        boardResponseDto.setContent(board.getContent());
        boardResponseDto.setMember(memberMapper.memberToMemberResponseDto(board.getMember()));
        return boardResponseDto;
    }

    public Page<BoardResponseDto> boardPageToBoardResponseDtoPage(Page<Board> boardPage) {
        Page<BoardResponseDto> map = boardPage.map(board ->boardToBoardResponseDto(board));

        return map;
    }


}
