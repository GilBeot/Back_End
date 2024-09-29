package gilBeot.board.domain;

import gilBeot.board.entity.BoardEntity;
import gilBeot.board.entity.CommentEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class CommentDomain {
    private Long id;
    private String content;
    private String author; // 댓글 작성자
    private BoardEntity board;
    private CommentEntity parentComment;
    private List<CommentEntity> childComments = new ArrayList<>();
}
