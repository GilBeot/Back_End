package gilBeot.board.repository;

import gilBeot.board.converter.BoardConverter;
import gilBeot.board.converter.CommentConverter;
import gilBeot.board.domain.BoardDomain;
import gilBeot.board.domain.CommentDomain;
import gilBeot.board.entity.BoardEntity;
import gilBeot.board.entity.CommentEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepository{

    private final BoardJpaRepository boardJpaRepository;
    private final CommentJpaRepository commentJpaRepository;
    private final BoardConverter boardConverter;
    private final CommentConverter commentConverter;

    @Override
    public BoardDomain save(BoardDomain boardDomain) {
        return boardConverter.toDomain(boardJpaRepository.save(boardConverter.toEntity(boardDomain)));
    }

    @Override
    public void deleteById(Long id) {
        boardJpaRepository.deleteById(id);
    }

    @Override
    public Optional<BoardDomain> findById(Long id) {
        return boardJpaRepository.findById(id)
                .map(boardConverter::toDomain);
    }

    @Override
    public List<BoardDomain> findAll() {
        return boardJpaRepository.findAll().stream()
                .map(boardConverter::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public CommentDomain addComment(BoardDomain boardDomain, CommentDomain commentDomain) {
        CommentEntity commentEntity = commentConverter.toEntity(commentDomain);
        BoardEntity boardEntity = boardConverter.toEntity(boardDomain);

        commentEntity.setBoard(boardEntity);

        CommentEntity savedCommentEntity = commentJpaRepository.save(commentEntity);

        return commentConverter.toDomain(savedCommentEntity);
    }
}
