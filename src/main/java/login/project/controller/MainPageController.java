package login.project.controller;

import login.project.domain.User;
import login.project.security.UserDetailsImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainPageController {

    public String mainPage(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
        if (userDetails != null) {
            User user = userDetails.getUser();
            //user.getRole().getDescription() 확인
        }
        //rest api 스타일로 변경 필요
        return "index";
    }
}
