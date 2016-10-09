package jp.gr.java_conf.star_diopside.spark.data.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import jp.gr.java_conf.star_diopside.silver.commons.data.converter.LocalDateTimeConverter;
import jp.gr.java_conf.star_diopside.spark.data.support.Trackable;
import jp.gr.java_conf.star_diopside.spark.data.support.TrackableListener;
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
@EntityListeners(TrackableListener.class)
@Table(name = "authorities")
@IdClass(AuthorityId.class)
@SuppressWarnings("serial")
public class Authority implements Serializable, Trackable {

    /** ユーザID */
    @Id
    @Column(name = "user_id")
    private String userId;

    /** 権限 */
    @Id
    private String authority;

    /** 登録日時 */
    @Column(name = "created_at")
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime createdAt;

    /** 登録ユーザID */
    @Column(name = "created_user_id")
    private String createdUserId;

    /** 更新日時 */
    @Column(name = "updated_at")
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime updatedAt;

    /** 更新ユーザID */
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
