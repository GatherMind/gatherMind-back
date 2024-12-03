package woongjin.gatherMind.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class FileMetadata {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fileMetadataId;

    private String fileName; // 업로드된 원본 파일 이름

    @Column(name = "short_url_key", nullable = false)
    private String shortUrlKey; // 단축 URL 키
    private String fileKey; // S3에서 사용되는 고유 Key
    private Long fileSize; // 파일 크기
    private String uploadByUserId; // 업로더 ID

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime uploadDate;

    // FileMapping과 1:1 관계
    @OneToOne(mappedBy = "fileMetadata", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private EntityFileMapping entityFileMapping;

    public FileMetadata(String fileName, String fileType, long fileSize) {
        this.fileName = fileName;
        this.fileSize = fileSize;
    }

    public String getShortUrlKey() {
        return this.shortUrlKey;
    }
}
