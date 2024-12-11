package woongjin.gatherMind.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import woongjin.gatherMind.entity.Member;
import woongjin.gatherMind.repository.MemberRepository;

import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CustomOAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        Map<String, Object> attributes = token.getPrincipal().getAttributes();

        String provider = token.getAuthorizedClientRegistrationId(); // "google" 등 제공자 ID
        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");

        Member member = memberRepository.findByEmail(email).orElseGet(() -> new Member(provider, email, name));

        // JWT 생성 등 추가 로직 수행 가능
        String jwt = jwtTokenProvider.createToken(email);

        // 리다이렉트 URL에 JWT 추가
        String redirectUrl = "http://localhost:3000/dashboard?token=" + jwt;
        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }

}
