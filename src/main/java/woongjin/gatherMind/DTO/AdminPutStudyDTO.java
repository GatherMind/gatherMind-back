package woongjin.gatherMind.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import woongjin.gatherMind.enums.Role;
import woongjin.gatherMind.enums.StudyCategory;
import woongjin.gatherMind.enums.StudyStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminPutStudyDTO {

    @NotNull
    private Long studyId;
    private String title;
    private String description;
    private StudyStatus status;
    private StudyCategory category;
}
