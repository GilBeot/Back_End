package gilBeot.global;

import gilBeot.authentication.domain.dto.jwt.JWTFilter;
import gilBeot.authentication.domain.dto.jwt.JWTUtil;
import gilBeot.authentication.domain.dto.jwt.CustomUsernamePasswordAuthenticationFilter;
import gilBeot.authentication.handler.OAuth2LoginSuccessHandler;
import gilBeot.authentication.service.GilBeotOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final JWTUtil jwtUtil;
    private final GilBeotOAuth2UserService gilBeotOAuth2UserService;
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.cors(cors -> cors.disable());
        http.csrf(csrf -> csrf.disable());
        http.formLogin(form -> form.disable());
        http.httpBasic(basic -> basic.disable());

//        http.oauth2Login(oauth2 -> oauth2
//                .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig.userService(gilBeotOAuth2UserService))
//                .successHandler(oAuth2LoginSuccessHandler));

        http.authorizeHttpRequests(
                auth -> auth
//                        .requestMatchers("/admin", "/login").permitAll()
                        .requestMatchers("/api/v1/members/**", "/api/v1/board/**", "/admin", "/login").permitAll()
                        .anyRequest().authenticated()
        );

//        http.addFilterBefore(new JWTFilter(jwtUtil), LoginFilter.class); // 로그인 필터 앞에 추가
//        http.addFilterAfter(new JWTFilter(jwtUtil), OAuth2LoginAuthenticationFilter.class);
//        http.addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil), UsernamePasswordAuthenticationFilter.class);

        http.addFilterAt(new CustomUsernamePasswordAuthenticationFilter(authenticationManager(authenticationConfiguration), jwtUtil), UsernamePasswordAuthenticationFilter.class); // 로그인 필터 대체
        http.addFilterAfter(new JWTFilter(jwtUtil), CustomUsernamePasswordAuthenticationFilter.class); // 로그인 필터 뒤에 추가
//        http.addFilterAfter(new JWTFilter(jwtUtil), OAuth2LoginAuthenticationFilter.class); // 소셜 로그인 뒤에 추가


        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}
