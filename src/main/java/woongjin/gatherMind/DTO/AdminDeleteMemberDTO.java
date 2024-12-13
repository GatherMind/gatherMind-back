package woongjin.gatherMind.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import woongjin.gatherMind.enums.Role;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminDeleteMemberDTO {
    @NotBlank
    private String memberId;
}

