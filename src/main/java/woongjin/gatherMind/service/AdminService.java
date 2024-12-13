package woongjin.gatherMind.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woongjin.gatherMind.DTO.*;
import woongjin.gatherMind.entity.Answer;
import woongjin.gatherMind.entity.Member;
import woongjin.gatherMind.entity.Question;
import woongjin.gatherMind.entity.Study;
import woongjin.gatherMind.enums.Role;

import woongjin.gatherMind.enums.StudyCategory;
import woongjin.gatherMind.enums.StudyStatus;
import woongjin.gatherMind.repository.AnswerRepository;
import woongjin.gatherMind.repository.MemberRepository;
import woongjin.gatherMind.repository.QuestionRepository;
import woongjin.gatherMind.repository.StudyRepository;
import woongjin.gatherMind.validation.NicknameValidator;
import woongjin.gatherMind.validation.UniqueEmailValidator;
import woongjin.gatherMind.validation.UniqueNicknameValidator;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final MemberRepository memberRepository;
    private final StudyRepository studyRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final CommonLookupService commonLookupService;

    private final NicknameValidator nicknameValidator;
    private final UniqueNicknameValidator uniqueNicknameValidator;
    private final UniqueEmailValidator uniqueEmailValidator;

    public long countMemberNotDeleted() {
        return memberRepository.countByIsDelete(false);
    }

    public long countMember7DayBefore() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime sevenDayBefore = now.minusDays(7);
        return memberRepository.countByIsDeleteAndCreatedAtBetween(false, sevenDayBefore, now);
    }

    public long countContent() {
        return  questionRepository.count() + answerRepository.count();
    }

    public List<AdminGetMemberDTO> getMembers() {

        Map<String, AdminGetMemberDTO> merged = new HashMap<>();

        List<AdminGetMemberDTO> allMemberAndStudies = memberRepository.getAllMemberAndStudies();

        for (AdminGetMemberDTO dto : allMemberAndStudies) {
            AdminGetMemberDTO existing = merged.get(dto.getMemberId());

            if (existing != null) {
                // 기존 DTO에 새로운 title 추가
                existing.setTitle(existing.getTitle() + ", " + dto.getTitle());
            } else {
                // 새로운 DTO 저장
                dto.setTitle(dto.getTitle()); // 초기 제목 설정
                merged.put(dto.getMemberId(), dto);
            }
        }

        return new ArrayList<>(merged.values());
    }


    @Transactional
    public void updateMemberInfo(AdminPutMemberDTO dto) {
        Member member = commonLookupService.findByMemberId(dto.getMemberId());

        String newNickname = dto.getNickname();
        String email = dto.getEmail();
        Role role = dto.getRole();

        if (newNickname != null && !newNickname.isEmpty() && !newNickname.equals(member.getNickname())) {
            nicknameValidator.validate(newNickname);
            uniqueNicknameValidator.validate(newNickname);
            member.setNickname(newNickname);
        }

        if (email != null && !email.isEmpty() && !email.equals(member.getEmail())) {
            uniqueEmailValidator.isValid(email);
            member.setEmail(email);
        }

        if (role != null  && role != member.getRole()) {
            Role.fromCode(role.getCode());
            member.setRole(role);
        }

        memberRepository.save(member);
    }

    @Transactional
    public void deleteMember(String memberId) {
        Member member = commonLookupService.findByMemberId(memberId);
        member.setDelete(true);
        memberRepository.save(member);
    }

    public List<AdminGetContentResponseDTO> getContents() {

        List<AdminGetQuestionDTO> allQuestion = questionRepository.getAllQuestion();
        List<Answer> allAnswer = answerRepository.findAllByOrderByCreatedAtDesc();

        List<AdminGetContentResponseDTO> list = new ArrayList<>(allQuestion.stream().map(AdminGetContentResponseDTO::new).toList());
        allAnswer.stream()
                .map(AdminGetContentResponseDTO::new)
                .forEach(list::add); // 리스트에 추가

        Comparator.comparing(AdminGetContentResponseDTO::getCreatedAt).reversed();

        return list;
    }

    @Transactional
    public void deleteBoard(Long boardId) {
        questionRepository.delete(commonLookupService.findQuestionByQuestionId(boardId));
    }

    @Transactional
    public void deleteAnswer(Long answerId) {
        answerRepository.delete(commonLookupService.findAnswerByAnswerId(answerId));
    }

    @Transactional
    public void updateStudyInfo(AdminPutStudyDTO dto) {
        Study study = commonLookupService.findStudyByStudyId(dto.getStudyId());

        String newTitle = dto.getTitle();
        String newDescription = dto.getDescription();
        StudyStatus newStatus = dto.getStatus();
        StudyCategory newCategory = dto.getCategory();


        if (newTitle != null && !newTitle.isEmpty() && !newTitle.equals(study.getTitle())) {
            study.setTitle(newTitle);
        }

        if (newDescription != null && !newDescription.isEmpty() && !newDescription.equals(study.getDescription())) {
            study.setDescription(newDescription);
        }

        if (newStatus != null  && newStatus != study.getStatus()) {
            StudyStatus.fromCode(newStatus.getCode());
            study.setStatus(newStatus);
        }

        if (newCategory != null  && newCategory != study.getCategory()) {
            StudyCategory.fromCode(newCategory.getCode());
            study.setCategory(newCategory);
        }

        studyRepository.save(study);
    }

    @Transactional
    public void deleteStudy(Long studyId) {
        studyRepository.delete(commonLookupService.findStudyByStudyId(studyId));
    }
}
