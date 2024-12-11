package woongjin.gatherMind.enums;

import woongjin.gatherMind.constants.ErrorMessages;
import woongjin.gatherMind.exception.notFound.StudyStatusCodeNotFoundException;

public enum StudyCategory {
    ALL(0),
    FRONT_END(1),
    BACK_END(2),
    FULL_STACK(3),
    DATA_SCIENCE(4),
    MOBILE_APP(5),
    AI_ML(6),
    DEVOPS(7),
    GAME_DEVELOPMENT(8),
    SECURITY(9),
    UI_UX_DESIGN(10),
    DATABASE(11),
    CLOUD_COMPUTING(12),
    BLOCKCHAIN(13),
    PROJECT_MANAGEMENT(14),
    NETWORKING(15),
    CYBERSECURITY(16),
    SOFT_SKILLS(17),
    ENTREPRENEURSHIP(18);

    private final int code;

    StudyCategory(int code) {
        this.code = code;
    }

    public int getCode(){
        return code;
    }

    public static StudyCategory fromCode(int code){
        for(StudyCategory status: StudyCategory.values()){
            if(status.code == code) {
                return status;
            }
        }
        throw new StudyStatusCodeNotFoundException(ErrorMessages.STUDY_STATUS_CODE_NOT_FOUND);
    }

}
