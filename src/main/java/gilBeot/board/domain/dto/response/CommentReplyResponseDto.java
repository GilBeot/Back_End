package gilBeot.board.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentReplyResponseDto {

    private Long boardId;
    private Long commentId;
    private String author;
    private String content;
}
