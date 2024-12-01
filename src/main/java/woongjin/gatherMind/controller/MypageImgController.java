package woongjin.gatherMind.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import woongjin.gatherMind.service.JwtService;
import woongjin.gatherMind.service.ProfileImgService;

import java.io.IOException;

@RestController
@RequestMapping("/api/mypage")
@RequiredArgsConstructor
public class MypageImgController {

    private final ProfileImgService profileImgService;
    private final JwtService jwtService;

    /**
     * 프로필 이미지 업로드
     */
    @PostMapping("/update-member-profile-image")
    public ResponseEntity<String> updateMemberProfileImage(
            @RequestHeader("Authorization") String token,
            @RequestParam("file") MultipartFile file) {
        try {
            // 1. 토큰에서 사용자 ID 추출
            String memberId = jwtService.verifyTokenAndGetMemberId(token);

            // 2. 서비스 호출: 프로필 이미지 업데이트
            profileImgService.updateMemberImage(memberId, file);

            // 3. 성공 응답
            return ResponseEntity.ok("프로필 이미지가 성공적으로 업데이트되었습니다.");
        } catch (IllegalArgumentException e) {
            // 4. 토큰 검증 실패 또는 유효하지 않은 사용자
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("토큰이 유효하지 않거나 사용자를 찾을 수 없습니다: " + e.getMessage());
        } catch (IOException e) {
            // 5. 파일 처리 오류
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("이미지 업로드 실패: " + e.getMessage());
        }
    }

    /**
     * 프로필 이미지 가져오기
     */
    @GetMapping("/member-profile-image")
    public ResponseEntity<byte[]> getMemberProfileImage(@RequestHeader("Authorization") String token) {
        System.out.println("=== 프로필 이미지 조회 컨트롤러 실행 ===");

        try {
            // JWT 토큰에서 멤버 ID 추출
            String memberId = jwtService.verifyTokenAndGetMemberId(token);
            System.out.println("토큰 검증 성공: memberId = " + memberId);
            // 서비스 호출: 프로필 이미지 데이터 가져오기
            byte[] imageBytes = profileImgService.getMemberProfileImage(memberId);

            if (imageBytes.length > 0) {
                // 이미지 반환
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.IMAGE_JPEG); // MIME 타입 설정
                return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
            } else {
                // 이미지가 없는 경우
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IllegalArgumentException e) {
            // 유효하지 않은 토큰
            System.err.println("토큰 검증 실패: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            // 기타 서버 오류
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
