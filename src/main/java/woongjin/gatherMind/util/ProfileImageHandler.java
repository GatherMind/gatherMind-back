package woongjin.gatherMind.util;

import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class ProfileImageHandler {

    public static String handleProfileImage(MultipartFile file, String memberId) throws IOException {
        // 절대 경로 가져오기
        String absolutePath = new File("").getAbsolutePath() + File.separator;

        // 파일 저장 경로 설정
        String path = "src" + File.separator + "main" + File.separator + "resources"
                + File.separator + "static" + File.separator + "profile-images";

        File userImgDir = new File(path);

        // 디렉토리가 존재하지 않으면 생성
        if (!userImgDir.exists()) {
            userImgDir.mkdirs();
        }

        // 파일이 비어있으면 null 반환
        if (file.isEmpty()) {
            return null;
        }

        // 파일 타입 및 확장자 검증
        String contentType = file.getContentType();
        String originalFileExtension;

        if (ObjectUtils.isEmpty(contentType)) {
            return null;
        } else if (contentType.contains("image/jpeg")) {
            originalFileExtension = ".jpg";
        } else if (contentType.contains("image/png")) {
            originalFileExtension = ".png";
        } else {
            return null; // 지원하지 않는 파일 형식
        }

        // 파일 이름 생성
        String originalFileName = file.getOriginalFilename();
        String fileName = memberId + "_" + System.nanoTime() + originalFileExtension;

        // 파일 저장
        File userImg = new File(userImgDir, fileName);
        file.transferTo(userImg);

        // 저장된 파일 경로 반환
        return Paths.get(path, fileName).toString();
    }
}

