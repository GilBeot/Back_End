package gilBeot.authentication.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberRequestDTO { //회원 가입 받을 DTO
    private String username;
    private String password;
}
