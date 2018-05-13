package jp.gr.java_conf.stardiopside.ark.web.validation.anonymous;

import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import jp.gr.java_conf.stardiopside.ark.web.form.anonymous.TemporaryUserForm;

@Named
@Singleton
public class TemporaryUserValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return TemporaryUserForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (errors.hasErrors()) {
            return;
        }

        TemporaryUserForm form = (TemporaryUserForm) target;

        // パスワードの一致チェックを行う。
        if (!StringUtils.equals(form.getUserDto().getPassword(), form.getPasswordConfirm())) {
            errors.reject("error.notMatchPasswordConfirm");
        }
    }
}
