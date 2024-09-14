package gilBeot.board.controller;

import gilBeot.board.domain.dto.request.BoardRequestDto;
import gilBeot.board.domain.dto.response.BoardResponseDto;
import gilBeot.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/save")
    public ResponseEntity<BoardResponseDto> saveBoard(@RequestBody BoardRequestDto boardRequestDTO) { //저장
        return ResponseEntity.status(HttpStatus.CREATED).body(boardService.save(boardRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long id) {
        boardService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<BoardResponseDto> updateBoard(@PathVariable Long id, @RequestBody BoardRequestDto boardRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(boardService.update(id, boardRequestDto));
    }
}
