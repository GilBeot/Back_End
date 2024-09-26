package gilBeot.board.domain;

import gilBeot.board.domain.dto.request.BoardRequestDto;
import gilBeot.board.entity.CommentEntity;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class BoardDomain {

    private Long id;
    private String title;
    private String content;
    private String author;
    private List<CommentDomain> comments = new ArrayList<>();

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
