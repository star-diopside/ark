package jp.gr.java_conf.star_diopside.spark.web.servlet;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.gr.java_conf.star_diopside.spark.service.logic.auth.UserManager;
import jp.gr.java_conf.star_diopside.spark.service.userdetails.LoginUserDetails;
import jp.gr.java_conf.star_diopside.spark.web.exception.DualLoginException;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.WebAttributes;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.FlashMapManager;

/**
 * 二重ログインチェックを行うサーブレットフィルタ
 */
public class DualLoginCheckFilter extends OncePerRequestFilter {

    @Inject
    private UserManager userManager;

    @Inject
    @Named(DispatcherServlet.FLASH_MAP_MANAGER_BEAN_NAME)
    private FlashMapManager flashMapManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 認証情報を取得する。
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // 認証済みの場合、二重ログインチェック処理を行う。
        if (principal instanceof LoginUserDetails && userManager.checkDualLogin((LoginUserDetails) principal)) {
            // 二重ログイン例外をフラッシュスコープに設定する。
            DualLoginException exception = new DualLoginException();
            FlashMap flashMap = new FlashMap();
            flashMap.put(WebAttributes.AUTHENTICATION_EXCEPTION, exception);
            flashMapManager.saveOutputFlashMap(flashMap, request, response);

            // 二重ログイン例外をスローする。
            throw exception;
        }

        filterChain.doFilter(request, response);
    }
}
