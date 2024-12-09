package woongjin.gatherMind.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileImageResponseDTO {
    private String profileImageUrl; // 프로필 이미지 URL
    private String message; // 응답 메시지
}
