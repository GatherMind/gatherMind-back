package woongjin.gatherMind.config;

import lombok.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OAuthConfig {

    @Value("${google.client.id}")
    private String googleClientId;

    @Value("${google.client.secret}")
    private String googleClientSecret;

    @Value("${github.clientId}")
    private String githubClientId;

    @Value("${github.client.secret}")
    private String githubClientSecret;

    @Value("${kakao.client.id}")
    private String kakaoClientId;

    @Value("${kakao.client.secret}")
    private String kakaoClientSecret;

    @Value("${naver.client.id}")
    private String naverClientId;

    @Value("${naver.client.secret}")
    private String naverClientSecret;

    @Bean
    public GithubClient githubClient() {
        return new GithubClient(githubClientId, githubClientSecret);
    }

    @Bean
    public GoogleClient googleClient() {
        return new GoogleClient(googleClientId, googleClientSecret);
    }

    @Bean
    public KakaoClient kakaoClient() {
        return new KakaoClient(kakaoClientId, kakaoClientSecret);
    }

    @Bean
    public NaverClient naverClient() {
        return new NaverClient(naverClientId, naverClientSecret);
    }

}
