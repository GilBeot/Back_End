package gilBeot.authentication.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Setter
@Getter
@ToString
public class MemberDomain {
    private Long id;
    private String username;
    private String name;
    private String email;
    private String password;
    private String role;
}
