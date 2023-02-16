package login.project.repository.user;

import lombok.Data;

@Data
public class UserSearchDto {
    private String email;

    public UserSearchDto(String email) {
        this.email = email;
    }
}
