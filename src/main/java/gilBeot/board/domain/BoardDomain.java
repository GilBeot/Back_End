package gilBeot.board.domain;

import gilBeot.board.domain.dto.request.BoardRequestDto;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class BoardDomain {

    private Long id;
    private String title;
    private String content;
    private String author;

    public static BoardDomain create(BoardRequestDto boardRequestDto, String author) {
        return BoardDomain.builder()
                .title(boardRequestDto.getTitle())
                .content(boardRequestDto.getContent())
                .author(author)
                .build();
    }

    public void update(BoardRequestDto boardRequestDto) {
        this.title = boardRequestDto.getTitle();
        this.content = boardRequestDto.getContent();
    }

}
