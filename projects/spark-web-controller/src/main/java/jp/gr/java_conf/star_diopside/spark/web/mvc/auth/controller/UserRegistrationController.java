package jp.gr.java_conf.star_diopside.spark.web.mvc.auth.controller;

import jp.gr.java_conf.star_diopside.spark.web.mvc.auth.form.UserRegistrationForm;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * ユーザ登録コントローラ
 */
@Controller
@RequestMapping("auth/user-registration")
public class UserRegistrationController {

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
}
