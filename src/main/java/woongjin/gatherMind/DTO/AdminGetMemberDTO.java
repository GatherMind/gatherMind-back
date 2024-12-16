package woongjin.gatherMind.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import woongjin.gatherMind.enums.CustomAuthProvider;
import woongjin.gatherMind.enums.Role;
import woongjin.gatherMind.enums.StudyRole;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminGetMemberDTO {
    private String memberId;
    private LocalDateTime createdAt;
    private String email;
    private String nickname;
    private CustomAuthProvider oauthProvider;
    private String profileImage;
    private Role role;
    private String title;
}

