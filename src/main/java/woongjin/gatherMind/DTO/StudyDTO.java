package woongjin.gatherMind.DTO;

import lombok.*;

@Data
@AllArgsConstructor
@Builder
public class StudyDTO {

    private Long studyId;
    private String title;
    private String description;
    private String status;
}
