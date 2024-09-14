package gilBeot.board.service;

import gilBeot.board.domain.dto.request.BoardRequestDto;
import gilBeot.board.domain.dto.response.BoardResponseDto;

public interface BoardService {

    BoardResponseDto save(BoardRequestDto boardRequestDto);

    void delete(Long id);

    BoardResponseDto update(Long id, BoardRequestDto boardRequestDto);
}
