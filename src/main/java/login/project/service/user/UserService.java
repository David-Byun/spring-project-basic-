package login.project.service.user;

import login.project.domain.User;
import login.project.repository.user.UserRepository;
import login.project.repository.user.UserSearchDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private static final String ADMIN_PW ="AAABnv/xRVklrnYxKZ0aHgTBcXukeZygoC";

    //회원 ID 중복 확인
    public User signup(UserSearchDto userDto){
        String email = userDto.getEmail();
    }


}
