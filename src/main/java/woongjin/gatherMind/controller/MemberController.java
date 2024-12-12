package woongjin.gatherMind.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import woongjin.gatherMind.DTO.*;

import woongjin.gatherMind.config.JwtTokenProvider;
import woongjin.gatherMind.entity.Member;
import woongjin.gatherMind.exception.invalid.InvalidNicknameException;
import woongjin.gatherMind.exception.invalid.InvalidPasswordException;
import woongjin.gatherMind.service.*;
import woongjin.gatherMind.validation.UniqueEmailValidator;
import woongjin.gatherMind.validation.UniqueMemberIdValidator;
import woongjin.gatherMind.validation.UniqueNicknameValidator;

import java.util.*;


@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
@Tag(name = "Member API")
public class MemberController {

    private final MemberService memberService;
    private final QuestionService questionService;
    private final StudyMemberService studyMemberService;
    private final AnswerService answerService;
    private final JwtTokenProvider jwtTokenProvider;
    private final UniqueNicknameValidator uniqueNicknameValidator;
    private final UniqueMemberIdValidator uniqueMemberIdValidator;
    private final UniqueEmailValidator uniqueEmailValidator;
    private final CommonLookupService commonLookupService;


    /**
     * 회원정보 및 역활 조회
     */
    @Operation(
            summary = "회원정보 및 역활 조회", description = "회원정보 및 역활 조회합니다."
    )
    @GetMapping("/role")
    public ResponseEntity<MemberAndStatusRoleDTO> getMemberAndRoleByMemberId(
            @RequestParam Long studyId,
            HttpServletRequest request
    ) {
        String memberId = jwtTokenProvider.extractMemberIdFromRequest(request);
        return ResponseEntity.ok(memberService.getMemberAndRoleByMemberId(memberId, studyId));
    }

    /**
     * 내 정보 조회
     */
    @Operation(summary = "내 정보 조회", description = "로그인된 회원의 정보를 조회합니다.")
    @GetMapping("/me")
    public ResponseEntity<MemberDTO> getMemberInfo(HttpServletRequest request) {
        String memberId = jwtTokenProvider.extractMemberIdFromRequest(request);
        return ResponseEntity.ok(new MemberDTO(commonLookupService.findByMemberId(memberId)));
    }

    /**
     * 회원 정보 수정 (닉네임, 비밀번호)
     */
    @Operation(summary = "회원 정보 수정", description = "로그인된 회원의 닉네임과 비밀번호를 수정합니다.")
    @PutMapping("/update")
    public ResponseEntity<String> updateMemberInfo(
            HttpServletRequest request,
//            @RequestBody Map<String, String> requestBody,
            @RequestBody UpdateProfileDTO dto)
    {

        if (dto.isEmpty()) {
            return ResponseEntity.badRequest().body("At least one field (nickname or password) must be provided");
        }

        // JWT에서 memberId 추출
        String memberId = jwtTokenProvider.extractMemberIdFromRequest(request);

        // 서비스 메서드 호출로 수정 진행
        String responseMessage = memberService.updateMemberInfo(memberId, dto.getNickname(), dto.getPassword());

        // 성공 메시지 반환
        return ResponseEntity.ok(responseMessage);
    }

    /**
     * 최근 작성한 게시글(질문) 목록 조회
     */
//    @GetMapping("/me/questions")
    @Operation(summary = "최근 작성한 질문 조회", description = "로그인된 회원이 작성한 최근 질문 목록을 조회합니다.")
    @GetMapping("/recent-questions")
    public ResponseEntity<List<QuestionDTO>> getRecentQuestions(HttpServletRequest request) {
        String memberId = jwtTokenProvider.extractMemberIdFromRequest(request);
        List<QuestionDTO> recentQuestions = questionService.findRecentQuestionsByMemberId(memberId);
        return ResponseEntity.ok(recentQuestions);
    }

    /**
     * 최근 작성한 답글 목록 조회
     */
    //    @GetMapping("/me/answers")
    @Operation(summary = "최근 작성한 답글 조회", description = "로그인된 회원이 작성한 최근 답글 목록을 조회합니다.")
    @GetMapping("/recent-answers")
    public ResponseEntity<List<AnswerDTO>> getRecentAnswers(HttpServletRequest request) {
        String memberId = jwtTokenProvider.extractMemberIdFromRequest(request);
        return ResponseEntity.ok(memberService.findRecentAnswersByMemberId(memberId));
    }


