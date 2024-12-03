package woongjin.gatherMind.controller;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import woongjin.gatherMind.DTO.FileUploadResponseDTO;
import woongjin.gatherMind.entity.EntityFileMapping;
import woongjin.gatherMind.service.FileService;

@RestController
@RequestMapping("/api/files")
@AllArgsConstructor
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    private final FileService fileService;

}
