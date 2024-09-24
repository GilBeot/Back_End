package gilBeot.authentication.domain;

import gilBeot.authentication.domain.dto.request.SignupRequestDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public class GilBeotOAuth2MemberDomain implements OAuth2User {

    private final SignupRequestDto signupRequestDto;

    @Override
    public Map<String, Object> getAttributes() { //받은 데이터를 리턴하는 것 : 사용 X -> username 같은 걸 반환해주는 건데, 네이버와 구글이 통일되어 있지 않아서 사용하기 애매함
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { //ROLE값 반환

        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {

            @Override
            public String getAuthority() {

                return signupRequestDto.getRole();
            }
        });

        return collection;
    }

    public String getUsername() {

        return signupRequestDto.getUsername();
    }

    @Override
    public String getName() {
        return signupRequestDto.getUsername();
    }
}
