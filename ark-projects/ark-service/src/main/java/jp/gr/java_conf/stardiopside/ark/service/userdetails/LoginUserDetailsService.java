package jp.gr.java_conf.stardiopside.ark.service.userdetails;

import jp.gr.java_conf.stardiopside.ark.data.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * ログインユーザ情報サービスクラス
 */
public class LoginUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public LoginUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username '" + username + "' not found"));

        if (user.getAuthorities().isEmpty()) {
            throw new UsernameNotFoundException("User '" + username + "' has no authorities");
        }

        return new LoginUser(user);
    }
}
