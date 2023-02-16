package login.project.controller;

import login.project.error.CustomException;
import login.project.error.ErrorCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenExceptionController {

    @GetMapping("/exception/entrypoint")
    public void entryPoint() {
        throw new CustomException(ErrorCode.NO_LOGIN);
    }

    @GetMapping("/exception/access")
    public void denied() {
        throw new CustomException(ErrorCode.NO_ADMIN);
    }
}
