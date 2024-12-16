package woongjin.gatherMind.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import woongjin.gatherMind.converters.RoleConverter;

import woongjin.gatherMind.enums.Role;
import woongjin.gatherMind.enums.CustomAuthProvider;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Member {

    @Id
    private String memberId;

    @Column(unique = true)
    private String nickname;
    @Column(nullable = false, unique = true)
    private String email;
    private String password;

    @Convert(converter = RoleConverter.class)
    private Role role = Role.USER;

    @Column(nullable = false)
    private String profileImage = "/api/files/default-profile";

    private Boolean isDelete = false;

    private String oauthId = null;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CustomAuthProvider oauthProvider;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    // Member - StudyMember (1:N)
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<StudyMember> studyMembers;

    public Member (String randomMemberId, CustomAuthProvider oauthProvider, String oauthId, String email){
        this.memberId = randomMemberId;
        this.oauthProvider  = oauthProvider;
        this.oauthId = oauthId;
        this.email = email;
    }
}
