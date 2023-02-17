package login.project.service.user;

import login.project.domain.User;
import login.project.repository.user.LoginUserDto;
import login.project.repository.user.UserDto;


public interface UserService {

    User signup(UserDto userDto);


    User login(LoginUserDto loginUserDto);

}
























