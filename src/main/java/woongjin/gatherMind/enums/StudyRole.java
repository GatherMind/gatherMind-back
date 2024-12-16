package woongjin.gatherMind.enums;

import lombok.Getter;
import woongjin.gatherMind.exception.notFound.StudyRoleCodeNotFoundException;

@Getter
public enum StudyRole {
    ADMIN(1),
    MEMBER(2);

    private final int code;

    StudyRole(int code) {
        this.code = code;
    }

    public static StudyRole fromCode(int code) {
        for (StudyRole role : StudyRole.values()) {
            if (role.code == code) {
                return role;
            }
        }
        throw new StudyRoleCodeNotFoundException(code);
    }
}
