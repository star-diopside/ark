package jp.gr.java_conf.stardiopside.ark.web.event;

import javax.inject.Named;
import javax.inject.Singleton;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import jp.gr.java_conf.stardiopside.ark.service.UserService;

@Named
@Singleton
public class AuthenticationFailureBadCredentialsEventListener
        implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

    private final UserService userService;

    public AuthenticationFailureBadCredentialsEventListener(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
        // ユーザが存在しない場合、何も処理しない。
        if (event.getException() instanceof UsernameNotFoundException) {
            return;
        }

        // 認証エラー時の処理を行う。
        userService.loginFailure(event.getAuthentication().getName());
    }
}
