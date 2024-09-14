package gilBeot.authentication.service;

import gilBeot.authentication.domain.Member;
import gilBeot.authentication.dto.CustomUserDetails;
import gilBeot.authentication.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //DB에서 특정 유저를 조회해서 리턴
        Member member = memberRepository.findByUsername(username);

        if (member == null) {
            // 유저가 없으면 예외를 던짐
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        // 유저가 존재하면 CustomUserDetails로 감싸서 리턴
        return new CustomUserDetails(member);
    }
}
