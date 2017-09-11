package jp.gr.java_conf.star_diopside.ark.web.event;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;

import jp.gr.java_conf.star_diopside.ark.data.entity.User;
import jp.gr.java_conf.star_diopside.ark.service.UserService;
import jp.gr.java_conf.star_diopside.ark.service.userdetails.LoginUserDetails;

@Named
@Singleton
public class AuthenticationSuccessEventListener implements ApplicationListener<AuthenticationSuccessEvent> {

    @Inject
    private UserService userService;

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        // 認証成功時の処理を行う。
        LoginUserDetails loginUser = (LoginUserDetails) event.getAuthentication().getPrincipal();
        User user = userService.loginSuccess(loginUser);

        // ログイン情報を更新する。
        loginUser.update(loginUser, user);
    }
}
