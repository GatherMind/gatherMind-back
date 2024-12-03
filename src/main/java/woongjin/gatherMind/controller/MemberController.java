package woongjin.gatherMind.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import woongjin.gatherMind.DTO.*;
import woongjin.gatherMind.config.JwtTokenProvider;
import woongjin.gatherMind.entity.Member;
import woongjin.gatherMind.service.*;

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

    // 멤버 역활 같이 조회
    @Operation(
            summary = "내 정보 역활 같이 조회"
    )
    @GetMapping("/role")
    public ResponseEntity<MemberAndStatusRoleDTO> getMemberAndRoleByMemberId(
            @RequestParam Long studyId,
            HttpServletRequest request
    ) {

        String memberId = jwtTokenProvider.extractMemberIdFromRequest(request);
        return ResponseEntity.ok(memberService.getMemberAndRoleByMemberId(memberId, studyId));
    }

    // 회원가입
    @Operation(
            summary = "회원가입"
    )
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody MemberDTO memberDTO) {
        try {
            System.out.println("수신된 데이터: " + memberDTO);
            memberService.signup(memberDTO); // 회원가입 처리
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("회원가입이 완료되었습니다."); // 성공 메시지 반환
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("회원가입에 실패했습니다: " + e.getMessage());
        }
    }

    // 로그인
    @Operation(
            summary = "로그인"
    )
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {

        boolean isAuthenticated = memberService.authenticate(loginDTO);

        if (isAuthenticated) {
            String token = jwtTokenProvider.createToken(loginDTO.getMemberId());
            return ResponseEntity.ok(Collections.singletonMap("token", token));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패");
        }
    }

    // 내 정보 조회
    @Operation(
            summary = "내 정보 조회"
    )
    @GetMapping("/me")
    public ResponseEntity<MemberDTO> getMemberInfo(HttpServletRequest request) {

        String memberId = jwtTokenProvider.extractMemberIdFromRequest(request);
        return memberService.getMemberById(memberId)
                .map(member -> ResponseEntity.ok(new MemberDTO(member)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));

    }


    @Operation(
            summary = "내 정보 수정(닉네임, 비밀번호)"
    )
    @PutMapping("/update")
    public ResponseEntity<String> updateMemberInfo(HttpServletRequest request, @RequestBody Map<String, String> requestBody) {

        String memberId = jwtTokenProvider.extractMemberIdFromRequest(request);
        String newNickname = requestBody.get("nickname");
        String newPassword = requestBody.get("password");

        return ResponseEntity.ok(memberService.updateMemberInfo(memberId, newNickname, newPassword));
    }

    @Operation(
            summary = "최근 작성한 게시글(질문) 조회"
    )
    @GetMapping("/recent-questions")
    public ResponseEntity<List<QuestionDTO>> getRecentQuestions(HttpServletRequest request) {
        try {
            String memberId = jwtTokenProvider.extractMemberIdFromRequest(request);

            List<QuestionDTO> recentQuestions = questionService.findRecentQuestionsByMemberId(memberId);
            return ResponseEntity.ok(recentQuestions);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    //    @GetMapping("/me/answers")
    @Operation(
            summary = "최근 작성한 댓글 조회"
    )
    @GetMapping("/recent-answers")
    public ResponseEntity<List<AnswerDTO>> getRecentAnswers(HttpServletRequest request) {
        try {
            String memberId = jwtTokenProvider.extractMemberIdFromRequest(request);

            List<AnswerDTO> recentAnswers = memberService.findRecentAnswersByMemberId(memberId);
            return ResponseEntity.ok(recentAnswers);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    // 가입한 스터디 수
    @Operation(
            summary = "가입한 스터디 수 조회"
    )
    @GetMapping("/study-count")
    public ResponseEntity<Long> getStudyCount(HttpServletRequest request) {
        String memberId = jwtTokenProvider.extractMemberIdFromRequest(request);
        long count = studyMemberService.countStudiesByMemberId(memberId);
        return ResponseEntity.ok(count);
    }

    // 작성한 질문 수
    @Operation(
            summary = "작성한 질문 수 조회"
    )
    @GetMapping("/question-count")
    public ResponseEntity<Long> getQuestionCount(HttpServletRequest request) {
        String memberId = jwtTokenProvider.extractMemberIdFromRequest(request);
        long count = questionService.countQuestionsByMemberId(memberId);
        return ResponseEntity.ok(count);
    }

    // 작성한 답변 수
    @Operation(
            summary = "작성한 답변 수 조회"
    )
    @GetMapping("/answer-count")
    public ResponseEntity<Long> getAnswerCount(HttpServletRequest request) {
        String memberId = jwtTokenProvider.extractMemberIdFromRequest(request);
        long count = answerService.countAnswersByMemberId(memberId);
        return ResponseEntity.ok(count);
    }


    @Operation(
            summary = "회원 탈퇴"
    )
    @DeleteMapping("/delete-account")
    public ResponseEntity<String> deleteAccount(HttpServletRequest request) {
        try {
            String memberId = jwtTokenProvider.extractMemberIdFromRequest(request);
            memberService.deleteAccount(memberId);
            return ResponseEntity.ok("회원 탈퇴가 완료되었습니다.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 토큰입니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원 탈퇴 중 오류가 발생했습니다.");
        }
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<MemberDTO> getMember(@PathVariable String memberId) {
        Member member = memberService.findByMemberId(memberId);
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setMemberId(member.getMemberId());
        memberDTO.setEmail(member.getEmail());
        memberDTO.setNickname(member.getNickname());
        memberDTO.setProfileImage(member.getProfileImage()); // "/image/default-profile.jpg"
        return ResponseEntity.ok(memberDTO);
    }

    @Operation(
            summary = "내 스터디 목록 조회"
    )
    @GetMapping("/joined-groups")
    public ResponseEntity<List<StudyDTO>> getJoinedGroups(HttpServletRequest request) {


        String memberId = jwtTokenProvider.extractMemberIdFromRequest(request);

        List<StudyDTO> joinedGroups = studyMemberService.findStudiesByMemberId(memberId);
        return ResponseEntity.ok(joinedGroups);

    }


    //회원이 가입한 그룹(스터디) 목록 가져오기
    @Operation(
            summary = "내 스터디 목록 조회2"
    )
    @GetMapping("/my-studies")
    public ResponseEntity<List<StudyDTO>> getMyGroups() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String memberId = authentication.getName(); // 인증된 사용자 ID (memberId)

            List<StudyDTO> joinedGroups = studyMemberService.getStudiesByMemberIdandStatus(memberId);

            return ResponseEntity.ok(joinedGroups);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }


    // 이메일 중복 확인
    @Operation(
            summary = "이메일 중복 확인"
    )
    @PostMapping("/check-email")
    public ResponseEntity<Map<String, Boolean>> checkEmail(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        boolean isUnique = memberService.isEmailUnique(email);
        return ResponseEntity.ok(Collections.singletonMap("isUnique", isUnique));
    }

    // 닉네임 중복 확인
    @Operation(
            summary = "닉네임 중복 확인"
    )
    @PostMapping("/check-nickname")
    public ResponseEntity<Map<String, Boolean>> checkNickname(@RequestBody Map<String, String> request) {
        String nickname = request.get("nickname");
        boolean isUnique = memberService.isNicknameUnique(nickname);
        return ResponseEntity.ok(Collections.singletonMap("isUnique", isUnique));
    }

    // 아이디 중복 확인
    @Operation(
            summary = "아이디 중복 확인"
    )
    @PostMapping("/check-memberId")
    public ResponseEntity<Map<String, Boolean>> checkMemberId(@RequestBody Map<String, String> request) {
        String memberId = request.get("memberId");
        boolean isUnique = memberService.isMemberIdUnique(memberId);
        return ResponseEntity.ok(Collections.singletonMap("isUnique", isUnique));
    }
}
