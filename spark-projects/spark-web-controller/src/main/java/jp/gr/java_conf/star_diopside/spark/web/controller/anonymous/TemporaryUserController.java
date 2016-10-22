package jp.gr.java_conf.star_diopside.spark.web.controller.anonymous;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.code.kaptcha.Constants;

import jp.gr.java_conf.star_diopside.spark.core.exception.ApplicationException;
import jp.gr.java_conf.star_diopside.spark.service.UserService;
import jp.gr.java_conf.star_diopside.spark.support.util.ExceptionUtils;
import jp.gr.java_conf.star_diopside.spark.web.form.UserLoginForm;
import jp.gr.java_conf.star_diopside.spark.web.form.anonymous.TemporaryUserForm;

@Controller
@RequestMapping("/anonymous/temporary-users")
public class TemporaryUserController {

    @Inject
    private UserService userService;

    @GetMapping("/create")
    public String create(TemporaryUserForm form) {
        return "anonymous/temporary-users/create";
    }

    @PostMapping
    public String save(@Valid TemporaryUserForm form, BindingResult result, HttpSession session,
            RedirectAttributes attr) {

        if (result.hasErrors() || !form.validate(result)) {
            return "anonymous/temporary-users/create";
        }

        // キャプチャ文字の一致チェックを行う。
        String captcha = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
        if (!StringUtils.equals(captcha, form.getCaptcha())) {
            result.rejectValue("captcha", "error.notMatchCaptcha");
            return "anonymous/temporary-users/create";
        }

        // ユーザ登録を行う。
        try {
            userService.createUser(form.getUsername(), form.getNickname(), form::getPassword);
        } catch (ApplicationException e) {
            ExceptionUtils.reject(result, e);
            return "anonymous/temporary-users/create";
        }

        // ユーザログイン画面に遷移する。
        UserLoginForm nextForm = new UserLoginForm();
        nextForm.setUsername(form.getUsername());
        attr.addFlashAttribute(nextForm);

        return "redirect:/authentication/create";
    }
}
