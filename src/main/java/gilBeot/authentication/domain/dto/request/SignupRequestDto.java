package gilBeot.authentication.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class SignupRequestDto {

    private String role;
    private String username; //고유한 닉네임
    private String name; //이름
    private String email;
    private String password;
}
