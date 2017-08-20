package jp.gr.java_conf.star_diopside.spark.data.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
 * 郵便番号住所データエンティティクラス
 */
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@EntityListeners(TrackableListener.class)
@Table(name = "postal_codes")
@SuppressWarnings("serial")
public class PostalCode implements Serializable, Trackable {

    /** ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 全国地方公共団体コード */
    @Column(name = "local_government_code")
    private String localGovernmentCode;

    /** (旧)郵便番号 */
    @Column(name = "old_postal_code")
    private String oldPostalCode;

    /** 郵便番号 */
    @Column(name = "postal_code")
    private String postalCode;

    /** 都道府県名(カナ) */
    @Column(name = "kana_prefecture_name")
    private String kanaPrefectureName;

    /** 市区町村名(カナ) */
    @Column(name = "kana_municipality_name")
    private String kanaMunicipalityName;

    /** 町域名(カナ) */
    @Column(name = "kana_area_name")
    private String kanaAreaName;

    /** 都道府県名(漢字) */
    @Column(name = "kanji_prefecture_name")
    private String kanjiPrefectureName;

    /** 市区町村名(漢字) */
    @Column(name = "kanji_municipality_name")
    private String kanjiMunicipalityName;

    /** 町域名(漢字) */
    @Column(name = "kanji_area_name")
    private String kanjiAreaName;

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

}
