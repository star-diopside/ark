package jp.gr.java_conf.stardiopside.ark.web.form.anonymous;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.validation.Errors;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(exclude = { "password", "passwordConfirm" })
@SuppressWarnings("serial")
public class TemporaryUserForm implements Serializable {

    /** ユーザ名 */
    @NotNull
    @NotBlank
    @Size(min = 6, max = 50)
    private String username;

    /** ニックネーム */
    @NotNull
    @NotBlank
    @Size(max = 50, message = "{jp.gr.java_conf.stardiopside.ark.validation.constraints.Size.max.message}")
    private String nickname;

    /** パスワード */
    @NotNull
    @NotEmpty
    @Size(min = 6, message = "{jp.gr.java_conf.stardiopside.ark.validation.constraints.Size.min.message}")
    private String password;

    /** パスワード(確認) */
    @NotNull
    @NotEmpty
    private String passwordConfirm;

    /** キャプチャ */
    @NotNull
    @NotEmpty
    private String captcha;

    /**
     * 項目関連チェックを行う。
     * 
     * @param errors エラー情報
     * @return エラーがない場合はtrue、エラーがある場合はfalse
     */
    public boolean validate(Errors errors) {

        // パスワードの一致チェックを行う。
        if (!StringUtils.equals(password, passwordConfirm)) {
            errors.reject("error.notMatchPasswordConfirm");
            return false;
        }

        return true;
    }
}
