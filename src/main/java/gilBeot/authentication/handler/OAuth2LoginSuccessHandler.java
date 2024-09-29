package gilBeot.authentication.handler;

import gilBeot.authentication.domain.GilBeotOAuth2MemberDomain;
import gilBeot.authentication.domain.dto.jwt.JWTUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JWTUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //로그인하면 동작하게 됨
        //OAuth2User
        GilBeotOAuth2MemberDomain customUserDetails = (GilBeotOAuth2MemberDomain) authentication.getPrincipal();

        //JWT를 만들기 위해 username과 role값이 필요함
        String username = customUserDetails.getUsername();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        String token = jwtUtil.createJwt(username, role, 60*60*60L);

//        response.setHeader("Authorization", "Bearer " + token);
        response.addCookie(createCookie("Authorization", token));
//        response.sendRedirect("http://localhost:8080/api/v1/membersmyPage"); //프론트 측의 특정 URL로 리다이렉팅해라
//        response.setContentType("application/json");
//        response.setCharacterEncoding("UTF-8");
//        response.getWriter().write("{\"token\": \"" + token + "\"}");

    }

    private Cookie createCookie(String key, String value) {

        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(60*60*60);
        //cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        // JWT 로그에 출력
        System.out.println("OAuth2LoginSuccessHandler에서 생성한 JWT: " + value);

        return cookie;
    }
}
