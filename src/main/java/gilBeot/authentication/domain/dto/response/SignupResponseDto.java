package gilBeot.authentication.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class SignupResponseDto {
    private final String username;
    private final String message;
}
