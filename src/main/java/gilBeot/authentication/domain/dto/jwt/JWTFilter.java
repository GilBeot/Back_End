package gilBeot.authentication.domain.dto.jwt;

import gilBeot.authentication.domain.MemberDomain;
import gilBeot.authentication.entity.MemberEntity;
import gilBeot.authentication.domain.dto.CustomUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter { //요청에 대해 한 번만 동작하는 필터

    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // Step 1: Authorization 헤더에서 JWT 찾기
        String token = null;
        String authorization = request.getHeader("Authorization");

        if (authorization != null && authorization.startsWith("Bearer ")) {
            token = authorization.split(" ")[1];
        }

        // Step 2: 쿠키에서 JWT 찾기 (헤더에 없을 경우)
        if (token == null) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("Authorization")) {
                        token = cookie.getValue();  // 쿠키에서 JWT 가져오기
                        break;
                    }
                }
            }
        }

        System.out.println("JWTFilter에서 확인한 JWT: " + token);

        // Step 3: JWT가 없거나 유효하지 않으면 필터 체인 계속 진행
        if (token == null) {
            System.out.println("Token is missing");
            filterChain.doFilter(request, response);
            return; // 메소드 종료
        }

        // Step 4: 토큰 소멸 시간 검증
        if (jwtUtil.isExpired(token)) {
            System.out.println("Token is expired");
            filterChain.doFilter(request, response);
            return; // 메소드 종료
        }

        // Step 5: 토큰에서 username과 role 획득
        String username = jwtUtil.getUsername(token);
        String role = jwtUtil.getRole(token);

        // Step 6: MemberDomain 생성 및 CustomUserDetails 생성
        MemberDomain memberDomain = MemberDomain.builder()
                .username(username)
                .password("temppassword")
                .role(role)
                .build();

        CustomUserDetails customUserDetails = new CustomUserDetails(memberDomain);

        // Step 7: 스프링 시큐리티 인증 토큰 생성 및 세션에 등록
        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);

        // Step 8: 필터 체인 계속 진행
        filterChain.doFilter(request, response);
    }
}
