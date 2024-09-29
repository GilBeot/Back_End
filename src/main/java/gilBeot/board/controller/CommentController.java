package gilBeot.board.controller;

import gilBeot.board.domain.dto.request.CommentRequestDto;
import gilBeot.board.domain.dto.response.CommentResponseDto;
import gilBeot.board.entity.CommentEntity;
import gilBeot.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/board/{board_id}")
    public ResponseEntity<CommentResponseDto> addComment(@PathVariable Long board_id, @RequestBody CommentRequestDto commentRequestDto) {
        CommentResponseDto commentResponseDto = commentService.addComment(board_id, commentRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(commentResponseDto);
    }

//    @PostMapping("/reply/{comment_id}")
//    public ResponseEntity<CommentResponseDto> addReply(@PathVariable Long comment_id, @RequestBody CommentRequestDto commentRequestDto) {
//        CommentResponseDto commentResponseDto = commentService.addReply(comment_id, commentRequestDto);
//        return ResponseEntity.status(HttpStatus.CREATED).body(commentResponseDto);
//    }
}