package gilBeot.authentication.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Builder
@Table(name = "member")
public class MemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = true, unique = true)
    private String username; //고유한 닉네임
    @Column(nullable = true)
    private String name; //이름
    @Column(nullable = true, unique = true)
    private String email; //이메일
    @Column(nullable = true)
    private String password;
    @Column(nullable = true)
    private String role;
}
