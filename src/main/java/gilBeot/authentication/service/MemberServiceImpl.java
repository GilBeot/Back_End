package gilBeot.authentication.service;

import gilBeot.authentication.domain.MemberDomain;
import gilBeot.authentication.domain.dto.response.MemberResponseDto;
import gilBeot.authentication.entity.MemberEntity;
import gilBeot.authentication.domain.dto.request.SignupRequestDto;
import gilBeot.authentication.domain.dto.response.SignupResponseDto;
import gilBeot.authentication.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public SignupResponseDto signUp(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();

        boolean isExist = memberRepository.existsByUsername(username);

        if (isExist) {
            return SignupResponseDto.builder()
                    .username(username)
                    .message("이미 가입된 회원입니다.")
                    .build();
        }

        memberRepository.save(
                MemberDomain.builder()
                        .username(username)
                        .name(signupRequestDto.getName())
                        .email(signupRequestDto.getEmail())
                        .password(bCryptPasswordEncoder.encode(signupRequestDto.getPassword()))
                        .role("ROLE_USER")
                        .build());

        return SignupResponseDto.builder()
                .username(signupRequestDto.getUsername())
                .message("회원가입이 성공했습니다.")
                .build();
    }

    @Override
    public MemberResponseDto findByUsername(String username) {
        MemberDomain findedMember = memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("찾는 회원이 없습니다." + username));

        return MemberResponseDto.builder()
                .id(findedMember.getId())
                .username(findedMember.getUsername())
                .name(findedMember.getName())
                .email(findedMember.getEmail())
                .build();
    }
}
