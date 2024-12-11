package woongjin.gatherMind.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

@Configuration
public class OAuth2Config {

    @Value("${security.oauth2.client.registration.google.client-id:default-client-id}")
    private String clientId;

    @Value("${security.oauth2.client.registration.google.client-secret:default-client-secret}")
    private String clientSecret;


    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(googleClientRegistration());
    }

    private ClientRegistration googleClientRegistration() {
        return ClientRegistration.withRegistrationId("google")
                .clientId(clientId) // Google Client ID
                .clientSecret(clientSecret) // Google Client Secret
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST) // POST 방식 인증
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE) // Authorization Code Grant
                .redirectUri("http://localhost:8080/login/oauth2/code/google") // Redirect URI
                .scope("openid", "profile", "email") // OAuth 범위
                .authorizationUri("https://accounts.google.com/o/oauth2/v2/auth") // Authorization URL
                .tokenUri("https://oauth2.googleapis.com/token") // Token URL
                .userInfoUri("https://www.googleapis.com/oauth2/v3/userinfo") // 사용자 정보 URL
                .userNameAttributeName("sub") // 사용자 고유 ID 필드 (Google에서 "sub" 사용)
                .clientName("Google") // 클라이언트 이름
                .jwkSetUri("https://www.googleapis.com/oauth2/v3/certs") // JWK Set URI (Google)
                .build();
    }
}
