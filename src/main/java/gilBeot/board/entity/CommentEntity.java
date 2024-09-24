package gilBeot.board.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Table(name = "comment")
public class CommentEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String author; // 댓글 작성자

    // 게시글과 댓글의 관계 설정 (다대일)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private BoardEntity board;

    // 대댓글을 위한 자기 참조 관계 설정 (부모 댓글)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_comment_id")
    private CommentEntity parentComment;

    // 대댓글 리스트 (하위 댓글)
    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentEntity> childComments = new ArrayList<>();

    // 댓글에 대댓글 추가
    public void addChildComment(CommentEntity childComment) {
        this.childComments.add(childComment);
        childComment.setParentComment(this);
    }

    private void setParentComment(CommentEntity parentComment) {
        this.parentComment = parentComment;
    }

    public void setBoard(BoardEntity boardEntity) {
        this.board = boardEntity;
    }
}
