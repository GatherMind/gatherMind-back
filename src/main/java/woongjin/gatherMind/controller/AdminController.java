package woongjin.gatherMind.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import woongjin.gatherMind.DTO.*;
import woongjin.gatherMind.entity.Member;
import woongjin.gatherMind.service.AdminService;
import woongjin.gatherMind.service.StudyService;


import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final StudyService studyService;

    @GetMapping("/member-count")
    public ResponseEntity<Long> countMember(HttpServletRequest request) {
        return ResponseEntity.ok(adminService.countMemberNotDeleted());
    }

    @GetMapping("/member-count-7day")
    public ResponseEntity<Long> countMember7Day(HttpServletRequest request) {
        return ResponseEntity.ok(adminService.countMember7DayBefore());
    }

    @GetMapping("/content-count")
    public ResponseEntity<Long> countContent(HttpServletRequest request) {
        return ResponseEntity.ok(adminService.countContent());
    }

    @GetMapping("/members")
    public ResponseEntity<List<AdminGetMemberDTO>> getMembers(HttpServletRequest request) {
        return ResponseEntity.ok(adminService.getMembers());
    }

    @PutMapping("/member")
    public ResponseEntity<Member> modifyMember(HttpServletRequest request,@RequestBody @Valid AdminPutMemberDTO adminPutMemberDTO) {
        adminService.updateMemberInfo(adminPutMemberDTO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/member/{memberId}")
    public ResponseEntity<Void> deleteMember(HttpServletRequest request, @PathVariable String memberId) {
        adminService.deleteMember(memberId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/contents")
    public ResponseEntity<List<AdminGetContentResponseDTO>> getContents(HttpServletRequest request) {
        return ResponseEntity.ok(adminService.getContents());
    }

    @DeleteMapping("/BOARD/{boardId}")
    public ResponseEntity<Void> deleteBoard(HttpServletRequest request,  @PathVariable Long boardId) {
        adminService.deleteBoard(boardId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/ANSWER/{answerId}")
    public ResponseEntity<Void> deleteAnswer(HttpServletRequest request,  @PathVariable Long answerId) {
        adminService.deleteAnswer(answerId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/study")
    public ResponseEntity<Member> modifyStudy(HttpServletRequest request,@RequestBody @Valid AdminPutStudyDTO adminPutMemberDTO) {
        adminService.updateStudyInfo(adminPutMemberDTO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/study/{studyId}")
    public ResponseEntity<Void> deleteStudy(HttpServletRequest request, @PathVariable Long studyId) {
        adminService.deleteStudy(studyId);
        return ResponseEntity.noContent().build();
    }
}
