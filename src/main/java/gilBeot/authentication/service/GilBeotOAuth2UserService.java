package gilBeot.authentication.service;

import gilBeot.authentication.domain.GilBeotOAuth2MemberDomain;
import gilBeot.authentication.domain.MemberDomain;
import gilBeot.authentication.domain.OAuth2Response;
import gilBeot.authentication.domain.dto.request.SignupRequestDto;
import gilBeot.authentication.domain.dto.response.KakaoResponseDto;
import gilBeot.authentication.entity.MemberEntity;
import gilBeot.authentication.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GilBeotOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        //OAuth2UserRequest: 리소스 서버에서 제공되는 유저 정보

        OAuth2User oAuth2User = super.loadUser(userRequest);

        OAuth2Response oAuth2Response = new KakaoResponseDto(oAuth2User.getAttributes());

        String username = oAuth2Response.getProvider() + " " + oAuth2Response.getProviderId();
        Optional<MemberDomain> existDataOptional = memberRepository.findByUsername(username); //DB에 해당 유저가 이미 로그인해서 존재했는지 조회

        if (existDataOptional.isEmpty()) { //존재하지 않는 경우

            MemberDomain newMember = MemberDomain.builder()
                    .username(username)
                    .email(oAuth2Response.getEmail())
                    .password("kakaoLogin")
                    .role("ROLE_USER")
                    .build();

            memberRepository.save(newMember);

            SignupRequestDto signupRequestDto = SignupRequestDto.builder()
                    .username(username)
                    .email(oAuth2Response.getEmail())
                    .role("ROLE_USER")
                    .build();

            return new GilBeotOAuth2MemberDomain(signupRequestDto);
        } else { //한 번이라도 로그인해서 데이터가 존재하는 경우
            MemberDomain existData = existDataOptional.get(); // Optional에서 실제 데이터 꺼냄

            // 이메일이 변경된 경우 업데이트
            if (!existData.getEmail().equals(oAuth2Response.getEmail())) {
                existData.setEmail(oAuth2Response.getEmail());
            }

            memberRepository.save(existData);

            SignupRequestDto signupRequestDto = SignupRequestDto.builder()
                    .username(existData.getUsername())
                    .role(existData.getRole())
                    .build();

            return new GilBeotOAuth2MemberDomain(signupRequestDto);
        }
    }
}
