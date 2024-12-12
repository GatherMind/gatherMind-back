package woongjin.gatherMind.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import woongjin.gatherMind.auth.CustomOAuth2SuccessHandler;
import woongjin.gatherMind.auth.JwtAuthenticationFilter;
import woongjin.gatherMind.service.CustomOAuth2UserService;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomOAuth2SuccessHandler customOAuth2SuccessHandler;

    @Autowired
    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter, CustomOAuth2UserService customOAuth2UserService,
                          CustomOAuth2SuccessHandler customOAuth2SuccessHandler) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.customOAuth2UserService = customOAuth2UserService;
        this.customOAuth2SuccessHandler = customOAuth2SuccessHandler;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/h2-console/**") // H2 Console에 대해 CSRF 비활성화
                        .disable()
                )
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(
//                                "/**",
//                                "/api/member/**",
                                "/api/auth/**",
                                "/api/member/check-email",
                                "/api/member/check-nickname",
                                "/api/member/check-memberId",
                                "/api/study/getallstudies",
                                "/api/study-categories/**",
                                "/h2-console/**",
                                "/oauth2/**",
                                "/login"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login") // 커스텀 로그인 페이지
                        .authorizationEndpoint(auth -> auth
                                .baseUri("/oauth2/authorization")) // OAuth2 인증 엔드포인트
                        .successHandler(customOAuth2SuccessHandler) // 성공 핸들러 사용
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService)) // 사용자 정보를 로드하는 서비스
                )
                .headers(headers -> headers
                        .defaultsDisabled() // 기본 헤더 설정을 비활성화하고 필요한 헤더만 추가
                        .frameOptions(frameOptions -> frameOptions.sameOrigin()) // 같은 출처의 frame 허용
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .cors(cors -> cors.configurationSource(corsConfigurationSource())); // 새로운 CORS 설정 방식;

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000")); // 프론트엔드 주소
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}