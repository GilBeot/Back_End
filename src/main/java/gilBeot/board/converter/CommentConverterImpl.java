package gilBeot.board.converter;

import gilBeot.board.domain.CommentDomain;
import gilBeot.board.entity.BoardEntity;
import gilBeot.board.entity.CommentEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CommentConverterImpl implements CommentConverter{

    @Override
    public CommentEntity toEntity(CommentDomain commentDomain) {
        return CommentEntity.builder()
                .content(commentDomain.getContent())
                .author(commentDomain.getAuthor())
                .board(commentDomain.getBoard())
                .parentComment(commentDomain.getParentComment())
                .childComments(commentDomain.getChildComments())
                .build();
    }

    @Override
    public CommentDomain toDomain(CommentEntity commentEntity) {
        return CommentDomain.builder()
                .id(commentEntity.getId())
                .content(commentEntity.getContent())
                .author(commentEntity.getAuthor())
                .board(commentEntity.getBoard())
                .parentComment(commentEntity.getParentComment())
                .childComments(commentEntity.getChildComments())
                .build();
    }
}
