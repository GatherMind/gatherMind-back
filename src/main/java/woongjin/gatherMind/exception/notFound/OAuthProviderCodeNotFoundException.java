package woongjin.gatherMind.exception.notFound;

import woongjin.gatherMind.constants.ErrorMessages;

public class OAuthProviderCodeNotFoundException extends NotFoundException {

    public OAuthProviderCodeNotFoundException(String message) {
        super(message);
    }

    public OAuthProviderCodeNotFoundException(int code) {
        super(code+ErrorMessages.PROVIDER_CODE_NOT_FOUND);
    }

    public OAuthProviderCodeNotFoundException() {
        super(ErrorMessages.PROVIDER_CODE_NOT_FOUND);
    }
}