package jp.gr.java_conf.star_diopside.spark.core.interceptor;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.slf4j.MDC;

@SuppressWarnings("serial")
public class CustomLoggingInterceptor extends LoggingInterceptor {

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
}
