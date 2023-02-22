package login.project.controller;

import login.project.domain.User;
import login.project.domain.UserRoleEnum;
import login.project.error.ErrorCode;
import login.project.error.ErrorResponse;
import login.project.repository.user.LoginUserDto;
import login.project.repository.user.UserDto;
import login.project.security.JwtTokenProvider;
import login.project.security.UserDetailsImpl;
import login.project.service.user.UserService;
import login.project.service.user.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 프로젝트 : restAPI 방식으로 변환 필요
/*
Login 과정을 직접 구현해야 함
로그인을 요청하면 동시에 JWT 토큰을 만들어서 반환해줘야 함
전달받은 아이디와 패스워드를 가지고 실제 DB에 존재하는 유저인지 확인 후 User 객체로 반환
User의 이메일과 권한을 추출해 토큰을 만들어주는 작업
 */
@Slf4j
@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserServiceImpl userService;
    private final JwtTokenProvider jwtTokenProvider;

    //회원가입 페이지 이동
    @GetMapping("/user/signup")
    public ResponseEntity<String> signupForm(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            User user = userDetails.getUser();
            return ResponseEntity.ok().body(String.valueOf(user));
        } catch (NullPointerException e){
            return ResponseEntity.badRequest().body(ErrorResponse.of(ErrorCode.RESOURCE_NOT_FOUND).getMessage());
            // NullPointerException은 실제 값이 아닌 null을 가지고 있는 객체/변수를 호출할 때 발생하는 예외
        }
    }

    //회원가입
    @ResponseBody
    @PostMapping("/user/signup")
    public ResponseEntity<User> signUp(@RequestBody UserDto userDto) {
        User user = userService.signup(userDto);
        return ResponseEntity.ok().body(user);
    }

    //로그인 페이지 이동
    @GetMapping("/user/login")
    public ResponseEntity<User> loginForm(@AuthenticationPrincipal UserDetailsImpl userDetails) {
            User user = userDetails.getUser();
            return ResponseEntity.ok().body(user);
    }

    @ResponseBody
    @PostMapping("/user/login")
    public String login(LoginUserDto loginUserDto, HttpServletResponse response) {

        System.out.println("loginUserDto = " + loginUserDto);

        User user = userService.login(loginUserDto);
        System.out.println("user = " + user);
        String checkEmail = user.getEmail();
        UserRoleEnum role = user.getRole();

        System.out.println("role = " + role);

        String token = jwtTokenProvider.createToken(checkEmail, role);
        System.out.println("token = " + token);
        response.setHeader("JWT", token);

        return token;
    }
}









































