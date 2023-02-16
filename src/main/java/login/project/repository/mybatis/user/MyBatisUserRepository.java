package login.project.repository.mybatis.user;

import login.project.domain.User;
import login.project.repository.item.ItemRepository;
import login.project.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class MyBatisUserRepository implements UserRepository {

    private final UserMapper userMapper;
    @Override
    public Optional<User> findByEmail(String email) {
        Optional<User> user = userMapper.searchByEmail(email);
        return user;
    }
}
