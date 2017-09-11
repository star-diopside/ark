package jp.gr.java_conf.star_diopside.ark.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import jp.gr.java_conf.star_diopside.ark.data.entity.AttachedFile;
import jp.gr.java_conf.star_diopside.ark.data.entity.AttachedFileData;
import jp.gr.java_conf.star_diopside.ark.data.repository.AttachedFileDataRepository;
import jp.gr.java_conf.star_diopside.ark.data.repository.AttachedFileRepository;

/**
 * 添付ファイル管理クラス
 */
@Named
@Singleton
public class AttachedFileServiceImpl implements AttachedFileService, InitializingBean {

    @Inject
    private AttachedFileRepository attachedFileRepository;

    @Inject
    private AttachedFileDataRepository attachedFileDataRepository;

    @Value("${divideAttachedFileDataSize}")
    private int divideAttachedFileDataSize;

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.isTrue(divideAttachedFileDataSize > 0, "Parameter 'divideAttachedFileDataSize' is not over 1.");
    }

    @Override
    @Transactional
    public Optional<AttachedFile> find(Long id) {
        return Optional.ofNullable(attachedFileRepository.findOne(id));
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
            byte[] buffer = new byte[divideAttachedFileDataSize];
            long fileSize = 0;
            int count = 0;
            int len;

            while ((len = input.read(buffer)) != -1) {
                digest.update(buffer, 0, len);
                attachedFileDataRepository.save(AttachedFileData.builder().attachedFile(attachedFile).orderBy(++count)
                        .data(Arrays.copyOf(buffer, len)).build());
                fileSize += len;
            }

            attachedFile.setSize(fileSize);
            attachedFile.setHash(Hex.encodeHexString(digest.digest()));
            return attachedFile;

        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
