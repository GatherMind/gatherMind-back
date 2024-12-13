package woongjin.gatherMind.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import woongjin.gatherMind.enums.MemberStatus;
import woongjin.gatherMind.enums.StudyRole;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class MemberAndStatusRoleDTO {
    private String memberId;
    private String nickname;
    private StudyRole role;
    private MemberStatus status;
    private Long studyMemberId;
}
