package woongjin.gatherMind.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import woongjin.gatherMind.config.JwtTokenProvider;
import woongjin.gatherMind.service.FileService;
//import woongjin.gatherMind.service.ProfileImageService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/files")
@AllArgsConstructor
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    private final FileService fileService;

//    @Autowired
//    private ProfileImageService profileImageService;

//    @Autowired
//    private JwtTokenProvider jwtTokenProvider;

//    @Operation(
//            summary = "파일 업로드"
//    )
//    @PostMapping("/upload")
//    public ResponseEntity<FileUploadResponseDTO> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("userId") String userId) {
//
//        FileUploadResponseDTO fileUploadResponseDTO = fileService.handleFileUpload(file, userId);
//        return ResponseEntity.status(HttpStatus.CREATED).body(fileUploadResponseDTO);
//    }k()

    // 프로필 이미지 업데이트 엔드포인트
//    @PostMapping("/update-profile-image")
//    public ResponseEntity<String> updateProfileImage(
//            @RequestParam("file") MultipartFile file,
//            HttpServletRequest request) {
//        try {
//            String token = jwtTokenProvider.resolveToken(request);
//            profileImageService.updateProfileImage(file, token);
//            String authorizationHeader = request.getHeader("Authorization");
//            System.out.println("Authorization Header: " + authorizationHeader);
//            return ResponseEntity.ok("프로필 이미지가 성공적으로 업데이트되었습니다.");
//        } catch (Exception e) {
//            String authorizationHeader = request.getHeader("Authorization");
//            System.out.println("Authorization Header: " + authorizationHeader);
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("프로필 이미지 업데이트에 실패했습니다." + e.getMessage());
//        }
//    }

    // 프로필 이미지 조회 엔드포인트
//    @GetMapping("/profile-image")
//    public ResponseEntity<Map<String, String>> getProfileImage(HttpServletRequest request) {
//        try {
//            String token = request.getHeader("Authorization").substring(7); // Bearer 토큰 제거
//            String profileImageUrl = String.valueOf(profileImageService.getProfileImage(token));
//
//            Map<String, String> response = new HashMap<>();
//            response.put("profileImageURL", profileImageUrl);
//
//            return ResponseEntity.ok(response);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//        }
//    }

}
