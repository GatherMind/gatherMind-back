package woongjin.gatherMind.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woongjin.gatherMind.entity.*;
import woongjin.gatherMind.exception.notFound.*;
import woongjin.gatherMind.repository.*;

@Service
@RequiredArgsConstructor
public class CommonLookupService {

    private final MemberRepository memberRepository;
    private final StudyRepository studyRepository;
    private final StudyMemberRepository studyMemberRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;


    /**
     * 회원 ID로 회원을 조회합니다.
     *
     * @param memberId 조회할 회원 ID
     * @return 조회된 회원 객체
     * @throws MemberNotFoundException 회원 ID가 존재하지 않을 경우
     */
    public Member findByMemberId(String memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> new MemberNotFoundException(memberId));
    }

    /**
     * 회원 ID로 회원체크
     *
     * @param memberId 조회할 회원 ID
     * @throws MemberNotFoundException 회원 ID가 존재하지 않을 경우
     */
    public void checkMemberExists(String memberId) {
        if (!memberRepository.existsById(memberId)) {
            throw new MemberNotFoundException(memberId);
        }
    }

    /**
     * 스터디 ID로 스터디 조회
     *
     * @param studyId 스터디 ID
     * @return 스터디
     * @throws StudyNotFoundException 스터디 ID가 존재하지 않을 경우
     */
    @Transactional(readOnly = true)
    public Study findStudyByStudyId(Long studyId) {
        return studyRepository.findById(studyId)
                .orElseThrow(() -> new StudyNotFoundException(studyId));
    }

    /**
     * 질문 ID로 질문 조회
     *
     * @param questionId 질문 ID
     * @return 질문
     * @throws QuestionNotFoundException 질문 ID가 존재하지 않을 경우
     */
    @Transactional(readOnly = true)
    public Question findQuestionByQuestionId(Long questionId) {
        return questionRepository.findById(questionId)
                .orElseThrow(() -> new QuestionNotFoundException(questionId));
    }

    /**
     * 답변 ID로 질문 조회
     *
     * @param answerId 답변 ID
     * @return 답변
     * @throws AnswerNotFoundException 질문 ID가 존재하지 않을 경우
     */
    @Transactional(readOnly = true)
    public Answer findAnswerByAnswerId(Long answerId) {
        return answerRepository.findById(answerId)
                .orElseThrow(() -> new AnswerNotFoundException(answerId));
    }

    /**
     * 회원 ID와 스터디 ID로 스터디 멤버 조회
     *
     * @param memberId 회원 ID
     * @param studyId  스터디 ID
     * @return 스터디 멤버
     * @throws StudyMemberNotFoundException 스터디 멤버가 존재하지 않을 경우
     */
    public StudyMember findByMemberIdAndStudyId(String memberId, Long studyId) {
        return studyMemberRepository.findByMember_MemberIdAndStudy_StudyId(memberId, studyId)
                .orElseThrow(() -> new StudyMemberNotFoundException(memberId, studyId));
    }


}
