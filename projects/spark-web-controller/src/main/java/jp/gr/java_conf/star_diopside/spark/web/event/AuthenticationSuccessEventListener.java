package jp.gr.java_conf.star_diopside.spark.web.event;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import jp.gr.java_conf.star_diopside.spark.data.entity.User;
import jp.gr.java_conf.star_diopside.spark.service.logic.auth.UserManager;
import jp.gr.java_conf.star_diopside.spark.service.userdetails.LoginUserDetails;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;

@Named
@Singleton
public class AuthenticationSuccessEventListener implements ApplicationListener<AuthenticationSuccessEvent> {

    @Inject
    private UserManager userManager;

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        // 認証成功時の処理を行う。
        LoginUserDetails loginUser = (LoginUserDetails) event.getAuthentication().getPrincipal();
        User user = userManager.loginSuccess(loginUser);

        // ログイン情報を更新する。
        loginUser.update(loginUser, user);
    }
}
