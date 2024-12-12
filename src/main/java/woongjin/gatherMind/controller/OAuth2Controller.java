package woongjin.gatherMind.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import woongjin.gatherMind.config.JwtTokenProvider;
import woongjin.gatherMind.entity.Member;
import woongjin.gatherMind.service.MemberService;

import java.util.Collections;

@RestController
@RequestMapping("/oauth2")
@RequiredArgsConstructor
public class OAuth2Controller {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberService memberService;

    @GetMapping("/login-success")
    public ResponseEntity<?> loginSuccess(@AuthenticationPrincipal OAuth2User oAuth2User) {
        try {
            // OAuth2User에서 사용자 정보 추출
            String email = oAuth2User.getAttribute("email");
            String name = oAuth2User.getAttribute("name");

            if (email == null || email.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User email not provided by OAuth2 provider.");
            }

            // 사용자 확인 또는 등록
            Member member = memberService.findByEmail(email);
            if (member == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found. Please sign up first.");
            }

            // JWT 토큰 생성
            String token = jwtTokenProvider.createToken(member.getMemberId());

            // 성공 응답 반환
            return ResponseEntity.ok(Collections.singletonMap("token", token));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("OAuth2 로그인 처리 중 오류 발생: " + e.getMessage());
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> oauth2Signup(@RequestBody Member member) {
        try {
            memberService.signupFromOAuth2(member);
            return ResponseEntity.status(HttpStatus.CREATED).body("회원가입이 완료되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("회원가입 중 오류 발생: " + e.getMessage());
        }
    }
}
