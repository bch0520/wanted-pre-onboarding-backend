package com.example.wantedpreonboardingbackend.board.controller;

import com.example.wantedpreonboardingbackend.board.dto.BoardPatchDto;
import com.example.wantedpreonboardingbackend.board.dto.BoardPostDto;
import com.example.wantedpreonboardingbackend.board.dto.BoardResponseDto;
import com.example.wantedpreonboardingbackend.board.entity.Board;
import com.example.wantedpreonboardingbackend.board.mapper.BoardMapper;
import com.example.wantedpreonboardingbackend.board.service.BoardService;
import com.example.wantedpreonboardingbackend.security.UserDetailsImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@Validated
@CrossOrigin
@RestController
@RequestMapping(path = "/boards", produces = MediaType.APPLICATION_JSON_VALUE)
public class BoardController {

    private final BoardService boardService;
    private final BoardMapper mapper;

    public BoardController(BoardService boardService, BoardMapper mapper) {
        this.boardService = boardService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity postBoard(@RequestBody @Valid BoardPostDto boardPostDto,
                                    @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Board board = mapper.boardPostDtoToBoard(boardPostDto);
        Board response = boardService.createBoard(board, userDetails);
        return new ResponseEntity<>(mapper.boardToBoardResponseDto(response), HttpStatus.CREATED);
    }

    @PatchMapping("/{board-id}")
    public ResponseEntity patchBoard(@PathVariable("board-id") @Positive long boardId,
                                     @Valid @RequestBody BoardPatchDto boardPatchDto,
                                     @AuthenticationPrincipal UserDetailsImpl userDetails) {

        boardPatchDto.setBoardId(boardId);
        Board response = boardService.updateBoard(userDetails, mapper.boardPatchDtoToBoard(boardPatchDto));

        return new ResponseEntity<>(mapper.boardToBoardResponseDto(response), HttpStatus.OK);
    }

    @GetMapping("/{board-id}")
    public ResponseEntity getBoard(@PathVariable("board-id") @Positive long boardId) {

        Board response = boardService.findBoard(boardId);

        return new ResponseEntity<>(mapper.boardToBoardResponseDto(response), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<BoardResponseDto>> getBoards(@PageableDefault(size=10, page = 0, sort="boardId",direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Board> boardPage = boardService.getBoards(pageable);
        Page<BoardResponseDto> boardResponseDtoPage = mapper.boardPageToBoardResponseDtoPage(boardPage);
        return new ResponseEntity<>(boardResponseDtoPage, HttpStatus.OK);
    }

    @DeleteMapping("/{board-id}")
    public ResponseEntity deleteBoard(@PathVariable("board-id") @Positive long boardId,
                                      @AuthenticationPrincipal UserDetailsImpl userDetails) {

        boardService.deleteBoard(userDetails,boardId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }






}
