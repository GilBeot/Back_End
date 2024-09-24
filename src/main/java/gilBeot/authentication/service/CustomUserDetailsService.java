package gilBeot.authentication.service;

import gilBeot.authentication.domain.MemberDomain;
import gilBeot.authentication.entity.MemberEntity;
import gilBeot.authentication.domain.dto.CustomUserDetails;
import gilBeot.authentication.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // DB에서 특정 유저를 조회하고 예외 처리
        MemberDomain memberDomain = memberRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        // 유저가 존재하면 CustomUserDetails로 감싸서 리턴
        return new CustomUserDetails(memberDomain);
    }
}
