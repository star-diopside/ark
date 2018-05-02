package jp.gr.java_conf.stardiopside.ark.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.function.Supplier;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import jp.gr.java_conf.stardiopside.ark.core.exception.ApplicationException;
import jp.gr.java_conf.stardiopside.ark.data.entity.Authority;
import jp.gr.java_conf.stardiopside.ark.data.entity.User;
import jp.gr.java_conf.stardiopside.ark.data.repository.AuthorityRepository;
import jp.gr.java_conf.stardiopside.ark.data.repository.UserRepository;
import jp.gr.java_conf.stardiopside.ark.service.userdetails.LoginUserDetails;

/**
 * ユーザ管理クラス
 */
@Named
@Singleton
public class UserServiceImpl implements UserService {

    @Inject
    private UserRepository userRepository;

    @Inject
    private AuthorityRepository authorityRepository;

    @Inject
    @Named("passwordEncoder")
    private PasswordEncoder passwordEncoder;

    @Value("${application.settings.temp-user-valid-days}")
    private int tempUserValidDays;

    @Override
    @Transactional
    public void createUser(String userId, String username, Supplier<String> password) {
        // ユーザの存在チェックを行う。
        if (userRepository.existsById(userId)) {
            throw new ApplicationException("error.userExists", true);
        }

        // 現在時刻を取得する。
        LocalDateTime current = LocalDateTime.now();

        // ユーザ情報の登録を行う。
        User user = new User();

        user.setUserId(userId);
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password.get()));
        user.setPasswordUpdatedAt(current);
        user.setEnabled(true);
        user.setHighGradeRegistry(false);

        userRepository.save(user);

        // 権限情報の登録を行う。
        Authority authority = new Authority();

        authority.setUserId(userId);
        authority.setAuthority("ROLE_USER");

        authorityRepository.save(authority);
    }

    @Override
    public boolean checkValid(User user) {
        // ユーザの有効チェックを行う
        if (user.isHighGradeRegistry()) {
            // 本登録済みの場合、有効ユーザとする。
            return true;
        } else {
            // 仮登録中の場合、有効期間を超過すると無効ユーザとする。
            return user.getCreatedAt().until(LocalDateTime.now(), ChronoUnit.DAYS) < tempUserValidDays;
        }
    }

    @Override
    @Transactional
    public void removeUser(User user) {
        userRepository.delete(user);
    }

    @Override
    @Transactional
    public boolean removeInvalidUser(LoginUserDetails loginUser) {
        // ユーザ情報を取得する。
        User user = loginUser.convertUserEntity();

        // 無効ユーザの場合、削除を行う
        if (!checkValid(user)) {
            removeUser(user);
            return true;
        }

        return false;
    }

    @Override
    @Transactional
    public User loginSuccess(LoginUserDetails loginUser) {
        User user = userRepository.getOne(loginUser.getUserId());
        LocalDateTime current = LocalDateTime.now();

        user.setLoginErrorCount(0);
        user.setLastLoginAt(current);
        user.setLogoutAt(null);

        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User loginFailure(String userId) {
        User user = userRepository.getOne(userId);

        user.setLoginErrorCount(user.getLoginErrorCount() + 1);

        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void logout(LoginUserDetails loginUser) {
        String userId = loginUser.getUserId();
        User user = userRepository.getOne(userId);

        // ログイン情報が更新されていない場合、ログアウト処理を行う。
        if (!checkLoginInfo(loginUser, user)) {
            LocalDateTime current = LocalDateTime.now();
            user.setLogoutAt(current);
            userRepository.save(user);
        }
    }

    @Override
    @Transactional
    public boolean checkDualLogin(LoginUserDetails loginUser) {
        User user = userRepository.getOne(loginUser.getUserId());
        return checkLoginInfo(loginUser, user);
    }

    /**
     * ログイン情報の不変チェックを行う。
     * 
     * @param loginUser ログインユーザ情報
     * @param user ユーザテーブルから取得したユーザ情報
     * @return ログイン後にログイン情報が更新されている場合はtrue、それ以外の場合はfalse。
     */
    private boolean checkLoginInfo(LoginUserDetails loginUser, User user) {
        // 最終ログイン日時、ログアウト日時の判定を行う。
        return !compare​LocalDateTime(loginUser.getLastLoginAt(), user.getLastLoginAt())
                || !compare​LocalDateTime(loginUser.getLogoutAt(), user.getLogoutAt());
    }

    /**
     * {@link LocalDateTime} を {@link java.util.Date} で表現可能なミリ秒までの精度に切り詰めて比較を行う。
     * 
     * @param a 比較対象の日付
     * @param b 比較対象の日付
     * @return 引数で指定された日付の値が等しい場合はtrue、それ以外の場合はfalse。
     */
    private boolean compare​LocalDateTime(LocalDateTime a, LocalDateTime b) {
        if (a == b) {
            return true;
        } else if (a == null || b == null) {
            return false;
        } else {
            return a.truncatedTo(ChronoUnit.MILLIS).equals(b.truncatedTo(ChronoUnit.MILLIS));
        }
    }
}
