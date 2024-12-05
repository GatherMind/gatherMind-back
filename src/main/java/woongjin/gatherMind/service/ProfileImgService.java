package woongjin.gatherMind.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import woongjin.gatherMind.entity.Member;
import woongjin.gatherMind.repository.MemberRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileImgService {

    private final MemberRepository memberRepository;
    private final JwtService jwtService;

//    // 프로필 이미지 업데이트
//    public void updateMemberImage(String token, MultipartFile imgFile) throws IOException {
//        // JWT 토큰에서 멤버 ID 추출
//        String memberId = jwtService.verifyTokenAndGetMemberId(token);
//
//        Optional<Member> memberOptional = memberRepository.findById(memberId);
//        if (memberOptional.isPresent()) {
//            Member memberEntity = memberOptional.get();
//
//            // 기존 프로필 이미지 삭제
//            if (memberEntity.getMemberProfile() != null) {
//                deleteMemberImage(memberEntity.getMemberProfile());
//            }
//
//            // 새로운 프로필 이미지 저장
//            String savedFilePath = fileHandler(imgFile, memberId);
//            memberEntity.setMemberProfile(savedFilePath);
//
//            // DB 업데이트
//            memberRepository.save(memberEntity);
//        } else {
//            throw new IllegalArgumentException("해당 멤버를 찾을 수 없습니다.");
//        }
//    }

    // 파일 저장 처리 메서드
    private String fileHandler(MultipartFile file, String memberId) throws IOException {
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        String filePath = "path/to/upload/" + fileName; // 파일 저장 경로

        File dest = new File(filePath);
        file.transferTo(dest); // 파일 저장
        return filePath; // 저장된 파일 경로 반환
    }

    // 기존 이미지 삭제 메서드
    private void deleteMemberImage(String imagePath) {
        File file = new File(imagePath);
        if (file.exists() && file.delete()) {
            System.out.println("기존 이미지 삭제 성공: " + imagePath);
        } else {
            System.out.println("기존 이미지 삭제 실패 또는 파일 없음: " + imagePath);
        }
    }

    // 프로필 이미지 삭제
//    public ResponseEntity<String> deleteProfileImage(String memberId) {
//        Optional<Member> memberOptional = memberRepository.findById(memberId);
//        if (memberOptional.isPresent()) {
//            Member member = memberOptional.get();
//
//            try {
//                // 기존 이미지 파일 삭제
//                String imagePath = member.getMemberProfile();
//                if (imagePath != null) {
//                    File imageFile = new File(imagePath);
//                    if (imageFile.exists()) {
//                        if (imageFile.delete()) {
//                            // DB의 이미지 경로 삭제
//                            member.setMemberProfile(null);
//                            memberRepository.save(member);
//                            return ResponseEntity.ok("이미지 삭제 성공");
//                        } else {
//                            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("이미지 파일 삭제 실패");
//                        }
//                    } else {
//                        return ResponseEntity.ok("이미지 파일이 존재하지 않습니다.");
//                    }
//                } else {
//                    return ResponseEntity.ok("저장된 프로필 이미지가 없습니다.");
//                }
//            } catch (Exception e) {
//                System.err.println("이미지 삭제 실패: " + e.getMessage());
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("이미지 삭제 중 오류 발생: " + e.getMessage());
//            }
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 멤버를 찾을 수 없습니다.");
//        }
//    }

    // 사용자 프로필 이미지 가져오기
//    public byte[] getMemberProfileImage(String memberId) {
//        System.out.println("getUserProfileImage 실행");
//
//        Optional<Member> memberOptional = memberRepository.findById(memberId);
//        if (memberOptional.isPresent()) {
//            Member member = memberOptional.get();
//            String profileImagePath = member.getMemberProfile();
//            System.out.println("profileImagePath: " + profileImagePath);
//
//            if (!StringUtils.isEmpty(profileImagePath)) {
//                Path imagePath = Paths.get(profileImagePath);
//                System.out.println("imagePath: " + imagePath);
//
//                try {
//                    if (Files.exists(imagePath)) {
//                        return Files.readAllBytes(imagePath);
//                    } else {
//                        System.out.println("파일이 존재하지 않습니다.");
//                    }
//                } catch (IOException e) {
//                    System.err.println("파일 읽기 오류: " + e.getMessage());
//                }
//            } else {
//                System.out.println("프로필 이미지 경로가 비어 있습니다.");
//            }
//        } else {
//            System.out.println("해당 멤버가 존재하지 않습니다.");
//        }
//
//        return new byte[0]; // 이미지가 없거나 읽기 실패 시 빈 바이트 배열 반환
//    }

}
