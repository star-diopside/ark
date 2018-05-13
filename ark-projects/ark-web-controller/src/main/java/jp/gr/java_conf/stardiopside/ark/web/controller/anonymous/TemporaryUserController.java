package jp.gr.java_conf.stardiopside.ark.web.controller.anonymous;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.code.kaptcha.Constants;

import jp.gr.java_conf.stardiopside.ark.core.exception.ApplicationException;
import jp.gr.java_conf.stardiopside.ark.service.UserService;
import jp.gr.java_conf.stardiopside.ark.support.util.ExceptionUtils;
import jp.gr.java_conf.stardiopside.ark.web.form.UserLoginForm;
import jp.gr.java_conf.stardiopside.ark.web.form.anonymous.TemporaryUserForm;

@Controller
@RequestMapping("/anonymous/temporary-users")
public class TemporaryUserController {

    private final Validator temporaryUserValidator;
    private final UserService userService;

    public TemporaryUserController(@Qualifier("temporaryUserValidator") Validator temporaryUserValidator,
            UserService userService) {
        this.temporaryUserValidator = temporaryUserValidator;
        this.userService = userService;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(temporaryUserValidator);
    }

    @GetMapping("/create")
    public String create(TemporaryUserForm form) {
        return "anonymous/temporary-users/create";
    }

    @PostMapping
    public String save(@Valid TemporaryUserForm form, BindingResult result, HttpSession session,
            RedirectAttributes attr) {

        if (result.hasErrors()) {
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
            userService.createTemporaryUser(form.getUserDto());
        } catch (ApplicationException e) {
            ExceptionUtils.reject(result, e);
            return "anonymous/temporary-users/create";
        }

        // ユーザログイン画面に遷移する。
        UserLoginForm nextForm = new UserLoginForm();
        nextForm.setUsername(form.getUserDto().getUsername());
        attr.addFlashAttribute(nextForm);

        return "redirect:/authentication/create";
    }
}
