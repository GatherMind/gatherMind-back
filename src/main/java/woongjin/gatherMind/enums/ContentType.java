package woongjin.gatherMind.enums;

import woongjin.gatherMind.exception.notFound.ContentTypeCodeNotFoundException;

public enum ContentType {
    BOARD(1),
    ANSWER(2);

    private final int code;

    ContentType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static ContentType fromCode(int code) {
        for(ContentType contentType: ContentType.values()) {
            if(contentType.code == code) {
                return contentType;
            }
        }
        throw new ContentTypeCodeNotFoundException(code);
    }

}
