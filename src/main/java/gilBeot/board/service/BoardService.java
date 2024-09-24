package gilBeot.board.service;

import gilBeot.board.domain.dto.request.BoardRequestDto;
import gilBeot.board.domain.dto.response.BoardResponseDto;

import java.util.List;

public interface BoardService {

    BoardResponseDto save(BoardRequestDto boardRequestDto);

    void delete(Long id);

    BoardResponseDto update(Long id, BoardRequestDto boardRequestDto);

    // 단일 게시글 조회
    BoardResponseDto findById(Long id);

    // 전체 게시글 조회
    List<BoardResponseDto> findAll();
}
