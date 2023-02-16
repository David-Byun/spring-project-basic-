package login.project.repository.user;

import login.project.domain.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByEmail(String email);

    void save(User user);
}
