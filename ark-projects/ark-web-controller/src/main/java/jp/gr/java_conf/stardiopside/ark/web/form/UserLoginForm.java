package jp.gr.java_conf.stardiopside.ark.web.form;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.ToString;

/**
 * ユーザログインフォーム
 */
@Data
@ToString(exclude = "password")
@SuppressWarnings("serial")
public class UserLoginForm implements Serializable {

    /** ユーザ名 */
    @NotNull
    @NotBlank
    private String username;

    /** パスワード */
    @NotNull
    @NotEmpty
    private String password;

}
