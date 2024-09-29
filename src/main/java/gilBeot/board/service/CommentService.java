package gilBeot.board.service;

import gilBeot.board.domain.dto.request.CommentRequestDto;
import gilBeot.board.domain.dto.response.CommentResponseDto;

public interface CommentService {

    CommentResponseDto addComment(Long boardId, CommentRequestDto commentRequestDto); // 댓글 작성

    CommentResponseDto addReply(Long parentCommentId, CommentRequestDto commentRequestDto);

}