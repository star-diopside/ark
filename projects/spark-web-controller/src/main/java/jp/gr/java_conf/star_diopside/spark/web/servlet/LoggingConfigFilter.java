package jp.gr.java_conf.star_diopside.spark.web.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.MDC;

/**
 * ログ出力に関する設定を行うサーブレットフィルタ
 */
public class LoggingConfigFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // MDCをクリアする。
        MDC.clear();

        // MDCにログ出力情報をセットする。
        HttpSession session = ((HttpServletRequest) request).getSession(false);

        if (session != null) {
            MDC.put("sessionId", session.getId());
        }
        MDC.put("remoteAddr", request.getRemoteAddr());

        chain.doFilter(request, response);
    }
}
