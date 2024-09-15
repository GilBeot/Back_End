package gilBeot.board.converter;

import gilBeot.board.domain.BoardDomain;
import gilBeot.board.entity.BoardEntity;

public interface BoardConverter {
    BoardEntity toEntity(BoardDomain boardDomain);

    BoardDomain toDomain(BoardEntity boardEntity);
}
