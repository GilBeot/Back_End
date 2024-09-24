package gilBeot.board.service;

import gilBeot.board.entity.BoardEntity;
import gilBeot.board.entity.CommentEntity;
import gilBeot.board.repository.BoardRepository;
import gilBeot.board.repository.CommentJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

    private final BoardRepository boardRepository;
    private final CommentJpaRepository commentRepository;
    @Override
    public CommentEntity addComment(Long boardId, String content, String author) {
//        BoardEntity board = boardRepository.findById(boardId)
//                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));
//
//        CommentEntity comment = CommentEntity.builder()
//                .content(content)
//                .author(author)
//                .board(board)
//                .build();
//
//        board.addComment(comment);
//        return commentRepository.save(comment);
        return null;
    }

    @Override
    public CommentEntity addReply(Long parentCommentId, String content, String author) {
        CommentEntity parentComment = commentRepository.findById(parentCommentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));

        CommentEntity reply = CommentEntity.builder()
                .content(content)
                .author(author)
                .parentComment(parentComment)
                .board(parentComment.getBoard())  // 동일 게시글에 추가
                .build();

        parentComment.addChildComment(reply);
        return commentRepository.save(reply);
    }
}
