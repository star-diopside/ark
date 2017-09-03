package jp.gr.java_conf.star_diopside.spark.batch.tasklet;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.core.io.Resource;

import lombok.Setter;

/**
 * ZIPファイルの解凍を行うTaskletクラス
 */
public class UnarchiveZipFileTasklet implements Tasklet {

    /** 郵便番号ZIPファイル */
    @Setter
    private Resource resource;

    /** ファイル名のデコードに使用する文字セット */
    @Setter
    private String charset;

    /** ファイル解凍先ディレクトリ */
    @Setter
    private String unarchiveDirectory;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        try (ZipInputStream zis = new ZipInputStream(resource.getInputStream(), Charset.forName(charset))) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                Path file = Paths.get(unarchiveDirectory, entry.getName());

                if (entry.isDirectory()) {
                    Files.createDirectories(file);
                } else {
                    Files.createDirectories(file.getParent());
                    Files.copy(zis, file);
                }

                zis.closeEntry();
            }
        }

        return RepeatStatus.FINISHED;
    }

}
