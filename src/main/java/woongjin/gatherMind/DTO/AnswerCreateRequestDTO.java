package woongjin.gatherMind.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class AnswerCreateRequestDTO {
    private Long answerId;
    private String memberId;
    @NotNull(message = "questionId is required")
    private Long questionId;
    @NotBlank(message = "content is required")
    private String content;
}