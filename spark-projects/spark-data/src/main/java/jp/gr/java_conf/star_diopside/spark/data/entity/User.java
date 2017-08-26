package jp.gr.java_conf.star_diopside.spark.data.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * ユーザエンティティクラス
 */
@Getter
@Setter
@ToString(exclude = "authorities")
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "users")
@SuppressWarnings("serial")
public class User implements Serializable, Cloneable {

    /** ユーザID */
    @Id
    @Column(name = "user_id")
    private String userId;

    /** ユーザ名 */
    private String username;

    /** パスワード */
    private String password;

    /** パスワード更新日時 */
    @Column(name = "password_updated_at")
    private LocalDateTime passwordUpdatedAt;

    /** 有効フラグ */
    private boolean enabled;

    /** 本登録フラグ */
    @Column(name = "high_grade_registry")
    private boolean highGradeRegistry;

    /** ログインエラー回数 */
    @Column(name = "login_error_count")
    private int loginErrorCount;

    /** ロックアウト日時 */
    @Column(name = "lockout_at")
    private LocalDateTime lockoutAt;

    /** 最終ログイン日時 */
    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;

    /** ログアウト日時 */
    @Column(name = "logout_at")
    private LocalDateTime logoutAt;

    /** 登録日時 */
    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    /** 登録ユーザID */
    @CreatedBy
    @Column(name = "created_user_id")
    private String createdUserId;

    /** 更新日時 */
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /** 更新ユーザID */
    @LastModifiedBy
    @Column(name = "updated_user_id")
    private String updatedUserId;

    /** バージョン */
    @Version
    private int version;

    /** 権限エンティティ一覧 */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Authority> authorities;

    @Override
    public User clone() {
        try {
            return (User) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new UnsupportedOperationException(e);
        }
    }
}
