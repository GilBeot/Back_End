package gilBeot.board.service;

import gilBeot.board.domain.BoardDomain;
import gilBeot.board.domain.dto.request.BoardRequestDto;
import gilBeot.board.domain.dto.response.BoardResponseDto;
import gilBeot.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
        String username = getAuthenticatedUsername();
        BoardDomain boardDomain = BoardDomain.create(boardRequestDto, username);
        BoardDomain savedBoardDomain = boardRepository.save(boardDomain);

        return BoardResponseDto.builder()
                .id(savedBoardDomain.getId())
                .build();
    }


    @Override
    public void delete(Long id) {
        BoardDomain boardDomain = getBoardById(id);
        validateAuthor(boardDomain.getAuthor());

        boardRepository.deleteById(id);
    }

    @Override
    public BoardResponseDto update(Long id, BoardRequestDto boardRequestDto) {
        BoardDomain boardDomain = getBoardById(id);
        validateAuthor(boardDomain.getAuthor());

        boardDomain.update(boardRequestDto);
        BoardDomain updatedBoard = boardRepository.save(boardDomain);

        return BoardResponseDto.builder()
                .id(updatedBoard.getId())
                .title(updatedBoard.getTitle())
                .content(updatedBoard.getContent())
                .author(updatedBoard.getAuthor())
                .build();
    }

    @Override
    public BoardResponseDto findById(Long id) {
        BoardDomain boardDomain = getBoardById(id);

        return BoardResponseDto.builder()
                .id(boardDomain.getId())
                .title(boardDomain.getTitle())
                .content(boardDomain.getContent())
                .author(boardDomain.getAuthor())
                .build();
    }

    @Override
    public List<BoardResponseDto> findAll() {

        List<BoardDomain> boardDomains = boardRepository.findAll();

        return boardDomains.stream()
                .map(boardDomain -> BoardResponseDto.builder()
                        .id(boardDomain.getId())
                        .title(boardDomain.getTitle())
                        .content(boardDomain.getContent())
                        .author(boardDomain.getAuthor())
                        .build())
                .collect(Collectors.toList());
    }

    // 인증된 사용자의 username을 가져오는 메서드
    private String getAuthenticatedUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();  // 인증된 사용자의 username 반환
        }
    }

    private BoardDomain getBoardById(Long id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 게시글이 존재하지 않습니다."));
    }

    private void validateAuthor(String author) {
        String currentUsername = getAuthenticatedUsername();
        if (!author.equals(currentUsername)) {
            throw new IllegalStateException("작성자만 해당 작업을 수행할 수 있습니다.");
        }
    }

}
