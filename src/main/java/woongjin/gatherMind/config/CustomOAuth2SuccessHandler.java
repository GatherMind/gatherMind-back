package woongjin.gatherMind.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import woongjin.gatherMind.entity.Member;
import woongjin.gatherMind.enums.CustomAuthProvider;
import woongjin.gatherMind.exception.notFound.AuthProviderNotFoundException;
import woongjin.gatherMind.service.MemberService;

import java.io.IOException;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class CustomOAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberService memberService;

    @Value("${server.port}")
    private String serverPort;
    @Value("${server.serverAddress}")
    private String serverAddress;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        Map<String, Object> attributes = token.getPrincipal().getAttributes();


        String provider = token.getAuthorizedClientRegistrationId(); // "google" 등 제공자 ID

        String oauthId;
        String email;
        String name;

        if ("google".equals(provider)) {
            // Google 사용자 정보 매핑
            oauthId = (String) attributes.get("sub");
            email = (String) attributes.get("email");
            name = (String) attributes.get("name");
        } else if ("github".equals(provider)) {
            // GitHub 사용자 정보 매핑
            oauthId = String.valueOf(attributes.get("id")); // GitHub의 ID는 Long 타입이므로 String으로 변환
            email = (String) attributes.get("email"); // GitHub API에서 이메일이 제공되지 않을 수도 있음
            if (email == null) {
                email = oauthId + "@github.com"; // 이메일이 없을 경우 기본값 설정 (필요 시 사용자 입력 요구)
            }
            name = (String) attributes.get("name");
            if (name == null) {
                name = (String) attributes.get("login"); // GitHub에서 이름 대신 login 사용
            }
        } else if ("kakao".equals(provider)) {
            Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
            Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
            oauthId = String.valueOf(attributes.get("id"));
            email = (String) kakaoAccount.get("email");
            name = (String) profile.get("nickname");

            if (email == null) {
                email = oauthId + "@kakao.com"; // 이메일이 없으면 기본값 제공
            }
        } else {
            throw new AuthProviderNotFoundException("Unsupported provider: " + provider);
        }
        CustomAuthProvider customAuthProvider = CustomAuthProvider.fromProviderName(provider);

        Member member = memberService.saveOrUpdateOAuthUser(customAuthProvider, oauthId, email, name);
        // JWT 생성 등 추가 로직 수행 가능
        String jwt = jwtTokenProvider.generateTokenWithRole(member);

        // 리다이렉트 URL에 JWT 추가
        String redirectUrl = "http://"+serverAddress+":"+serverAddress+"/dashboard?token=" + jwt;
        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }


}
