package jp.gr.java_conf.star_diopside.spark.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.gr.java_conf.star_diopside.spark.data.entity.AttachedFileData;

/**
 * 添付ファイルデータリポジトリインタフェース
 */
public interface AttachedFileDataRepository extends JpaRepository<AttachedFileData, Long> {

}
