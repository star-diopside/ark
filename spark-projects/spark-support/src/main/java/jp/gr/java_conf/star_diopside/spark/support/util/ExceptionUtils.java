package jp.gr.java_conf.star_diopside.spark.support.util;

import jp.gr.java_conf.star_diopside.spark.core.exception.ApplicationException;

import org.springframework.validation.Errors;

/**
 * 例外処理ユーティリティクラス
 */
public final class ExceptionUtils {

    private ExceptionUtils() {
    }

    /**
     * 業務例外メッセージをエラー情報に設定する。
     * 
     * @param errors エラー情報
     * @param ex 業務例外
     */
    public static void reject(Errors errors, ApplicationException ex) {
        if (ex.isResource()) {
            errors.reject(ex.getMessage(), ex.getArguments().toArray(), null);
        } else {
            errors.reject(null, ex.getMessage());
        }
    }
}
