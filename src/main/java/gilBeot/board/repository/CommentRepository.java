package gilBeot.board.repository;

import gilBeot.board.domain.BoardDomain;
import gilBeot.board.domain.CommentDomain;

import java.util.Optional;

public interface CommentRepository {
    CommentDomain save(CommentDomain commentDomain);

    Optional<CommentDomain> findById(Long id);

    CommentDomain addChildComment(CommentDomain parentComment, CommentDomain reply);
}
