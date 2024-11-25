package woongjin.gatherMind.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class QuestionCreateWithFileDTO {
    private Long questionId;
    private String option;
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    private MultipartFile file;

    private List<AnswerDTO> answers;
}