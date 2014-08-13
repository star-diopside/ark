package jp.gr.java_conf.star_diopside.spark.web.mvc.auth.form;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.ToString;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

@Data
@ToString(exclude = "password")
@SuppressWarnings("serial")
public class LoginForm implements Serializable {

    /** ユーザ名 */
    @NotNull(message = "{jp.gr.java_conf.star_diopside.spark.validation.username.Required.message}")
    @NotBlank(message = "{jp.gr.java_conf.star_diopside.spark.validation.username.Required.message}")
    private String username;

    /** パスワード */
    @NotNull(message = "{jp.gr.java_conf.star_diopside.spark.validation.password.Required.message}")
    @NotEmpty(message = "{jp.gr.java_conf.star_diopside.spark.validation.password.Required.message}")
    private String password;

}
