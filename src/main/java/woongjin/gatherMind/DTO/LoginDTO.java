package woongjin.gatherMind.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class LoginDTO {
    @NotBlank
    private String memberId;
    @NotBlank
    private String password;
}