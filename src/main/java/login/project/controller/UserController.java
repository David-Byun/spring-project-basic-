package login.project.controller;

import login.project.domain.User;
import login.project.domain.UserRoleEnum;
import login.project.repository.user.LoginUserDto;
import login.project.repository.user.UserDto;
import login.project.security.JwtTokenProvider;
import login.project.security.UserDetailsImpl;
import login.project.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

// 프로젝트 : restAPI 방식으로 변환 필요
/*
Login 과정을 직접 구현해야 함
로그인을 요청하면 동시에 JWT 토큰을 만들어서 반환해줘야 함
전달받은 아이디와 패스워드를 가지고 실제 DB에 존재하는 유저인지 확인 후 User 객체로 반환
User의 이메일과 권한을 추출해 토큰을 만들어주는 작업
 */
@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    //회원가입 페이지 이동
    @GetMapping("/user/signup")
    public String signupForm(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
        try {
            User user = userDetails.getUser();
            model.addAttribute("user", user);
        } catch (NullPointerException e){
            return "signup";
            // NullPointerException은 실제 값이 아닌 null을 가지고 있는 객체/변수를 호출할 때 발생하는 예외
        }
        return "signup";
    }

    //회원가입
    @ResponseBody
    @PostMapping("/user/signup")
    public User signUp(@RequestBody UserDto userDto) {
        User user = userService.signup(userDto);
        return user;
    }

    //로그인 페이지 이동
    @GetMapping("/user/login")
    public String loginForm(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
        try {
            User user = userDetails.getUser();
            model.addAttribute("user", user);
        } catch (NullPointerException e) {
            model.addAttribute("user", "");
            return "login";
        }
        return "login";
    }

    @PostMapping("/user/login")
    @ResponseBody
    public String login(LoginUserDto loginUserDto, HttpServletResponse response) {
        User user = userService.login(loginUserDto);
        String checkEmail = user.getEmail();
        UserRoleEnum role = user.getRole();

        String token = jwtTokenProvider.createToken(checkEmail, role);
        response.setHeader("JWT", token);

        return token;
    }



}









































