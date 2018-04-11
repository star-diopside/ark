package jp.gr.java_conf.stardiopside.ark.data.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
 * 権限エンティティクラス
 */
@Getter
@Setter
@ToString(exclude = "user")
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "authorities")
@IdClass(AuthorityId.class)
@SuppressWarnings("serial")
public class Authority implements Serializable {

    /** ユーザID */
    @Id
    @Column(name = "user_id")
    private String userId;

    /** 権限 */
    @Id
    private String authority;

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

    /** ユーザエンティティ */
    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

}
