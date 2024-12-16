package woongjin.gatherMind.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import woongjin.gatherMind.entity.Answer;
import woongjin.gatherMind.enums.ContentType;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminGetContentResponseDTO {

    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private ContentType type;
    private String memberId;



    public AdminGetContentResponseDTO(AdminGetQuestionDTO dto) {
        this.id = dto.getQuestionId();
        this.title = dto.getTitle();
        this.content = dto.getContent();
        this.createdAt = dto.getCreatedAt();
        this.type = ContentType.BOARD;
        this.memberId = dto.getMemberId();
    }

    public AdminGetContentResponseDTO(Answer Answer) {
        this.id = Answer.getAnswerId();
        this.title = Answer.getContent();
        this.content = "";
        this.createdAt = Answer.getCreatedAt();
        this.type = ContentType.ANSWER;
        this.memberId = Answer.getMemberId();
    }

}
