package jp.gr.java_conf.stardiopside.ark.service.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.ToString;

/**
 * ユーザ情報クラス
 */
@Data
@ToString(exclude = "password")
@SuppressWarnings("serial")
public class UserDto implements Serializable {

    /** ユーザ名 */
    @NotNull
    @NotBlank
    @Size(min = 6, max = 50)
    private String username;

    /** 表示名 */
    @NotNull
    @NotBlank
    @Size(max = 50, message = "{jp.gr.java_conf.stardiopside.ark.validation.constraints.Size.max.message}")
    private String displayName;

    /** パスワード */
    @NotNull
    @NotEmpty
    @Size(min = 6, message = "{jp.gr.java_conf.stardiopside.ark.validation.constraints.Size.min.message}")
    private String password;

}
