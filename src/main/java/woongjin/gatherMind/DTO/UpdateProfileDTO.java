package woongjin.gatherMind.DTO;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateProfileDTO {

    @Size(min = 2, max = 20, message = "Nickname must be between 2 and 20 characters")
    private String nickname;

    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    public boolean isEmpty() {
        return (nickname == null || nickname.isEmpty()) &&
                (password == null || password.isEmpty());
    }
}
