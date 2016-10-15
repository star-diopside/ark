package jp.gr.java_conf.star_diopside.spark.web.exception;

import org.springframework.security.authentication.AccountStatusException;

/**
 * 二重ログインが発生したことを示す例外クラス
 */
@SuppressWarnings("serial")
public class DualLoginException extends AccountStatusException {

    public DualLoginException() {
        this("Dual login exception has occurred.");
    }

    public DualLoginException(Throwable t) {
        this("Dual login exception has occurred.", t);
    }

    public DualLoginException(String msg) {
        super(msg);
    }

    public DualLoginException(String msg, Throwable t) {
        super(msg, t);
    }
}
