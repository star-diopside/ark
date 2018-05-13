package jp.gr.java_conf.stardiopside.ark.web.form.anonymous;

import java.io.Serializable;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import jp.gr.java_conf.stardiopside.ark.service.dto.UserDto;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(exclude = "passwordConfirm")
@SuppressWarnings("serial")
public class TemporaryUserForm implements Serializable {

    /** ユーザ情報 */
    @NotNull
    @Valid
    private UserDto userDto;

    /** パスワード(確認) */
    @NotNull
    @NotEmpty
    private String passwordConfirm;

    /** キャプチャ */
    @NotNull
    @NotEmpty
    private String captcha;

}
