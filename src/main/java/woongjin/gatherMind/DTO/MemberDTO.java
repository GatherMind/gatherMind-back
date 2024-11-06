package woongjin.gatherMind.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MemberDTO {
    private String memberId;
    private String nickname;
    private String email;
    private String password; // 회원가입 시에만 사용
    private String profileImage;
    private LocalDateTime createdAt;
}
