package woongjin.gatherMind.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import woongjin.gatherMind.entity.Member;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDTO {

    @NotBlank(message = "memberId is required")
    private String memberId;

    @NotBlank(message = "nickname is required")
    @Size(min = 2, max = 20)
    private String nickname;


    @Email
    @NotBlank(message = "email is required")
    private String email;

    private String profileImage;

    private LocalDateTime createdAt;

    @NotBlank(message = "password is required")
    @Size(min = 8, max = 255, message = "비밀번호는 8자 이상 255자 이하로 입력해야 합니다.")
    private String password;

}
