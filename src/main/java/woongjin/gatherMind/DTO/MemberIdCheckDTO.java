package woongjin.gatherMind.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MemberIdCheckDTO {

    @NotBlank(message = "memberId is required")
    private String memberId;
}
