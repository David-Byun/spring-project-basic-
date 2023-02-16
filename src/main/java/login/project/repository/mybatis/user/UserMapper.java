package login.project.repository.mybatis.user;

import login.project.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface UserMapper {

    Optional<User> searchByEmail(@Param("email") String email);

    void save(User user);
}
