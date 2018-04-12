package jp.gr.java_conf.stardiopside.ark.data.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
 * 添付ファイルデータエンティティクラス
 */
@Getter
@Setter
@ToString(exclude = { "data", "attachedFile" })
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "attached_file_data")
@SuppressWarnings("serial")
public class AttachedFileData implements Serializable {

    /** ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 添付ファイルID */
    @Column(name = "attached_file_id", insertable = false, updatable = false)
    private Long attachedFileId;

    /** 順序 */
    @Column(name = "order_by")
    private int orderBy;

    /** ファイルデータ */
    private byte[] data;

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

    /** 添付ファイルエンティティ */
    @ManyToOne
    @JoinColumn(name = "attached_file_id", referencedColumnName = "id")
    private AttachedFile attachedFile;

}
