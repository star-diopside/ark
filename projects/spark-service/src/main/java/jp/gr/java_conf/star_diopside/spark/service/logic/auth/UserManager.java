package jp.gr.java_conf.star_diopside.spark.service.logic.auth;

import java.time.ZonedDateTime;
import java.util.Date;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import jp.gr.java_conf.star_diopside.spark.core.exception.ApplicationException;
import jp.gr.java_conf.star_diopside.spark.data.entity.Authority;
import jp.gr.java_conf.star_diopside.spark.data.entity.User;
import jp.gr.java_conf.star_diopside.spark.data.repository.AuthorityRepository;
import jp.gr.java_conf.star_diopside.spark.data.repository.UserRepository;
import jp.gr.java_conf.star_diopside.spark.service.userdetails.LoginUserDetails;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

/**
 * ユーザ管理クラス
 */
@Named
@Singleton
public class UserManager {

    @Inject
    private UserRepository userRepository;

    @Inject
    private AuthorityRepository authorityRepository;

    @Inject
    @Named("passwordEncoder")
    private PasswordEncoder passwordEncoder;

    @Transactional
    public void createUser(String userId, String username, String password) {

        // ユーザの存在チェックを行う。
        if (userRepository.exists(userId)) {
            throw new ApplicationException("error.UserExists", true);
        }

        // 現在時刻を取得する。
        Date current = new Date();

        // ユーザ情報の登録を行う。
        User user = new User();

        user.setUserId(userId);
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setPasswordUpdatedAt(current);
        user.setEnabled(true);
        user.setHighGradeRegistry(false);
        user.setLoginErrorCount(0);
        user.setLockoutAt(null);
        user.setLastLoginAt(null);
        user.setLogoutAt(null);
        user.setCreatedAt(current);
        user.setCreatedUserId(userId);
        user.setUpdatedAt(current);
        user.setUpdatedUserId(userId);

        userRepository.save(user);

        // 権限情報の登録を行う。
        Authority authority = new Authority();

        authority.setUserId(userId);
        authority.setAuthority("ROLE_USER");
        authority.setCreatedAt(current);
        authority.setCreatedUserId(userId);
        authority.setUpdatedAt(current);
        authority.setUpdatedUserId(userId);

        authorityRepository.save(authority);
    }

    /**
     * ログアウト処理を行う。
     * 
     * @param loginUser ユーザ情報
     */
    @Transactional
    public void logout(LoginUserDetails loginUser) {
        String userId = loginUser.getUserId();
        User user = userRepository.findOne(userId);

        // ログイン情報が更新されていない場合、ログアウト処理を行う。
        if (!checkLoginInfo(loginUser, user)) {
            Date current = new Date();
            user.setLogoutAt(current);
            user.setUpdatedAt(current);
            user.setUpdatedUserId(userId);
            userRepository.save(user);
        }
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
        return !equalsDateTime(loginUser.getLastLoginAt(), user.getLastLoginAt())
                || !equalsDateTime(loginUser.getLogoutAt(), user.getLogoutAt());
    }

    private static boolean equalsDateTime(ZonedDateTime dateTime, Date date) {
        if (dateTime == null) {
            return date == null;
        } else if (date == null) {
            return false;
        } else {
            return dateTime.toInstant().equals(date.toInstant());
        }
    }
}
