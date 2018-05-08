package jp.gr.java_conf.stardiopside.ark.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.transaction.Transactional;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;

import jp.gr.java_conf.stardiopside.ark.data.entity.AttachedFile;
import jp.gr.java_conf.stardiopside.ark.data.entity.AttachedFileData;
import jp.gr.java_conf.stardiopside.ark.data.repository.AttachedFileDataRepository;
import jp.gr.java_conf.stardiopside.ark.data.repository.AttachedFileRepository;

/**
 * 添付ファイル管理クラス
 */
@Named
@Singleton
public class AttachedFileServiceImpl implements AttachedFileService {

    private final AttachedFileRepository attachedFileRepository;
    private final AttachedFileDataRepository attachedFileDataRepository;
    private final int divideAttachedFileDataSize;

    public AttachedFileServiceImpl(AttachedFileRepository attachedFileRepository,
            AttachedFileDataRepository attachedFileDataRepository,
            @Value("${application.settings.divide-attached-file-data-size}") int divideAttachedFileDataSize) {
        this.attachedFileRepository = attachedFileRepository;
        this.attachedFileDataRepository = attachedFileDataRepository;
        this.divideAttachedFileDataSize = divideAttachedFileDataSize;
    }

    @Override
    @Transactional
    public Optional<AttachedFile> find(Long id) {
        return attachedFileRepository.findById(id);
    }

    @Override
    @Transactional
    public List<AttachedFile> search() {
        return attachedFileRepository.findAll();
    }

    @Override
    @Transactional
    public AttachedFile create(Path file, String fileName, String contentType) {
        try (InputStream input = Files.newInputStream(file)) {
            return create(input, fileName, contentType);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    @Transactional
    public AttachedFile create(InputStream input, String fileName, String contentType) {
        try {
            AttachedFile attachedFile = new AttachedFile();
            attachedFile.setName(fileName);
            attachedFile.setContentType(contentType);
            attachedFile = attachedFileRepository.save(attachedFile);

            MessageDigest digest = DigestUtils.getSha256Digest();
            long fileSize = 0;

            if (divideAttachedFileDataSize > 0) {
                byte[] buffer = new byte[divideAttachedFileDataSize];
                int count = 0;
                int len;

                while ((len = input.read(buffer)) != -1) {
                    saveFileData(digest, attachedFile, ++count, Arrays.copyOf(buffer, len));
                    fileSize += len;
                }
            } else {
                byte[] data = input.readAllBytes();
                saveFileData(digest, attachedFile, 1, data);
                fileSize = data.length;
            }

            attachedFile.setSize(fileSize);
            attachedFile.setHash(Hex.encodeHexString(digest.digest()));
            return attachedFile;

        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private void saveFileData(MessageDigest digest, AttachedFile attachedFile, int order, byte[] data) {
        digest.update(data);
        attachedFileDataRepository
                .save(AttachedFileData.builder().attachedFile(attachedFile).orderBy(order).data(data).build());
    }
}
