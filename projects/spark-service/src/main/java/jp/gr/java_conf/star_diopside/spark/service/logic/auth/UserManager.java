package jp.gr.java_conf.star_diopside.spark.service.logic.auth;

import java.time.ZonedDateTime;
import java.util.Date;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import jp.gr.java_conf.star_diopside.spark.data.entity.User;
import jp.gr.java_conf.star_diopside.spark.data.repository.UserRepository;
import jp.gr.java_conf.star_diopside.spark.service.userdetails.LoginUserDetails;

import org.springframework.transaction.annotation.Transactional;

/**
 * ユーザ管理クラス
 */
@Named
@Singleton
public class UserManager {

    @Inject
    private UserRepository userRepository;

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
