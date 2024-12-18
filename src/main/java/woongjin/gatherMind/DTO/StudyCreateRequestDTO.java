package woongjin.gatherMind.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import woongjin.gatherMind.enums.StudyCategory;
import woongjin.gatherMind.enums.StudyStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudyCreateRequestDTO {
    private Long studyId;
    private String title;
    private String description;
    private StudyStatus status;
    private StudyCategory category;
    private String memberId;
}
