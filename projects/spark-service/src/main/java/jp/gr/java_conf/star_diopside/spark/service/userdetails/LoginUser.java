package jp.gr.java_conf.star_diopside.spark.service.userdetails;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Stream;
import java.util.stream.Stream.Builder;

import jp.gr.java_conf.star_diopside.spark.core.logging.Loggable;
import jp.gr.java_conf.star_diopside.spark.core.logging.LoggableUtil;
import jp.gr.java_conf.star_diopside.spark.data.entity.User;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * ログインユーザ情報クラス
 */
@SuppressWarnings("serial")
public class LoginUser implements LoginUserDetails, Loggable {

    private UserDetails _userDetails;
    private User _user;
    private ZonedDateTime _lastLoginAt;
    private ZonedDateTime _logoutAt;

    /**
     * コンストラクタ
     * 
     * @param user ユーザエンティティ
     */
    public LoginUser(User user) {
        _userDetails = new org.springframework.security.core.userdetails.User(user.getUserId(), user.getPassword(),
                user.getEnabled(), true, true, true, AuthorityUtils.NO_AUTHORITIES);
        _user = user.clone();
        _lastLoginAt = toZonedDateTime(user.getLastLoginAt());
        _logoutAt = toZonedDateTime(user.getLogoutAt());
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
    public ZonedDateTime getLastLoginAt() {
        return _lastLoginAt;
    }

    @Override
    public ZonedDateTime getLogoutAt() {
        return _logoutAt;
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
        _lastLoginAt = toZonedDateTime(user.getLastLoginAt());
        _logoutAt = toZonedDateTime(user.getLogoutAt());
    }

    private static ZonedDateTime toZonedDateTime(Date date) {
        return date == null ? null : ZonedDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    @Override
    public Stream<?> streamLoggingObjects() {
        Builder<String> builder = Stream.builder();
        LoggableUtil.addLog(builder, _userDetails, "userDetails");
        LoggableUtil.addLog(builder, _user, "user");
        return builder.build();
    }
}
