package woongjin.gatherMind.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminGetAnswerDTO {
    private Long questionId;
    private String content;
    private LocalDateTime createdAt;
    private String memberId;
}
