package jp.gr.java_conf.star_diopside.spark.service.userdetails;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;

import jp.gr.java_conf.star_diopside.spark.data.entity.User;
import jp.gr.java_conf.star_diopside.spark.data.repository.AuthorityRepository;
import jp.gr.java_conf.star_diopside.spark.data.repository.UserRepository;

/**
 * ログインユーザ情報サービスクラス
 */
public class LoginUserDetailsService extends JdbcDaoImpl {

    @Inject
    private UserRepository userRepository;

    @Inject
    private AuthorityRepository authorityRepository;

    @Override
    protected List<UserDetails> loadUsersByUsername(String username) {
        User user = userRepository.findOne(username);
        return user == null ? Collections.emptyList() : Collections.singletonList(new LoginUser(user));
    }

    @Override
    protected List<GrantedAuthority> loadUserAuthorities(String username) {
        return authorityRepository.findByUserId(username).stream()
                .map(authority -> new SimpleGrantedAuthority(getRolePrefix() + authority.getAuthority()))
                .collect(Collectors.toList());
    }

    @Override
    protected UserDetails createUserDetails(String username, UserDetails userFromUserQuery,
            List<GrantedAuthority> combinedAuthorities) {
        return new LoginUser(super.createUserDetails(username, userFromUserQuery, combinedAuthorities),
                (LoginUserDetails) userFromUserQuery);
    }
}
