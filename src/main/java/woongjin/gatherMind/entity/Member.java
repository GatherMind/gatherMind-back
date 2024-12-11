package woongjin.gatherMind.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Member {

    @Id
    private String memberId;

    private String oauthProvider; // "google", "facebook" 등
    private String oauthId;       // OAuth2 제공자가 부여한 고유 ID

    @Column(unique = true)
    private String nickname;
    @Column(nullable = false, unique = true)
    private String email;
    private String password;

//    private boolean isEmailVerified = false; // 초기값 false

    @Column(nullable = false)
    private String profileImage = "/api/files/default-profile";

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    // Member - StudyMember (1:N)
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<StudyMember> studyMembers;

    public Member (String oauthProvider, String email, String name){
        this.oauthProvider  = oauthProvider;
        this.email = email;
        this.nickname = name;
    }
}
