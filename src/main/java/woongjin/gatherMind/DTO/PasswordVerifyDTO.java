package woongjin.gatherMind.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class PasswordVerifyDTO {
    private String memberId;
    @NotNull
    private String password;
}
