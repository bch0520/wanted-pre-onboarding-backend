package com.example.wantedpreonboardingbackend.board.entity;

import com.example.wantedpreonboardingbackend.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long boardId;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 300)
    private String content;

    //@Enumerated(value = EnumType.STRING)
    //private BoardStatus boardStatus = BoardStatus.BOARD_RESISTRATION;

    public enum BoardStatus {

        BOARD_RESISTRATION("게시물등록"),
        BOARD_DELETE("게시물삭제");

        @Getter
        private String questDec;
        BoardStatus(String questDec) {
            this.questDec = questDec;
        }


    }

}
