package jp.gr.java_conf.star_diopside.spark.core.exception;

/**
 * システム例外クラス
 */
@SuppressWarnings("serial")
public class SystemException extends RuntimeException {

    /**
     * コンストラクタ
     */
    public SystemException() {
        super();
    }

    /**
     * コンストラクタ
     * 
     * @param message 例外メッセージ
     */
    public SystemException(String message) {
        super(message);
    }

    /**
     * コンストラクタ
     * 
     * @param message 例外メッセージ
     * @param cause 原因例外
     */
    public SystemException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * コンストラクタ
     * 
     * @param cause 原因例外
     */
    public SystemException(Throwable cause) {
        super(cause);
    }
}
