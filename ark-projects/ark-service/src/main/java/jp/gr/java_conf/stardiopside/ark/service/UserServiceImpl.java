package jp.gr.java_conf.stardiopside.ark.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;

import jp.gr.java_conf.stardiopside.ark.core.exception.ApplicationException;
import jp.gr.java_conf.stardiopside.ark.data.entity.Authority;
import jp.gr.java_conf.stardiopside.ark.data.entity.User;
import jp.gr.java_conf.stardiopside.ark.data.repository.AuthorityRepository;
import jp.gr.java_conf.stardiopside.ark.data.repository.UserRepository;
import jp.gr.java_conf.stardiopside.ark.service.dto.UserDto;
import jp.gr.java_conf.stardiopside.ark.service.userdetails.LoginUserDetails;

/**
 * ユーザ管理クラス
 */
@Named
@Singleton
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;
    private final int tempUserValidDays;

    public UserServiceImpl(UserRepository userRepository, AuthorityRepository authorityRepository,
            @Named("passwordEncoder") PasswordEncoder passwordEncoder,
            @Value("${application.settings.temp-user-valid-days}") int tempUserValidDays) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.passwordEncoder = passwordEncoder;
        this.tempUserValidDays = tempUserValidDays;
    }

    @Override
    @Transactional
    public void create(UserDto userDto, boolean mainRegistration, String... authorities) {
        // ユーザの存在チェックを行う。
        if (userRepository.existsById(userDto.getUsername())) {
            throw new ApplicationException("error.userExists", true);
        }

        // 現在時刻を取得する。
        LocalDateTime current = LocalDateTime.now();

        // ユーザ情報の登録を行う。
        User user = new User();

        user.setUserId(userDto.getUsername());
        user.setUsername(userDto.getDisplayName());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setPasswordUpdatedAt(current);
        user.setEnabled(true);
        user.setHighGradeRegistry(mainRegistration);

        userRepository.save(user);

        // 権限情報の登録を行う。
        authorityRepository.saveAll(Arrays.stream(authorities).map(auth -> {
            Authority authority = new Authority();
            authority.setUserId(userDto.getUsername());
            authority.setAuthority(auth);
            return authority;
        })::iterator);
    }

    @Override
    @Transactional
    public void createTemporaryUser(UserDto userDto) {
        create(userDto, false, "ROLE_USER");
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
    public void remove(User user) {
        userRepository.delete(user);
    }

    @Override
    @Transactional
    public boolean removeInvalidUser(LoginUserDetails loginUser) {
        // ユーザ情報を取得する。
        User user = loginUser.convertUserEntity();

        // 無効ユーザの場合、削除を行う
        if (!checkValid(user)) {
            remove(user);
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
        return !compareLocalDateTime(loginUser.getLastLoginAt(), user.getLastLoginAt())
                || !compareLocalDateTime(loginUser.getLogoutAt(), user.getLogoutAt());
    }

    /**
     * {@link LocalDateTime} を {@link java.util.Date} で表現可能なミリ秒までの精度に切り詰めて比較を行う。
     * 
     * @param a 比較対象の日付
     * @param b 比較対象の日付
     * @return 引数で指定された日付の値が等しい場合はtrue、それ以外の場合はfalse。
     */
    private boolean compareLocalDateTime(LocalDateTime a, LocalDateTime b) {
        if (a == b) {
            return true;
        } else if (a == null || b == null) {
            return false;
        } else {
            return a.truncatedTo(ChronoUnit.MILLIS).equals(b.truncatedTo(ChronoUnit.MILLIS));
        }
    }
}
