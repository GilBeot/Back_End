package gilBeot.board.service;

import gilBeot.board.domain.BoardDomain;
import gilBeot.board.domain.CommentDomain;
import gilBeot.board.domain.dto.request.CommentRequestDto;
import gilBeot.board.domain.dto.response.CommentResponseDto;
import gilBeot.board.entity.BoardEntity;
import gilBeot.board.entity.CommentEntity;
import gilBeot.board.repository.BoardRepository;
import gilBeot.board.repository.CommentJpaRepository;
import gilBeot.board.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    @Override
    public CommentResponseDto addComment(Long boardId, CommentRequestDto commentRequestDto) {
        BoardDomain boardDomain = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다"));

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        CommentDomain commentDomain = CommentDomain.builder()
                .content(commentRequestDto.getContent())
                .author(username)
                .build();

        CommentDomain savedCommentDomain = boardRepository.addComment(boardDomain, commentDomain);

        return CommentResponseDto.builder()
                .boardId(boardDomain.getId())
                .author(savedCommentDomain.getAuthor())
                .content(savedCommentDomain.getContent())
                .build();
    }

    @Override
    public CommentResponseDto addReply(Long parentCommentId, CommentRequestDto commentRequestDto) {
        // 부모 댓글을 조회 (이미 저장된 상태여야 함)
        CommentDomain parentComment = commentRepository.findById(parentCommentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다"));

        // 부모 댓글이 이미 저장된 상태인지 확인
        if (parentComment.getId() == null) {
            throw new IllegalStateException("부모 댓글이 저장되지 않았습니다.");
        }

        // 현재 인증된 사용자 이름을 가져옴
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // 대댓글 생성 (부모 댓글과 동일한 게시글에 연관)
        CommentDomain reply = CommentDomain.builder()
                .content(commentRequestDto.getContent())
                .author(username)
                .build();

        // 부모 댓글과 연관된 대댓글 저장
        CommentDomain savedReply = commentRepository.addChildComment(parentComment, reply);

        // Response DTO 반환
        return CommentResponseDto.builder()
                .boardId(savedReply.getBoard().getId())  // 게시글 ID 반환
                .author(savedReply.getAuthor())  // 댓글 작성자 반환
                .content(savedReply.getContent())  // 대댓글 내용 반환
                .build();
    }

}
