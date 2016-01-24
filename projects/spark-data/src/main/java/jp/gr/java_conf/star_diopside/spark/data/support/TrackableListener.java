package jp.gr.java_conf.star_diopside.spark.data.support;

import java.time.LocalDateTime;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * エンティティ更新情報の設定を行うリスナークラス
 */
public class TrackableListener {

    /**
     * エンティティ登録時の情報を設定する。
     * 
     * @param entity 登録を行うエンティティ
     */
    @PrePersist
    public void changeCreated(Trackable entity) {
        String username = principalName();
        LocalDateTime current = LocalDateTime.now();
        entity.setCreatedAt(current);
        entity.setCreatedUserId(username);
        entity.setUpdatedAt(current);
        entity.setUpdatedUserId(username);
    }

    /**
     * エンティティ更新時の情報を設定する。
     * 
     * @param entity 更新を行うエンティティ
     */
    @PreUpdate
    public void changeUpdated(Trackable entity) {
        String username = principalName();
        LocalDateTime current = LocalDateTime.now();
        entity.setUpdatedAt(current);
        entity.setUpdatedUserId(username);
    }

    /**
     * 認証ユーザ名を取得する。
     * 
     * @return 認証ユーザ名
     */
    private String principalName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication == null ? null : authentication.getName();
    }
}
