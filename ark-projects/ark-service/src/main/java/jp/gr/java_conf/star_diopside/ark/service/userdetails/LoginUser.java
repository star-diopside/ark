package jp.gr.java_conf.star_diopside.ark.service.userdetails;

import java.time.LocalDateTime;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import jp.gr.java_conf.star_diopside.ark.data.entity.User;
import jp.gr.java_conf.star_diopside.silver.commons.core.logging.Loggable;
import jp.gr.java_conf.star_diopside.silver.commons.core.logging.LoggingSetting;

/**
 * ログインユーザ情報クラス
 */
@SuppressWarnings("serial")
public class LoginUser implements LoginUserDetails, Loggable {

    @LoggingSetting(key = "userDetails")
    private UserDetails _userDetails;

    @LoggingSetting(key = "user")
    private User _user;

    /**
     * コンストラクタ
     * 
     * @param user ユーザエンティティ
     */
    public LoginUser(User user) {
        _userDetails = new org.springframework.security.core.userdetails.User(user.getUserId(), user.getPassword(),
                user.isEnabled(), true, true, true, AuthorityUtils.NO_AUTHORITIES);
        _user = user.clone();
    }

    /**
     * コンストラクタ
     * 
     * @param user ユーザ情報
     * @param loginUser ログインユーザ情報
     */
    public LoginUser(UserDetails user, LoginUserDetails loginUser) {
        update(user, loginUser.convertUserEntity());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return _userDetails.getAuthorities();
    }

    @Override
    public String getPassword() {
        return _userDetails.getPassword();
    }

    @Override
    public String getUsername() {
        return _userDetails.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return _userDetails.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return _userDetails.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return _userDetails.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return _userDetails.isEnabled();
    }

    @Override
    public String getUserId() {
        return getUsername();
    }

    @Override
    public String getNickname() {
        return _user.getUsername();
    }

    @Override
    public LocalDateTime getLastLoginAt() {
        return _user.getLastLoginAt();
    }

    @Override
    public LocalDateTime getLogoutAt() {
        return _user.getLogoutAt();
    }

    @Override
    public User convertUserEntity() {
        return _user.clone();
    }

    @Override
    public final void update(UserDetails userDetails, User user) {
        _userDetails = new org.springframework.security.core.userdetails.User(userDetails.getUsername(),
                userDetails.getPassword(), userDetails.isEnabled(), userDetails.isAccountNonExpired(),
                userDetails.isCredentialsNonExpired(), userDetails.isAccountNonLocked(), userDetails.getAuthorities());
        _user = user.clone();
    }
}
