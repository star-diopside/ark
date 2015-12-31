package jp.gr.java_conf.star_diopside.spark.service.logic.file;

import java.nio.file.Path;
import java.util.Optional;

import jp.gr.java_conf.star_diopside.spark.data.entity.AttachedFile;

/**
 * 添付ファイル管理インタフェース
 */
public interface AttachedFileManager {

    /**
     * 添付ファイルを取得する。
     * 
     * @param attachedFileId 添付ファイルID
     * @return 添付ファイルエンティティ
     */
    Optional<AttachedFile> find(Long attachedFileId);

    /**
     * 添付ファイルを登録する。
     * 
     * @param file 添付ファイル
     * @param fileName ファイル名
     * @return 登録した添付ファイルエンティティ
     */
    AttachedFile create(Path file, String fileName);

}
