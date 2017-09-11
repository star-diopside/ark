package jp.gr.java_conf.star_diopside.ark.web.form;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

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
