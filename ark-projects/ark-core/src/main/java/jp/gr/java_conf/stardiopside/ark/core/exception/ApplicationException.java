package jp.gr.java_conf.stardiopside.ark.core.exception;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import lombok.Getter;

/**
 * 業務例外クラス
 */
@Getter
@SuppressWarnings("serial")
public class ApplicationException extends RuntimeException {

    private boolean resource = false;
    private List<?> arguments = Collections.emptyList();

    /**
     * コンストラクタ
     */
    public ApplicationException() {
        super();
    }

    /**
     * コンストラクタ
     * 
     * @param message 例外メッセージ
     */
    public ApplicationException(String message) {
        super(message);
    }

    /**
     * コンストラクタ
     * 
     * @param message 例外メッセージ
     * @param cause 原因例外
     */
    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * コンストラクタ
     * 
     * @param cause 原因例外
     */
    public ApplicationException(Throwable cause) {
        super(cause);
    }

    /**
     * コンストラクタ
     * 
     * @param message 例外メッセージ
     * @param resource messageをリソースキーとする場合はtrue、メッセージ文字列の場合はfalse
     * @param arguments messageにリソースキーを指定した場合の埋め字配列
     */
    public ApplicationException(String message, boolean resource, Object... arguments) {
        super(message);
        this.resource = resource;
        this.arguments = Collections.unmodifiableList(Arrays.asList(arguments));
    }

    /**
     * コンストラクタ
     * 
     * @param message 例外メッセージ
     * @param cause 原因例外
     * @param resource messageをリソースキーとする場合はtrue、メッセージ文字列の場合はfalse
     * @param arguments messageにリソースキーを指定した場合の埋め字配列
     */
    public ApplicationException(String message, Throwable cause, boolean resource, Object... arguments) {
        super(message, cause);
        this.resource = resource;
        this.arguments = Collections.unmodifiableList(Arrays.asList(arguments));
    }
}
