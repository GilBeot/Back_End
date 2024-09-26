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
@Table(name = "board")
public class BoardEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;
    @Column(nullable = true)
    private String title;
    @Column(nullable = true)
    private String content;
    @Column(nullable = true)
    private String author; //작성자(usernmame)
    // 게시글과 댓글의 관계 (일대다)
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentEntity> comments = new ArrayList<>();

    // 댓글 추가
    public void addComment(CommentEntity comment) {
        this.comments.add(comment);
        comment.setBoard(this); // CommentEntity에 BoardEntity 설정
    }
}
