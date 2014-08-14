package jp.gr.java_conf.star_diopside.spark.web.mvc.auth.form;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.ToString;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * ユーザ登録フォーム
 */
@Data
@ToString(exclude = { "password", "passwordConfirm" })
@SuppressWarnings("serial")
public class UserRegistrationForm implements Serializable {

    /** ユーザ名 */
    @NotNull(message = "{jp.gr.java_conf.star_diopside.spark.validation.username.Required.message}")
    @NotBlank(message = "{jp.gr.java_conf.star_diopside.spark.validation.username.Required.message}")
    @Size(min = 6, max = 50, message = "{jp.gr.java_conf.star_diopside.spark.validation.username.Size.message}")
    private String username;

    /** ニックネーム */
    @NotNull(message = "{jp.gr.java_conf.star_diopside.spark.validation.nickname.Required.message}")
    @NotBlank(message = "{jp.gr.java_conf.star_diopside.spark.validation.nickname.Required.message}")
    @Size(max = 50, message = "jp.gr.java_conf.star_diopside.spark.validation.nickname.Size.message")
    private String nickname;

    /** パスワード */
    @NotNull(message = "{jp.gr.java_conf.star_diopside.spark.validation.password.Required.message}")
    @NotEmpty(message = "{jp.gr.java_conf.star_diopside.spark.validation.password.Required.message}")
    @Size(min = 6, message = "{jp.gr.java_conf.star_diopside.spark.validation.password.Size.message}")
    private String password;

    /** パスワード(確認) */
    @NotNull(message = "{jp.gr.java_conf.star_diopside.spark.validation.passwordConfirm.Required.message}")
    @NotEmpty(message = "{jp.gr.java_conf.star_diopside.spark.validation.passwordConfirm.Required.message}")
    private String passwordConfirm;

}
