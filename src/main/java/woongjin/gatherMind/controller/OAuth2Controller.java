package woongjin.gatherMind.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import woongjin.gatherMind.config.OAuthConfig;

@Controller
public class OAuth2Controller {

    @Autowired
    private OAuthConfig oAuthConfig;

    @GetMapping("/google/login")
    public String googleLogin() {

    }

    @GetMapping("/github/login")
    public String githubLogin() {

    }

    @GetMapping("/kakao/login")
    public String kakaoLogin() {

    }

    @GetMapping("/naver/login")
    public String naverLogin() {

    }
}
