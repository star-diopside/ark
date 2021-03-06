package jp.gr.java_conf.stardiopside.ark.service;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import jp.gr.java_conf.stardiopside.ark.data.entity.AttachedFile;

/**
 * 添付ファイル管理インタフェース
 */
public interface AttachedFileService {

    /**
     * 添付ファイルを取得する。
     * 
     * @param attachedFileId 添付ファイルID
     * @return 添付ファイルエンティティ
     */
    Optional<AttachedFile> find(Long attachedFileId);

    /**
     * 添付ファイルを検索する。
     * 
     * @return 添付ファイルエンティティのリスト
     */
    List<AttachedFile> search();

    /**
     * 添付ファイルを登録する。
     * 
     * @param file 添付ファイル
     * @param fileName ファイル名
     * @param contentType コンテンツタイプ
     * @return 登録した添付ファイルエンティティ
     */
    AttachedFile create(Path file, String fileName, String contentType);

    /**
     * 添付ファイルを登録する。
     * 
     * @param input 添付ファイル入力ストリーム
     * @param fileName ファイル名
     * @param contentType コンテンツタイプ
     * @return 登録した添付ファイルエンティティ
     */
    AttachedFile create(InputStream input, String fileName, String contentType);

}
