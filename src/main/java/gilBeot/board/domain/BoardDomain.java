package gilBeot.board.domain;

import gilBeot.board.domain.dto.request.BoardRequestDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class BoardDomain {

    private Long id;
    private String boardWriter;
    private String boardTitle;
    private String boardContents;

    public static BoardDomain create(BoardRequestDto boardRequestDto) {
        return BoardDomain.builder()
                .boardWriter(boardRequestDto.getBoardWriter())
                .boardTitle(boardRequestDto.getBoardTitle())
                .boardContents(boardRequestDto.getBoardContents())
                .build();
    }

    public void update(BoardRequestDto boardRequestDto) {
        this.boardTitle = boardRequestDto.getBoardTitle();
        this.boardContents = boardRequestDto.getBoardContents();
    }
}
