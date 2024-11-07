package woongjin.gatherMind.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import woongjin.gatherMind.exception.member.MemberNotFoundException;
import woongjin.gatherMind.exception.study.StudyNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(StudyNotFoundException.class)
    public ResponseEntity<String> handleStudyNotFoundException(StudyNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<String> handleMemberNotFoundException(MemberNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
