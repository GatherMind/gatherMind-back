package woongjin.gatherMind.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import woongjin.gatherMind.config.JwtTokenProvider;
import woongjin.gatherMind.entity.Member;
import woongjin.gatherMind.repository.MemberRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
public class ProfileImageService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public ProfileImageService(MemberRepository memberRepository, JwtTokenProvider jwtTokenProvider) {
        this.memberRepository = memberRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    // JWT에서 memberId 추출
    public String extractMemberIdFromToken(String token) {
        return jwtTokenProvider.getMemberIdFromToken(token);
    }

    // 프로필 이미지 저장
    public void saveProfileImage(MultipartFile file, String memberId) throws IOException {
        // Member 정보 가져오기
        Optional<Member> memberOptional = memberRepository.findById(memberId);
        if (memberOptional.isEmpty()) {
            throw new IllegalArgumentException("사용자를 찾을 수 없습니다.");
        }

        Member member = memberOptional.get();

        // 파일 저장 경로 생성
        String directory = "profile-images";
        Path directoryPath = Paths.get(directory);
        if (!Files.exists(directoryPath)) {
            Files.createDirectories(directoryPath);
        }

        String fileName = memberId + "_" + file.getOriginalFilename();
        Path filePath = directoryPath.resolve(fileName);
        Files.write(filePath, file.getBytes());

        // Member에 프로필 이미지 경로 저장
        member.setProfileImage(filePath.toString());
        memberRepository.save(member);
    }

    // 프로필 이미지 삭제
    public void deleteProfileImage(String memberId) throws IOException {
        // Member 정보 가져오기
        Optional<Member> memberOptional = memberRepository.findById(memberId);
        if (memberOptional.isEmpty()) {
            throw new IllegalArgumentException("사용자를 찾을 수 없습니다.");
        }

        Member member = memberOptional.get();
        String profileImagePath = member.getProfileImage();

        if (StringUtils.hasText(profileImagePath)) {
            Path filePath = Paths.get(profileImagePath);
            if (Files.exists(filePath)) {
                Files.delete(filePath); // 파일 삭제
            }
        }

        // Member의 프로필 이미지 경로 초기화
        member.setProfileImage(null);
        memberRepository.save(member);
    }

    // 프로필 이미지 가져오기
    public byte[] getProfileImage(String memberId) throws IOException {
        Optional<Member> memberOptional = memberRepository.findById(memberId);
        if (memberOptional.isEmpty()) {
            throw new IllegalArgumentException("사용자를 찾을 수 없습니다.");
        }

        Member member = memberOptional.get();
        String profileImagePath = member.getProfileImage();

        if (StringUtils.hasText(profileImagePath)) {
            Path imagePath = Paths.get(profileImagePath);
            if (Files.exists(imagePath)) {
                return Files.readAllBytes(imagePath);
            }
        }
        return new byte[0];
    }
}
