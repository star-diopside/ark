package jp.gr.java_conf.stardiopside.ark.web.interceptor;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.WebAttributes;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.FlashMapManager;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.support.RequestContextUtils;

import jp.gr.java_conf.stardiopside.ark.service.UserService;
import jp.gr.java_conf.stardiopside.ark.service.userdetails.LoginUserDetails;
import jp.gr.java_conf.stardiopside.ark.web.exception.DualLoginException;

/**
 * 二重ログインチェックを行うインターセプター
 */
public class DualLoginCheckInterceptor extends HandlerInterceptorAdapter {

    @Inject
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        // 認証情報を取得する。
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // 認証済みの場合、二重ログインチェック処理を行う。
        if (principal instanceof LoginUserDetails && userService.checkDualLogin((LoginUserDetails) principal)) {
            // 二重ログイン例外をフラッシュスコープに設定する。
            DualLoginException exception = new DualLoginException();
            FlashMap flashMap = RequestContextUtils.getOutputFlashMap(request);
            FlashMapManager flashMapManager = RequestContextUtils.getFlashMapManager(request);
            flashMap.put(WebAttributes.AUTHENTICATION_EXCEPTION, exception);
            flashMapManager.saveOutputFlashMap(flashMap, request, response);

            // 二重ログイン例外をスローする。
            throw exception;
        }

        return true;
    }
}
