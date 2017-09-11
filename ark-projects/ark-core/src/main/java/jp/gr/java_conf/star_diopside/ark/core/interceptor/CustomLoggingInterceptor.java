package jp.gr.java_conf.star_diopside.ark.core.interceptor;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.slf4j.MDC;

import jp.gr.java_conf.star_diopside.ark.core.exception.ApplicationException;
import jp.gr.java_conf.star_diopside.silver.commons.core.interceptor.LoggingObjectDetailsInterceptor;

@SuppressWarnings("serial")
public class CustomLoggingInterceptor extends LoggingObjectDetailsInterceptor {

    /** クラス名を格納するMDCキー */
    private static final String MDC_CLASS_NAME = "className";

    /** メソッド名を格納するMDCキー */
    private static final String MDC_METHOD_NAME = "methodName";

    @Override
    protected Object invokeUnderTrace(MethodInvocation invocation, Log logger) throws Throwable {

        // MDCにメソッド呼び出し情報をセットする。
        String beforeClassName = MDC.get(MDC_CLASS_NAME);
        String beforeMethodName = MDC.get(MDC_METHOD_NAME);
        MDC.put(MDC_CLASS_NAME, getClassForLogging(invocation.getThis()).getName());
        MDC.put(MDC_METHOD_NAME, invocation.getMethod().getName());

        try {
            return super.invokeUnderTrace(invocation, logger);

        } finally {
            // MDCのメソッド呼び出し情報を元に戻す。
            if (beforeClassName == null) {
                MDC.remove(MDC_CLASS_NAME);
            } else {
                MDC.put(MDC_CLASS_NAME, beforeClassName);
            }
            if (beforeMethodName == null) {
                MDC.remove(MDC_METHOD_NAME);
            } else {
                MDC.put(MDC_METHOD_NAME, beforeMethodName);
            }
        }
    }

    @Override
    protected void writeToExceptionLog(Log logger, Object message, Throwable t) {
        if (t instanceof ApplicationException) {
            logger.info(message);
        } else {
            super.writeToExceptionLog(logger, message, t);
        }
    }

    @Override
    protected boolean isExceptionLogEnabled(Log logger, Throwable t) {
        if (t instanceof ApplicationException) {
            return logger.isInfoEnabled();
        } else {
            return super.isExceptionLogEnabled(logger, t);
        }
    }
}
