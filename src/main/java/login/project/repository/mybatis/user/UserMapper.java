package login.project.repository.mybatis.user;

import login.project.domain.User;
import login.project.repository.user.UserSearchDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Mapper
public interface UserMapper {

    Optional<User> searchByEmail(@Param("email") String email);
}
