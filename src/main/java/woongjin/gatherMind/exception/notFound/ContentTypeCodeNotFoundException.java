package woongjin.gatherMind.exception.notFound;

import woongjin.gatherMind.constants.ErrorMessages;

public class ContentTypeCodeNotFoundException extends NotFoundException {

    public ContentTypeCodeNotFoundException(String message) {
        super(message);
    }

    public ContentTypeCodeNotFoundException(int code) {
        super(code+ErrorMessages.CONTENT_TYPE_CODE_NOT_FOUND);
    }

    public ContentTypeCodeNotFoundException() {
        super(ErrorMessages.CONTENT_TYPE_CODE_NOT_FOUND);
    }
}