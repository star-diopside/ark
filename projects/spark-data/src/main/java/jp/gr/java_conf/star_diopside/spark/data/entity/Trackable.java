package jp.gr.java_conf.star_diopside.spark.data.entity;

import java.util.Date;

/**
 * エンティティ更新情報の設定処理を定義したインタフェース
 */
public interface Trackable {

    /**
     * 登録日時を設定する。
     * 
     * @param createdAt 登録日時
     */
    void setCreatedAt(Date createdAt);

    /**
     * 登録ユーザIDを設定する。
     * 
     * @param createdUserId 登録ユーザID
     */
    void setCreatedUserId(String createdUserId);

    /**
     * 更新日時を設定する。
     * 
     * @param updatedAt 更新日時
     */
    void setUpdatedAt(Date updatedAt);

    /**
     * 更新ユーザIDを設定する。
     * 
     * @param updatedUserId 更新ユーザID
     */
    void setUpdatedUserId(String updatedUserId);

}
