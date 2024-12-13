package woongjin.gatherMind.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import woongjin.gatherMind.entity.Member;
import woongjin.gatherMind.enums.CustomAuthProvider;
import woongjin.gatherMind.repository.MemberRepository;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 부모 클래스의 사용자 정보 로딩
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // 사용자 속성 가져오기
        Map<String, Object> attributes = oAuth2User.getAttributes();

        // 클라이언트 등록 ID (google, github, kakao 등)
        String provider = userRequest.getClientRegistration().getRegistrationId();

        // 사용자 정보 추출
        String oauthId = extractOAuthId(attributes, provider);
        String email = extractEmail(attributes, provider);

        if (oauthId == null || oauthId.isEmpty()) {
            throw new IllegalArgumentException("OAuth ID cannot be null or empty for provider: " + provider);
        }

        // 사용자 데이터 처리 및 저장
        Member user = processOAuthUser(oauthId, email, provider);

        // Spring Security의 DefaultOAuth2User 반환
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                attributes,
                "email" // 사용자 주식별자
        );
    }

    private String extractOAuthId(Map<String, Object> attributes, String provider) {
        if ("google".equals(provider)) {
            return (String) attributes.get("sub"); // Google의 고유 ID
        } else if ("github".equals(provider)) {
            return String.valueOf(attributes.get("id")); // GitHub의 고유 ID
        } else if ("kakao".equals(provider)) {
            return String.valueOf(attributes.get("id")); // Kakao의 고유 ID
        } else if ("naver".equals(provider)) {
            Map<String, Object> response = (Map<String, Object>) attributes.get("response");
            return (String) response.get("id"); // Naver의 고유 ID
        }
        throw new IllegalArgumentException("Unsupported OAuth2 Provider: " + provider);
    }

    private String extractEmail(Map<String, Object> attributes, String provider) {
        if ("google".equals(provider)) {
            return (String) attributes.get("email");
        } else if ("github".equals(provider)) {
            return (String) attributes.get("email");
        } else if ("kakao".equals(provider)) {
            Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
            return (String) kakaoAccount.get("email");
        } else if ("naver".equals(provider)) {
            Map<String, Object> response = (Map<String, Object>) attributes.get("response");
            return (String) response.get("email");
        }
        throw new IllegalArgumentException("Unsupported OAuth2 Provider: " + provider);
    }

    private Member processOAuthUser(String oauthId, String email, String provider) {
        if (oauthId == null || email == null) {
            throw new IllegalArgumentException("OAuth ID and Email cannot be null");
        }

        // 기존 회원 확인 또는 새 회원 생성
        Optional<Member> existingUser = memberRepository.findByOauthId(oauthId);

        return existingUser.orElseGet(() -> {
            Member newMember = new Member();
            newMember.setMemberId(UUID.randomUUID().toString());
            newMember.setOauthId(oauthId);
            newMember.setEmail(email);
            newMember.setNickname(generateNickname(email));
            newMember.setOauthProvider(CustomAuthProvider.valueOf(provider.toUpperCase()));
            newMember.setProfileImage("/api/files/default-profile");
            newMember.setEmailVerified(true);
            newMember.setIsDeleted(false);

            return memberRepository.save(newMember);
        });
    }

    private String generateNickname(String email) {
        return email.split("@")[0] + UUID.randomUUID().toString().substring(0, 5);
    }
}
