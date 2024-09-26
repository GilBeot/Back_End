package gilBeot.board.converter;

import gilBeot.board.domain.CommentDomain;
import gilBeot.board.entity.CommentEntity;

public interface CommentConverter {
    CommentEntity toEntity(CommentDomain commentDomain);

    CommentDomain toDomain(CommentEntity commentEntity);
}
