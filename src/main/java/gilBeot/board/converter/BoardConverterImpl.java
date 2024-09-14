package gilBeot.board.converter;

import gilBeot.board.domain.BoardDomain;
import gilBeot.board.entity.BoardEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BoardConverterImpl implements BoardConverter{

    @Override
    public BoardEntity toEntity(BoardDomain boardDomain) {
        return BoardEntity.builder()
                .id(boardDomain.getId())
                .boardWriter(boardDomain.getBoardWriter())
                .boardTitle(boardDomain.getBoardTitle())
                .boardContents(boardDomain.getBoardContents())
                .build();
    }

    @Override
    public BoardDomain toDomain(BoardEntity boardEntity) {
        return BoardDomain.builder()
                .id(boardEntity.getId())
                .boardWriter(boardEntity.getBoardWriter())
                .boardTitle(boardEntity.getBoardTitle())
                .boardContents(boardEntity.getBoardContents())
                .build();
    }
}
