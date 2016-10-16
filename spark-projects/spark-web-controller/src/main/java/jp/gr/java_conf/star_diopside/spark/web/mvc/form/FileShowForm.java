package jp.gr.java_conf.star_diopside.spark.web.mvc.form;

import java.io.Serializable;

import jp.gr.java_conf.star_diopside.spark.data.entity.AttachedFile;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@SuppressWarnings("serial")
public class FileShowForm implements Serializable {

    private Long id;
    private String fileName;
    private long size;
    private String digest;

    public FileShowForm(AttachedFile file) {
        assign(file);
    }

    public void assign(AttachedFile file) {
        id = file.getId();
        fileName = file.getName();
        size = file.getSize();
        digest = file.getHash();
    }
}
