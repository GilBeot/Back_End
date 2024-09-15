package gilBeot.board.service;

import gilBeot.board.domain.BoardDomain;
import gilBeot.board.domain.dto.request.BoardRequestDto;
import gilBeot.board.domain.dto.response.BoardResponseDto;
import gilBeot.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{
    /**
     * 주로 DTO -> Entity 변환하거나 Entity -> DTO로 변환하는 서비스
     * 컨트롤러로부터 호출 받을 땐 DTO로 넘겨 받고, 리포지토리에 넘겨줄 땐 Entity로 넘겨주고
     * DB로부터 조회할 땐 Entity로 받아오고, 컨트롤러로 넘겨줄 땐 DTO로 넘겨준다.
     */
    private final BoardRepository boardRepository;

    @Override
    public BoardResponseDto save(BoardRequestDto boardRequestDto) {
        BoardDomain boardDomain = BoardDomain.create(boardRequestDto);
        BoardDomain savedBoardDomain = boardRepository.save(boardDomain);
        return BoardResponseDto.builder()
                .id(savedBoardDomain.getId())
                .build();
    }

    @Override
    public void delete(Long id) {
        boardRepository.deleteById(id);
    }

    @Override
    public BoardResponseDto update(Long id, BoardRequestDto boardRequestDto) {
        BoardDomain existingBoard = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 게시글이 존재하지 않습니다."));

        existingBoard.update(boardRequestDto);
        BoardDomain updatedBoard = boardRepository.save(existingBoard);

        return BoardResponseDto.builder()
                .id(updatedBoard.getId())
                .build();
    }
}
