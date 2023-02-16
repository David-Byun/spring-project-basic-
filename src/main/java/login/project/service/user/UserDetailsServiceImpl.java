package login.project.service.user;

import login.project.domain.User;
import login.project.error.CustomException;
import login.project.error.ErrorCode;
import login.project.repository.user.UserRepository;
import login.project.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(()->new CustomException(ErrorCode.INVALID_CODE));
        return new UserDetailsImpl(user);
    }
}
