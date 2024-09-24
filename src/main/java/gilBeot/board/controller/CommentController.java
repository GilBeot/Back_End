package gilBeot.board.controller;

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

    // 댓글 작성
    @PostMapping("/board/{boardId}")
    public ResponseEntity<CommentEntity> addComment(@PathVariable Long boardId, @RequestBody String content, @RequestParam String author) {
        CommentEntity comment = commentService.addComment(boardId, content, author);
        return ResponseEntity.status(HttpStatus.CREATED).body(comment);
    }

    // 대댓글 작성
    @PostMapping("/reply/{commentId}")
    public ResponseEntity<CommentEntity> addReply(@PathVariable Long commentId, @RequestBody String content, @RequestParam String author) {
        CommentEntity reply = commentService.addReply(commentId, content, author);
        return ResponseEntity.status(HttpStatus.CREATED).body(reply);
    }
}