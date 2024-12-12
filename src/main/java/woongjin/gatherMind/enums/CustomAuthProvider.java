package woongjin.gatherMind.enums;

import woongjin.gatherMind.constants.ErrorMessages;
import woongjin.gatherMind.exception.notFound.AuthProviderNotFoundException;

public enum CustomAuthProvider {
    LOCAL(0, "local"),     // 일반 회원가입
    GOOGLE(1, "google"),   // 구글 OAuth
    GITHUB(2, "github"),   // 깃허브 OAuth
    KAKAO(3, "kakao"),     // 카카오 OAuth
    NAVER(4, "naver");     // 네이버 OAuth

    private final int code;
    private final String providerName;

    CustomAuthProvider(int code, String providerName) {
        this.code = code;
        this.providerName = providerName;
    }

    public int getCode() {
        return code;
    }

    public String getProviderName() {
        return providerName;
    }

    // 코드 기반 변환
    public static CustomAuthProvider fromCode(int code) throws AuthProviderNotFoundException {
        for (CustomAuthProvider provider : CustomAuthProvider.values()) {
            if (provider.code == code) {
                return provider;
            }
        }
        throw new AuthProviderNotFoundException(ErrorMessages.AUTH_PROVIDER_NOT_FOUND);
    }

    // 문자열 기반 변환 (OAuth2 RegistrationId에 대응)
    public static CustomAuthProvider fromProviderName(String name) throws AuthProviderNotFoundException {
        for (CustomAuthProvider provider : CustomAuthProvider.values()) {
            if (provider.providerName.equalsIgnoreCase(name)) {
                return provider;
            }
        }
        throw new AuthProviderNotFoundException(ErrorMessages.AUTH_PROVIDER_NOT_FOUND);
    }
}
