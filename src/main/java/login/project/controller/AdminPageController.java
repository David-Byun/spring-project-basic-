package login.project.controller;

import login.project.security.UserDetailsImpl;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminPageController {

    @GetMapping("/api/admin")
    @Secured("ROLE_ADMIN")
    public String adminPage(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return "admin";
    }
}
