package gilBeot.board.domain.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BoardResponseDto {
    private Long id;
    private String title;
    private String content;
    private String author;
    private String createdTime;
    private String updatedTime;
}
