package woongjin.gatherMind.enums;

import woongjin.gatherMind.exception.notFound.OAuthProviderCodeNotFoundException;

public enum OAuthProvider {
    GOOGLE(1),
    GITHUB(2),
    KAKAO(3),
    NAVER(4);

    private final int code;

    OAuthProvider(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static OAuthProvider fromCode(int code) {
        for (OAuthProvider oAuthProvider : OAuthProvider.values()) {
            if (oAuthProvider.getCode() == code) {
                return oAuthProvider;
            }
        }
        throw new OAuthProviderCodeNotFoundException(code);
    }
}
