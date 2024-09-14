package gilBeot.board.domain.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BoardResponseDto {
    private Long id;
    private String response;
}
