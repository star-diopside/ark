package jp.gr.java_conf.stardiopside.ark.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.gr.java_conf.stardiopside.ark.data.entity.AttachedFile;

/**
 * 添付ファイルリポジトリインタフェース
 */
public interface AttachedFileRepository extends JpaRepository<AttachedFile, Long> {

}
