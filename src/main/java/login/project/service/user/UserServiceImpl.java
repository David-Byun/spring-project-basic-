package login.project.service.user;

import login.project.domain.User;
import login.project.domain.UserRoleEnum;
import login.project.error.CustomException;
import login.project.error.ErrorCode;
import login.project.repository.user.LoginUserDto;
import login.project.repository.user.UserDto;
import login.project.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private static final String ADMIN_PW ="AAABnv/xRVklrnYxKZ0aHgTBcXukeZygoC";

    public User signup(UserDto userDto){
        String email = userDto.getEmail();

        //회원 ID 중복 확인
        Optional<User> found = userRepository.findByEmail(email);
        if (found.isPresent()) {
            throw new CustomException(ErrorCode.SAME_USER);
        }

        String nickname = userDto.getNickname();

        //패스워드 암호화
        String password = passwordEncoder.encode(userDto.getPassword());

        //사용자 Role 확인
        UserRoleEnum role = UserRoleEnum.ROLE_MEMBER;
        //true 면 == 관리자이면
        //boolean 타입의 getter는 is를 붙인다.
        if (userDto.isAdmin()) {
            if (!userDto.getAdminToken().equals(ADMIN_PW)) {
                throw new CustomException(ErrorCode.ADMIN_TOKEN);
            }
            //role을 admin으로 바꿔준다.
            role = UserRoleEnum.ROLE_ADMIN;
        }

        User user = new User(email, nickname, password, role);
        userRepository.save(user);
        return user;
    }

    //로그인
    public User login(LoginUserDto loginUserDto) {
        User user = userRepository.findByEmail(loginUserDto.getEmail()).orElseThrow(() -> new CustomException(ErrorCode.NO_USER));
        if (!passwordEncoder.matches(loginUserDto.getPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.NO_USER);
        }
        return user;
    }
}
