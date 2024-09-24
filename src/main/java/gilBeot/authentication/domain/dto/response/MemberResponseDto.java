package gilBeot.authentication.domain.dto.response;

import lombok.*;

@Getter
@Builder
public class MemberResponseDto {
    private Long id;
    private String username; //고유한 닉네임
    private String name; //이름
    private String email;
}
