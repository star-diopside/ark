package jp.gr.java_conf.star_diopside.spark.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.gr.java_conf.star_diopside.spark.data.entity.AttachedFile;

/**
 * 添付ファイルリポジトリインタフェース
 */
public interface AttachedFileRepository extends JpaRepository<AttachedFile, Long> {

}
