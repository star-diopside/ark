package jp.gr.java_conf.star_diopside.spark.service.bean;

import jp.gr.java_conf.star_diopside.spark.commons.core.logging.Loggable;
import jp.gr.java_conf.star_diopside.spark.commons.core.logging.LoggingSetting;
import jp.gr.java_conf.star_diopside.spark.commons.core.logging.LoggingType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * パスワードをログに残さないためのラッパークラス
 */
@AllArgsConstructor
@Data
@ToString(exclude = "password")
public class PasswordWrapper implements Loggable {

    /** パスワード */
    @LoggingSetting(LoggingType.PROTECT)
    private String password;

    /**
     * インスタンスを生成する。
     * 
     * @param password パスワード
     * @return 新たに生成した {@link PasswordWrapper} インスタンス
     */
    public static PasswordWrapper of(String password) {
        return new PasswordWrapper(password);
    }
}
