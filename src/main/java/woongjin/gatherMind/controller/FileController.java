package woongjin.gatherMind.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import woongjin.gatherMind.DTO.FileUploadResponseDTO;
import woongjin.gatherMind.auth.MemberDetails;
import woongjin.gatherMind.config.JwtTokenProvider;
import woongjin.gatherMind.service.FileService;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/files")
@AllArgsConstructor
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);
    private final FileService fileService;
    private final JwtTokenProvider jwtTokenProvider;

    @Operation(
            summary = "파일 업로드",
            description = "에디터를 통해 첨부된 이미지 파일을 업로드합니다."
    )
    @PostMapping("/upload")
    public ResponseEntity<List<String>> uploadFiles(@AuthenticationPrincipal MemberDetails memberDetails,
                                                    @RequestParam("files[]") List<MultipartFile> files) {
        String memberId = memberDetails.getUsername();
        List<String> fileUrls = new ArrayList<>();
        Boolean isContentEmbedded = true;
        try {
            for (MultipartFile file : files) {
                FileUploadResponseDTO responseDTO = fileService.handleFileUpload(file, memberId, isContentEmbedded);
                fileUrls.add(responseDTO.getFileUrl());
            }
            return ResponseEntity.ok(fileUrls);
        } catch (Exception e) {
            logger.error("파일 업로드 실패: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}