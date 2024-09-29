package gilBeot.board.controller;

import gilBeot.board.domain.dto.request.BoardRequestDto;
import gilBeot.board.domain.dto.response.BoardResponseDto;
import gilBeot.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    // 게시글 생성
    @PostMapping("/create")
    public ResponseEntity<Void> create(@RequestBody BoardRequestDto boardRequestDto) {
        boardService.save(boardRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // 게시글 삭제
    @DeleteMapping("/{board_id}")
    public ResponseEntity<Void> delete(@PathVariable Long board_id) {
        boardService.delete(board_id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    // 게시글 수정
    @PutMapping("/{board_id}")
    public ResponseEntity<BoardResponseDto> update(@PathVariable Long board_id, @RequestBody BoardRequestDto boardRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(boardService.update(board_id, boardRequestDto));
    }

    // 게시글 단건 조회
    @GetMapping("/{board_id}")
    public ResponseEntity<BoardResponseDto> findById(@PathVariable Long board_id) {
        BoardResponseDto boardResponseDto = boardService.findById(board_id);
        return ResponseEntity.status(HttpStatus.OK).body(boardResponseDto);
    }

    // 전체 게시글 조회
    @GetMapping("/list")
    public ResponseEntity<List<BoardResponseDto>> findAll() {
        List<BoardResponseDto> boards = boardService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(boards);
    }
}
