package woongjin.gatherMind.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class NickNameCheckDTO {

    @NotBlank(message = "nickname is required")
    private String nickname;
}
