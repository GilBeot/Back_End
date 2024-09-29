package gilBeot.board.repository;

import gilBeot.board.domain.BoardDomain;
import gilBeot.board.domain.CommentDomain;

import java.util.List;
import java.util.Optional;

public interface BoardRepository {

    BoardDomain save(BoardDomain boardDomain);

    void deleteById(Long id);

    Optional<BoardDomain> findById(Long id);

    List<BoardDomain> findAll();

    CommentDomain addComment(BoardDomain boardDomain, CommentDomain commentDomain);
}
