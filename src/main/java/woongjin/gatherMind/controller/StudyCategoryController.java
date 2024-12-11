package woongjin.gatherMind.controller;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woongjin.gatherMind.DTO.StudyCategoryResponseDTO;
import woongjin.gatherMind.enums.StudyCategory;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/study-categories")
public class StudyCategoryController {

    @GetMapping
    @Cacheable("studyCategories") // 캐시 이름 정의
    public ResponseEntity<List<StudyCategoryResponseDTO>> getAllCategories() {
        List<StudyCategoryResponseDTO> categories = Arrays.stream(StudyCategory.values())
                .map(category -> new StudyCategoryResponseDTO(category.name(), category.getCode()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(categories);
    }
}
