package jp.gr.java_conf.stardiopside.ark.service.userdetails;

import java.time.LocalDateTime;

import org.springframework.security.core.userdetails.UserDetails;

import jp.gr.java_conf.stardiopside.ark.data.entity.User;

/**
 * ログインユーザ情報詳細インタフェース
 */
public interface LoginUserDetails extends UserDetails {

    /**
     * ユーザIDを取得する。
     * 
     * @return ユーザID
     */
    String getUserId();

    /**
     * ニックネームを取得する。
     * 
     * @return nickname ニックネーム
     */
    String getNickname();

    /**
     * 最終ログイン日時を取得する。
     * 
     * @return 最終ログイン日時
     */
    LocalDateTime getLastLoginAt();

    /**
     * ログアウト日時を取得する。
     * 
     * @return ログアウト日時
     */
    LocalDateTime getLogoutAt();

    /**
     * ユーザエンティティに変換する。
     * 
     * @return ユーザエンティティ
     */
    User convertUserEntity();

    /**
     * ログイン情報を更新する。
     * 
     * @param userDetails ユーザ情報
     * @param user ユーザエンティティ
     */
    void update(UserDetails userDetails, User user);

}
