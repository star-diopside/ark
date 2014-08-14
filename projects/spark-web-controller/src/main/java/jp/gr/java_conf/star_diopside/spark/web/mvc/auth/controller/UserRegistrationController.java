package jp.gr.java_conf.star_diopside.spark.web.mvc.auth.controller;

import javax.inject.Inject;
import javax.validation.Valid;

import jp.gr.java_conf.star_diopside.spark.core.exception.ApplicationException;
import jp.gr.java_conf.star_diopside.spark.service.logic.auth.UserManager;
import jp.gr.java_conf.star_diopside.spark.support.util.ExceptionUtils;
import jp.gr.java_conf.star_diopside.spark.web.mvc.auth.form.UserLoginForm;
import jp.gr.java_conf.star_diopside.spark.web.mvc.auth.form.UserRegistrationForm;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * ユーザ登録コントローラ
 */
@Controller
@RequestMapping("auth/user-registration")
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
    public String register(@Valid UserRegistrationForm form, Errors errors, RedirectAttributes attr) {

        if (errors.hasErrors()) {
            return "auth/user-registration";
        }

        // パスワードの一致チェックを行う。
        if (!StringUtils.equals(form.getPassword(), form.getPasswordConfirm())) {
            errors.reject("error.NotMatchPasswordConfirm");
            return "auth/user-registration";
        }

        // ユーザ登録を行う。
        try {
            userManager.createUser(form.getUsername(), form.getNickname(), form.getPassword());
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
