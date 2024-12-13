package woongjin.gatherMind.exception.notFound;

import woongjin.gatherMind.constants.ErrorMessages;

public class StudyRoleCodeNotFoundException extends NotFoundException {

    public StudyRoleCodeNotFoundException(String message) {
        super(message);
    }

    public StudyRoleCodeNotFoundException(int code) {
        super(code+ErrorMessages.STUDY_ROLE_CODE_NOT_FOUND);
    }

    public StudyRoleCodeNotFoundException() {
        super(ErrorMessages.STUDY_ROLE_CODE_NOT_FOUND);
    }
}