package woongjin.gatherMind.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import woongjin.gatherMind.enums.Role;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminPutMemberDTO {

    @NotBlank
    private String memberId;
    @Email(message = "유효한 이메일 형식이 아닙니다.")
    private String email;
    private String nickname;
    private Role role;
}
