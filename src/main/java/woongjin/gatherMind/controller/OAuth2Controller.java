package woongjin.gatherMind.controller;

import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
public class OAuth2Controller {

    @GetMapping("/oauth-success")
    public Map<String, Object> oauthSuccess(OAuth2User oAuth2User) {
        if (oAuth2User == null) {
            return Collections.singletonMap("error", "OAuth2User is null");
        }

        return oAuth2User.getAttributes();
    }
}