    /**
     * 가입한 스터디 수 조회
     */
    @Operation(
            summary = "가입한 스터디 수 조회", description = "가입한 스터디 수 조회합니다."
    )
    @GetMapping("/study-count")
    public ResponseEntity<Long> getStudyCount(HttpServletRequest request) {
        String memberId = jwtTokenProvider.extractMemberIdFromRequest(request);
        long count = studyMemberService.countStudiesByMemberId(memberId);
        return ResponseEntity.ok(count);
    }


    /**
     * 작성한 질문 수 조회
     */
    @Operation(
            summary = "작성한 질문 수 조회", description = "작성한 질문 수 조회합니다."
    )
    @GetMapping("/question-count")
    public ResponseEntity<Long> getQuestionCount(HttpServletRequest request) {
        String memberId = jwtTokenProvider.extractMemberIdFromRequest(request);
        long count = questionService.countQuestionsByMemberId(memberId);
        return ResponseEntity.ok(count);
    }

    /**
     * 작성한 답변 수 조회
     */
    @Operation(
            summary = "작성한 답변 수 조회", description = "작성한 답변 수 조회합니다."
    )
    @GetMapping("/answer-count")
    public ResponseEntity<Long> getAnswerCount(HttpServletRequest request) {
        String memberId = jwtTokenProvider.extractMemberIdFromRequest(request);
        long count = answerService.countAnswersByMemberId(memberId);
        return ResponseEntity.ok(count);
    }

    /**
     * 회원 탈퇴
     */
//    @DeleteMapping("/me")
    @Operation(summary = "회원 탈퇴", description = "로그인된 회원의 계정을 삭제합니다.")
    @DeleteMapping("/delete-account")
    public ResponseEntity<String> deleteAccount(HttpServletRequest request) {

        String memberId = jwtTokenProvider.extractMemberIdFromRequest(request);
        memberService.deleteAccount(memberId);
        return ResponseEntity.ok("회원 탈퇴가 완료되었습니다.");
    }

    /**
     * 회원이 가입한 그룹(스터디) 목록 가져오기
     */
//    @GetMapping("/me/groups")
    @Operation(
            summary = "회원 스터디 목록 조회", description = "회원이 가입한 스터디 목록 조회합니다."
    )
    @GetMapping("/joined-groups")
    public ResponseEntity<List<StudyDTO>> getJoinedGroups(HttpServletRequest request) {
        String memberId = jwtTokenProvider.extractMemberIdFromRequest(request);
        List<StudyDTO> joinedGroups = studyMemberService.findStudiesByMemberId(memberId);
        return ResponseEntity.ok(joinedGroups);
    }

    /**
     * 회원이 가입한 그룹(스터디) 목록 가져오기
     */
    @Operation(
            summary = "회원 스터디 목록 조회", description = "회원이 가입한 스터디 목록 조회합니다."
    )
    @GetMapping("/my-studies")
    public ResponseEntity<List<StudyDTO>> getMyGroups() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String memberId = authentication.getName(); // 인증된 사용자 ID (memberId)

            List<StudyDTO> joinedGroups = studyMemberService.findApprovedStudiesByMemberId(memberId);

            return ResponseEntity.ok(joinedGroups);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }


    /**
     * 이메일 중복 확인
     */
    @Operation(summary = "이메일 중복 확인", description = "이메일 중복 여부를 확인합니다.")
    @PostMapping("/check-email")
    public ResponseEntity<Map<String, Boolean>> checkEmail(@Valid @RequestBody EmailCheckDTO emailCheckDTO) {
        return ResponseEntity.ok(Collections.singletonMap("isUnique", uniqueEmailValidator.isValid(emailCheckDTO.getEmail())));
    }

    /**
     * 닉네임 중복 확인
     */
    @Operation(summary = "닉네임 중복 확인", description = "닉네임 중복 여부를 확인합니다.")
    @PostMapping("/check-nickname")
    public ResponseEntity<Map<String, Boolean>> checkNickname(@Valid @RequestBody NickNameCheckDTO nickNameCheckDTO) {
        return ResponseEntity.ok(Collections.singletonMap("isUnique", uniqueNicknameValidator.isValid(nickNameCheckDTO.getNickname())));
    }


    /**
     * 아이디 중복 확인
     */
    @Operation(summary = "아이디 중복 확인", description = "아이디 중복 여부를 확인합니다.")
    @PostMapping("/check-memberId")
    public ResponseEntity<Map<String, Boolean>> checkMemberId(@Valid @RequestBody MemberIdCheckDTO memberIdCheckDTO) {
        return ResponseEntity.ok(Collections.singletonMap("isUnique", uniqueMemberIdValidator.isValid(memberIdCheckDTO.getMemberId())));
    }
}
