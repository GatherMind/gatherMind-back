package woongjin.gatherMind.service;

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

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    public CustomOAuth2UserService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 부모 클래스인 DefaultOAuth2UserService를 사용해 OAuth2User 정보를 가져옴
        OAuth2User oauth2User = super.loadUser(userRequest);

        String provider = userRequest.getClientRegistration().getRegistrationId();
        String oauthId = oauth2User.getAttribute("sub"); // Google에서 반환하는 고유 ID
        String email = oauth2User.getAttribute("email") != null
                ? oauth2User.getAttribute("email")
                : oauthId + "@example.com"; // 이메일 속성
        String name = oauth2User.getAttribute("name");

        // 사용자 정보로 새 Member 생성 또는 기존 사용자 조회
        CustomAuthProvider authProvider = CustomAuthProvider.valueOf(provider.toUpperCase());
        Optional<Member> existingMember = memberRepository.findByOauthIdAndOauthProvider(oauthId, authProvider);

        Member member = existingMember.orElseGet(() -> {
            Member newMember = new Member();
            newMember.setMemberId(UUID.randomUUID().toString());
            newMember.setOauthId(oauthId);
            newMember.setOauthProvider(authProvider);
            newMember.setEmail(email);
            newMember.setNickname(name);
            newMember.setCreatedAt(LocalDateTime.now());
            return memberRepository.save(newMember);
        });

        // 사용자 권한 설정 및 반환
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                oauth2User.getAttributes(),
                "sub" // 사용자 고유 ID를 key로 사용
        );
    }
}
