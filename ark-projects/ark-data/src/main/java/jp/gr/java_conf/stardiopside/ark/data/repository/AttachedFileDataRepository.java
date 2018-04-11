package jp.gr.java_conf.stardiopside.ark.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.gr.java_conf.stardiopside.ark.data.entity.AttachedFileData;

/**
 * 添付ファイルデータリポジトリインタフェース
 */
public interface AttachedFileDataRepository extends JpaRepository<AttachedFileData, Long> {

}
