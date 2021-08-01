package jp.gr.java_conf.stardiopside.ark.web.interceptor;

import com.google.code.kaptcha.Constants;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * {@link com.google.code.kaptcha.servlet.KaptchaServlet KaptchaServlet}
 * が使用するセッション情報をクリアするインターセプター
 */
public class KaptchaSessionClearInterceptor implements HandlerInterceptor {

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
