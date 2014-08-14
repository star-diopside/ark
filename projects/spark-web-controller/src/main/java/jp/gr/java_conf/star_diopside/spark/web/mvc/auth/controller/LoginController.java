package jp.gr.java_conf.star_diopside.spark.web.mvc.auth.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import jp.gr.java_conf.star_diopside.spark.web.mvc.auth.form.LoginForm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * ログインコントローラ
 */
@Controller
@RequestMapping("auth/login")
public class LoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
    private static final Map<String, String> EXCEPTION_MAP;

    static {
        HashMap<String, String> map = new HashMap<>();
        map.put(BadCredentialsException.class.getName(), "Error.BadCredential");
        map.put(UsernameNotFoundException.class.getName(), "Error.BadCredential");
        map.put(AccountExpiredException.class.getName(), "Error.UserInvalid");
        EXCEPTION_MAP = Collections.unmodifiableMap(map);
    }

    /**
     * 画面表示処理を行う。
     * 
     * @param form フォーム情報
     * @param model モデルマップ
     * @param errors エラー情報
     * @return 処理結果
     */
    @RequestMapping(method = RequestMethod.GET)
    public String show(LoginForm form, ModelMap model, Errors errors) {

        // 認証エラーメッセージを取得する。
        Exception exception = (Exception) model.get(WebAttributes.AUTHENTICATION_EXCEPTION);

        if (exception != null) {
            String errorCode = EXCEPTION_MAP.get(exception.getClass().getName());
            if (errorCode != null) {
                errors.reject(errorCode);
            } else {
                LOGGER.debug(exception.getMessage(), exception);
                errors.reject("Error.Authentication");
            }
        }

        return "auth/login";
    }

    /**
     * 認証エラー処理を行う。
     * 
     * @param session セッション情報
     * @param attr リダイレクト属性情報
     * @return 処理結果
     */
    @RequestMapping(value = "failure", method = RequestMethod.GET)
    public String failure(HttpSession session, RedirectAttributes attr) {

        // 認証エラー例外を取得する。
        Exception exception = (Exception) session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);

        if (exception != null) {
            attr.addFlashAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, exception);
        }

        return "redirect:/auth/login";
    }

    /**
     * ログイン処理を行う。
     * 
     * @param form フォーム情報
     * @param errors エラー情報
     * @return 処理結果
     */
    @RequestMapping(method = RequestMethod.POST, params = "login")
    public String login(@Valid LoginForm form, Errors errors) {
        if (errors.hasErrors()) {
            return "auth/login";
        } else {
            return "forward:/j_spring_security_check";
        }
    }
}
