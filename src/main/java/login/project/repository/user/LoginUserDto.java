package login.project.repository.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class LoginUserDto {
    private String email;
    private String password;
}
