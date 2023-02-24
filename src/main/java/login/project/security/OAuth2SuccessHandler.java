package login.project.security;

import login.project.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

@Component
@Slf4j
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    //서비스마다 다르지만, 비밀번호 입력하는 페이지 이동한 후 회원가입
    //이메일과 닉네임은 Oauth 서버에서 제공해주는 값을 그대로 사용하기 위해 redirect URI 뒤에 파라미터 붙여서 controller 보냄
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        log.info("principal : {}", oAuth2User);

        String email = (String) oAuth2User.getAttributes().get("email");
        String nickname = (String) oAuth2User.getAttributes().get("name");

        //한글 닉네임인 경우 인코딩
        nickname = URLEncoder.encode(nickname);
        log.info("nickname : {}", nickname);

        //패스워드 입력하도록 리다이렉트
        response.sendRedirect("/user/oauth/password" + email + "/" + nickname);


    }
}
