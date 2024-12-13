package woongjin.gatherMind.enums;

import woongjin.gatherMind.exception.notFound.RoleCodeNotFoundException;

public enum Role {

    ADMIN(1),
    USER(2);

    private final int code;

    Role(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static Role fromCode(int code) {
        for(Role role : Role.values()) {
            if(role.getCode() == code) {
                return role;
            }
        }
        throw new RoleCodeNotFoundException(code);
    }

}
