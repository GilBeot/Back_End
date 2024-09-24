package gilBeot.board.service;

import gilBeot.board.entity.CommentEntity;

public interface CommentService {

    CommentEntity addComment(Long boardId, String content, String author); // 댓글 작성

    CommentEntity addReply(Long parentCommentId, String content, String author); // 대댓글 작성

}