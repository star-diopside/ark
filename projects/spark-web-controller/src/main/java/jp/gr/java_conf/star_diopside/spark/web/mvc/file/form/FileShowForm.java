package jp.gr.java_conf.star_diopside.spark.web.mvc.file.form;

import java.io.Serializable;

import jp.gr.java_conf.star_diopside.spark.data.entity.AttachedFile;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@SuppressWarnings("serial")
public class FileShowForm implements Serializable {

    private Long attachedFileId;
    private String fileName;
    private long size;
    private String digest;

    public FileShowForm(AttachedFile file) {
        assign(file);
    }

    public void assign(AttachedFile file) {
        attachedFileId = file.getAttachedFileId();
        fileName = file.getName();
        size = file.getData().length;
        digest = file.getHash();
    }
}
