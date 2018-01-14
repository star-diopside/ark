package jp.gr.java_conf.stardiopside.ark.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.google.code.kaptcha.Constants;

/**
 * {@link com.google.code.kaptcha.servlet.KaptchaServlet KaptchaServlet}
 * が使用するセッション情報をクリアするインターセプター
 */
public class KaptchaSessionClearInterceptor extends HandlerInterceptorAdapter {

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(Constants.KAPTCHA_SESSION_KEY);
            session.removeAttribute(Constants.KAPTCHA_SESSION_DATE);
        }
    }
}
