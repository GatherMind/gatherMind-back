package woongjin.gatherMind.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateAnswerRequestDTO {
    @NotBlank(message = "content is required")
    String content;
}
