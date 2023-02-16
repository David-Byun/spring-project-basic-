package login.project.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class User {
    private Long id;
    private String email;
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
