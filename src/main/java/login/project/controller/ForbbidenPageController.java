package login.project.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ForbbidenPageController {

    @GetMapping("/forbbiden")
    public String forbiddenPage() {
        return "forbbiden";
    }
}
