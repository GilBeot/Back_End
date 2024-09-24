package gilBeot.board.converter;

import gilBeot.board.domain.BoardDomain;
import gilBeot.board.entity.BoardEntity;
import jakarta.persistence.Column;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BoardConverterImpl implements BoardConverter{

    @Override
    public BoardEntity toEntity(BoardDomain boardDomain) {
        return BoardEntity.builder()
                .id(boardDomain.getId())
                .title(boardDomain.getTitle())
                .content(boardDomain.getContent())
                .author(boardDomain.getAuthor())
                .build();
    }


    @Override
    public BoardDomain toDomain(BoardEntity boardEntity) {
        return BoardDomain.builder()
                .id(boardEntity.getId())
                .title(boardEntity.getTitle())
                .content(boardEntity.getContent())
                .author(boardEntity.getAuthor())
                .build();
    }
}
