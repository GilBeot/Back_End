package gilBeot.authentication.service;

import gilBeot.authentication.domain.Member;
import gilBeot.authentication.dto.request.MemberRequestDTO;
import gilBeot.authentication.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JoinService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void join(MemberRequestDTO memberRequestDTO) {
        String username = memberRequestDTO.getUsername();
        String password = memberRequestDTO.getPassword();

        Boolean isExist = memberRepository.existsByUsername(username);

        if (isExist) {
            return;
        }

        memberRepository.save(
                Member.builder()
                        .username(username)
                        .password(bCryptPasswordEncoder.encode(password))
                        .role("ROLE_ADMIN")
                        .build()
        );
    }
}
