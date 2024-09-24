package gilBeot.authentication.domain.dto.response;

import gilBeot.authentication.domain.OAuth2Response;

import java.util.Map;

public class KakaoResponseDto implements OAuth2Response {

    private final Map<String, Object> attributes;
    private final Map<String, Object> kakaoAccount;
    private final Map<String, Object> properties;

    public KakaoResponseDto(Map<String, Object> attributes) {
        this.attributes = attributes;
        this.kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        this.properties = (Map<String, Object>) attributes.get("properties");
    }

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getProviderId() {
        return attributes.get("id").toString();
    }

    @Override
    public String getEmail() {
        if (kakaoAccount.containsKey("email")) {
            return kakaoAccount.get("email").toString();
        }
        return "No email provided";
    }

    @Override
    public String getName() {
        if (properties != null && properties.containsKey("nickname")) {
            return properties.get("nickname").toString();
        }
        return "No name provided";
    }
}
