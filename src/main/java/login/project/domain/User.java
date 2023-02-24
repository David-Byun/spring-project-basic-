package login.project.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Validated
public class User {
    private Long id;
    @NotBlank(message="공백은 입력할 수 없어요")
    private String email;

    @NotNull(message="Null값은 허용하지 않아요")
    @NotBlank
    private String nickname;

    private String password;
    private UserRoleEnum role;

    public User(String email, String nickname, String password, UserRoleEnum role) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }
}
