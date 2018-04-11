package jp.gr.java_conf.stardiopside.ark.batch.bean;

import java.io.Serializable;

import lombok.Data;

/**
 * 郵便番号住所データ
 */
@Data
@SuppressWarnings("serial")
public class PostalCodeAddress implements Serializable {

    /** 全国地方公共団体コード */
    private String localGovernmentCode;

    /** (旧)郵便番号 */
    private String oldPostalCode;

    /** 郵便番号 */
    private String postalCode;

    /** 都道府県名(カナ) */
    private String kanaPrefectureName;

    /** 市区町村名(カナ) */
    private String kanaMunicipalityName;

    /** 町域名(カナ) */
    private String kanaAreaName;

    /** 都道府県名(漢字) */
    private String kanjiPrefectureName;

    /** 市区町村名(漢字) */
    private String kanjiMunicipalityName;

    /** 町域名(漢字) */
    private String kanjiAreaName;

    /**
     * フラグ(一町域が二以上の郵便番号で表される場合の表示)
     * <ul>
     * <li>1 : 該当</li>
     * <li>0 : 該当せず</li>
     * </ul>
     */
    private String flag1;

    /**
     * フラグ(小字毎に番地が起番されている町域の表示)
     * <ul>
     * <li>1 : 該当</li>
     * <li>0 : 該当せず</li>
     * </ul>
     */
    private String flag2;

    /**
     * フラグ(丁目を有する町域の場合の表示)
     * <ul>
     * <li>1 : 該当</li>
     * <li>0 : 該当せず</li>
     * </ul>
     */
    private String flag3;

    /**
     * フラグ(一つの郵便番号で二以上の町域を表す場合の表示)
     * <ul>
     * <li>1 : 該当</li>
     * <li>0 : 該当せず</li>
     * </ul>
     */
    private String flag4;

    /**
     * フラグ(更新の表示)
     * <ul>
     * <li>0 : 変更なし</li>
     * <li>1 : 変更あり</li>
     * <li>2 : 廃止(廃止データのみ使用)</li>
     * </ul>
     */
    private String flag5;

    /**
     * フラグ(変更理由)
     * <ul>
     * <li>0 : 変更なし</li>
     * <li>1 : 市政・区政・町政・分区・政令指定都市施行</li>
     * <li>2 : 住居表示の実施</li>
     * <li>3 : 区画整理</li>
     * <li>4 : 郵便区調整等</li>
     * <li>5 : 訂正</li>
     * <li>6 : 廃止(廃止データのみ使用)</li>
     * </ul>
     */
    private String flag6;

}
