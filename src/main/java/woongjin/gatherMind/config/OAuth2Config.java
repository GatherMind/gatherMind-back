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

    @Value("${spring.security.oauth2.client.registration.google.client-id:default-client-id}")
    private String googleClientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret:default-client-secret}")
    private String googleClientSecret;

    @Value("${spring.security.oauth2.client.registration.github.client-id:default-client-id}")
    private String githubClientId;

    @Value("${spring.security.oauth2.client.registration.github.client-secret:default-client-secret}")
    private String githubClientSecret;

    @Value("${spring.security.oauth2.client.registration.kakao.client-id:default-client-id}")
    private String kakaoClientId;


    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(
                googleClientRegistration(),
                githubClientRegistration(),
                kakaoClientRegistration()
        );
    }

    private ClientRegistration googleClientRegistration() {
        return ClientRegistration.withRegistrationId("google")
                .clientId(googleClientId) // Google Client ID
                .clientSecret(googleClientSecret) // Google Client Secret
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

    private ClientRegistration githubClientRegistration() {
        return ClientRegistration.withRegistrationId("github")
                .clientId(githubClientId) // GitHub Client ID
                .clientSecret(githubClientSecret) // GitHub Client Secret
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST) // POST 방식 인증
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE) // Authorization Code Grant
                .redirectUri("http://localhost:8080/login/oauth2/code/github") // Redirect URI
                .scope("user:email", "read:user") // OAuth 범위
                .authorizationUri("https://github.com/login/oauth/authorize") // Authorization URL
                .tokenUri("https://github.com/login/oauth/access_token") // Token URL
                .userInfoUri("https://api.github.com/user") // 사용자 정보 URL
                .userNameAttributeName("id") // 사용자 고유 ID 필드 (GitHub에서 "id" 사용)
                .clientName("GitHub") // 클라이언트 이름
                .build();
    }

    private ClientRegistration kakaoClientRegistration() {
        return ClientRegistration.withRegistrationId("kakao")
                .clientId(kakaoClientId)
//                .clientSecret("your-kakao-client-secret")
                .scope( "profile_nickname")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .redirectUri("{baseUrl}/login/oauth2/code/kakao")
                .authorizationUri("https://kauth.kakao.com/oauth/authorize")
                .tokenUri("https://kauth.kakao.com/oauth/token")
                .userInfoUri("https://kapi.kakao.com/v2/user/me")
                .userNameAttributeName("id")
                .build();
    }
}
