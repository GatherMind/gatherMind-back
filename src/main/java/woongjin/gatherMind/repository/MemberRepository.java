package woongjin.gatherMind.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import woongjin.gatherMind.DTO.AdminGetMemberDTO;
import woongjin.gatherMind.DTO.MemberAndStatusRoleDTO;
import woongjin.gatherMind.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;


import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {

    @Query("SELECT new woongjin.gatherMind.DTO.MemberAndStatusRoleDTO(m.memberId, m.nickname, sm.role, sm.status, sm.studyMemberId ) " +
            "FROM Member m " +
            "JOIN m.studyMembers sm " + // 엔티티 관계 필드 사용
            "WHERE m.memberId = :memberId AND sm.study.studyId= :studyId")
    Optional<MemberAndStatusRoleDTO> findMemberAndRoleByMemberId(@Param("memberId") String memberId, @Param("studyId") Long studyId);

    boolean existsByMemberId(String memberId);

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);

    Optional<Member> findById(String memberId);

    Optional<Member> findByNickname(String nickname);

    Optional<String> findProfileImageUrlByMemberId(@Param("memberId") String memberId);

    Optional<Member> findByEmail(String email);


    long countByIsDelete(boolean isDelete);

    long countByIsDeleteAndCreatedAtBetween(boolean isDelete, LocalDateTime sevenDaysBefore, LocalDateTime today);

    @Query("SELECT new woongjin.gatherMind.DTO.AdminGetMemberDTO(m.memberId , m.createdAt, m.email, m.nickname,  " +
            " m.oauthProvider, m.profileImage, m.role, s.title ) " +
            "FROM Member m " +
            "JOIN m.studyMembers sm " +
            "JOIN sm.study s " +
            "WHERE m.isDelete = false ")
    List<AdminGetMemberDTO> getAllMemberAndStudies();
}
