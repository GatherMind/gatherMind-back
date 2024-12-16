package woongjin.gatherMind.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import woongjin.gatherMind.DTO.StudyApplyDTO;
import woongjin.gatherMind.DTO.StudyMemberConfirmDTO;
import woongjin.gatherMind.DTO.StudyMemberDTO;
import woongjin.gatherMind.auth.MemberDetails;
import woongjin.gatherMind.config.JwtTokenProvider;
import woongjin.gatherMind.entity.StudyMember;
import woongjin.gatherMind.service.StudyMemberService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/study-members")
@RequiredArgsConstructor
@Tag(name = "Study Member API")
public class StudyMemberController {

    private final StudyMemberService studyMemberService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/{studyMemberId}")
    public StudyMemberDTO getStudyMemberById(@PathVariable Long studyMemberId) {
        StudyMember studyMember = studyMemberService.getStudyMemberById(studyMemberId).orElse(null);
        return studyMember != null ? new StudyMemberDTO(studyMember) : null;
    }


    @Operation(
            summary = "스터디 가입 신청"
    )
    @PostMapping
    public ResponseEntity<StudyMemberDTO> applyStudy(@AuthenticationPrincipal MemberDetails memberDetails, @RequestBody StudyApplyDTO dto) {
        String memberId = memberDetails.getUsername();
        return ResponseEntity.ok(studyMemberService.applyStudy(memberId, dto.getStudyId()));
    }

    @Operation(
            summary = "스터디 가입 신청 승인"
    )
    @PutMapping
    public ResponseEntity<StudyMemberDTO> confirmStudyMember(@AuthenticationPrincipal MemberDetails memberDetails,
                                                             @RequestBody StudyMemberConfirmDTO dto) {
        String memberId = memberDetails.getUsername();
        return ResponseEntity.ok(studyMemberService.confirmStudyMember(memberId, dto));
    }

    @Operation(
            summary = "스터디 강퇴"
    )
    @DeleteMapping
    public ResponseEntity<StudyMemberDTO> resignStudyMember(@AuthenticationPrincipal MemberDetails memberDetails,
                                                            @RequestBody StudyMemberConfirmDTO dto) {
        String memberId = memberDetails.getUsername();
        studyMemberService.resignStudyMember(memberId, dto);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "스터디 탈퇴"
    )
    @DeleteMapping("/{studyId}")
    public ResponseEntity<StudyMemberDTO> withdrawStudyMember(@AuthenticationPrincipal MemberDetails memberDetails,
                                                              @PathVariable Long studyId) {
        String memberId = memberDetails.getUsername();
        studyMemberService.withdrawStudyMember(memberId, studyId);
        return ResponseEntity.noContent().build();
    }
}
