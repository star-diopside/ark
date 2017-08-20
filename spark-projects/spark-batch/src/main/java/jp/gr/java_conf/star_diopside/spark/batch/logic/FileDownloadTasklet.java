package jp.gr.java_conf.star_diopside.spark.batch.logic;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.core.io.Resource;

import lombok.Setter;

/**
 * ファイルダウンロード処理を行うTaskletクラス
 */
public class FileDownloadTasklet implements Tasklet {

    /** ダウンロードURL */
    @Setter
    private String uri;

    /** 保存先ファイル */
    @Setter
    private Resource saveFile;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        HttpClient client = HttpClients.createDefault();
        HttpResponse response = client.execute(new HttpGet(uri));
        HttpEntity entity = response.getEntity();

        try (InputStream content = entity.getContent()) {
            Files.copy(content, saveFile.getFile().toPath(), StandardCopyOption.REPLACE_EXISTING);
        }

        return RepeatStatus.FINISHED;
    }
}
