package jp.gr.java_conf.star_diopside.spark.web.mvc.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.code.kaptcha.Constants;

import jp.gr.java_conf.star_diopside.spark.core.exception.ApplicationException;
import jp.gr.java_conf.star_diopside.spark.service.bean.PasswordWrapper;
import jp.gr.java_conf.star_diopside.spark.service.logic.auth.UserManager;
import jp.gr.java_conf.star_diopside.spark.support.util.ExceptionUtils;
import jp.gr.java_conf.star_diopside.spark.web.mvc.form.UserLoginForm;
import jp.gr.java_conf.star_diopside.spark.web.mvc.form.UserRegistrationForm;

/**
 * ユーザ登録コントローラ
 */
@Controller
@RequestMapping("/auth/user-registration")
public class UserRegistrationController {

    @Inject
    private UserManager userManager;

    /**
     * 画面表示処理を行う。
     * 
     * @param form フォーム情報
     * @return 処理結果
     */
    @RequestMapping(method = RequestMethod.GET)
    public String show(UserRegistrationForm form) {
        return "auth/user-registration";
    }

    /**
     * ユーザ登録処理を行う。
     * 
     * @param form フォーム情報
     * @param errors エラー情報
     * @param attr リダイレクト属性情報
     * @return 処理結果
     */
    @RequestMapping(method = RequestMethod.POST, params = "register")
    public String register(@Valid UserRegistrationForm form, Errors errors, HttpSession session, RedirectAttributes attr) {

        if (errors.hasErrors() || !form.validate(errors)) {
            return "auth/user-registration";
        }

        // キャプチャ文字の一致チェックを行う。
        String captcha = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
        if (!StringUtils.equals(captcha, form.getCaptcha())) {
            errors.rejectValue("captcha", "error.NotMatchCaptcha");
            return "auth/user-registration";
        }

        // ユーザ登録を行う。
        try {
            userManager.createUser(form.getUsername(), form.getNickname(), PasswordWrapper.of(form.getPassword()));
        } catch (ApplicationException e) {
            ExceptionUtils.reject(errors, e);
            return "auth/user-registration";
        }

        // ユーザログイン画面に遷移する。
        UserLoginForm nextForm = new UserLoginForm();
        nextForm.setUsername(form.getUsername());
        attr.addFlashAttribute(nextForm);

        return "redirect:/auth/user-login";
    }
}
