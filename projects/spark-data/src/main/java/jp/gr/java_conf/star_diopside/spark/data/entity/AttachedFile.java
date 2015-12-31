package jp.gr.java_conf.star_diopside.spark.data.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.apache.commons.codec.digest.DigestUtils;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.ToString;

/**
 * 添付ファイルエンティティクラス
 */
@Data
@ToString(exclude = "data")
@Entity
@EntityListeners(TrackableListener.class)
@Table(name = "attached_files")
@SuppressWarnings("serial")
public class AttachedFile implements Serializable, Trackable {

    /** 添付ファイルID */
    @Id
    @Column(name = "attached_file_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long attachedFileId;

    /** ファイル名 */
    private String name;

    /** ファイルデータ */
    private byte[] data;

    /** ハッシュ値 */
    @Setter(AccessLevel.NONE)
    private String hash;

    /** 登録日時 */
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    /** 登録ユーザID */
    @Column(name = "created_user_id")
    private String createdUserId;

    /** 更新日時 */
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    /** 更新ユーザID */
    @Column(name = "updated_user_id")
    private String updatedUserId;

    /** バージョン */
    @Version
    private int version;

    /**
     * ファイルデータを設定する。
     * 
     * @param data ファイルデータ
     */
    public void setData(byte[] data) {
        this.data = data;
        this.hash = DigestUtils.sha256Hex(data);
    }
}
