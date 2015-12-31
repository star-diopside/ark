package jp.gr.java_conf.star_diopside.spark.service.logic.file;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.springframework.transaction.annotation.Transactional;

import jp.gr.java_conf.star_diopside.spark.data.entity.AttachedFile;
import jp.gr.java_conf.star_diopside.spark.data.repository.AttachedFileRepository;

/**
 * 添付ファイル管理クラス
 */
@Named
@Singleton
public class AttachedFileManagerImpl implements AttachedFileManager {

    @Inject
    private AttachedFileRepository attachedFileRepository;

    @Override
    @Transactional
    public Optional<AttachedFile> find(Long attachedFileId) {
        return Optional.ofNullable(attachedFileRepository.findOne(attachedFileId));
    }

    @Override
    @Transactional
    public AttachedFile create(Path file, String fileName) {
        try {
            AttachedFile attachedFile = new AttachedFile();
            attachedFile.setName(fileName);
            attachedFile.setData(Files.readAllBytes(file));
            return attachedFileRepository.save(attachedFile);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
