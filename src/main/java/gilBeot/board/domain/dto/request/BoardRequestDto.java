package gilBeot.board.domain.dto.request;

import gilBeot.board.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardRequestDto {
    private Long id;
    private String title;
    private String content;
}
