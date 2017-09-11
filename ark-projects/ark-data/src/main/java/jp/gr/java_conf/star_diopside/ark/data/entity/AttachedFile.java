package jp.gr.java_conf.star_diopside.ark.data.entity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
 * 添付ファイルエンティティクラス
 */
@Getter
@Setter
@ToString(exclude = "attachedFileData")
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "attached_files")
@SuppressWarnings("serial")
public class AttachedFile implements Serializable {

    /** ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** ファイル名 */
    private String name;

    /** コンテンツタイプ */
    @Column(name = "content_type")
    private String contentType;

    /** ファイルサイズ */
    private long size;

    /** ハッシュ値 */
    private String hash;

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

    /** 添付ファイルデータ */
    @OneToMany(mappedBy = "attachedFile", cascade = CascadeType.ALL)
    private List<AttachedFileData> attachedFileData;

    /**
     * ファイルデータを読み込む {@link InputStream} を生成する。
     * 
     * @return ファイルデータを読み込む {@link InputStream}
     */
    public InputStream newDataInputStream() {
        try {
            Path tempFile = Files.createTempFile(null, null);
            try (OutputStream output = Files.newOutputStream(tempFile)) {
                attachedFileData.stream().sorted(Comparator.comparingInt(AttachedFileData::getOrderBy))
                        .forEachOrdered(data -> {
                            try {
                                output.write(data.getData());
                            } catch (IOException e) {
                                throw new UncheckedIOException(e);
                            }
                        });
            }
            return Files.newInputStream(tempFile, StandardOpenOption.DELETE_ON_CLOSE);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
