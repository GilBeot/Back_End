package gilBeot.board.repository;

import gilBeot.board.converter.CommentConverter;
import gilBeot.board.domain.BoardDomain;
import gilBeot.board.domain.CommentDomain;
import gilBeot.board.entity.CommentEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepository {

    private final CommentJpaRepository commentJpaRepository;
    private final CommentConverter commentConverter;

    @Override
    public CommentDomain save(CommentDomain commentDomain) {
        return commentConverter.toDomain(commentJpaRepository.save(commentConverter.toEntity(commentDomain)));
    }

    @Override
    public Optional<CommentDomain> findById(Long id) {
        return commentJpaRepository.findById(id)
                .map(commentConverter::toDomain);
    }

    @Override
    public CommentDomain addChildComment(CommentDomain parentComment, CommentDomain reply) {
        CommentEntity parentCommentEntity = commentConverter.toEntity(parentComment);
        CommentEntity replyCommentEntity = CommentEntity.builder()
                .content(reply.getContent())
                .author(reply.getAuthor())
                .parentComment(parentCommentEntity)
                .board(parentCommentEntity.getBoard())
                .build();

        replyCommentEntity.setParentComment(parentCommentEntity);
        parentCommentEntity.addChildComment(replyCommentEntity);
        return commentConverter.toDomain(commentJpaRepository.save(replyCommentEntity));
    }
}
