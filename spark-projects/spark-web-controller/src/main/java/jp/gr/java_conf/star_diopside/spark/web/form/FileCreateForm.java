package jp.gr.java_conf.star_diopside.spark.web.form;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import jp.gr.java_conf.star_diopside.silver.commons.web.validation.constraints.multipart.MultipartFileNotEmpty;
import jp.gr.java_conf.star_diopside.silver.commons.web.validation.constraints.multipart.MultipartFileRequired;
import lombok.Data;

@Data
@SuppressWarnings("serial")
public class FileCreateForm implements Serializable {

    @NotNull
    @MultipartFileRequired
    @MultipartFileNotEmpty
    private MultipartFile file;
}
