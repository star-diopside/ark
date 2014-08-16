package jp.gr.java_conf.star_diopside.spark.web.event;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import jp.gr.java_conf.star_diopside.spark.service.logic.auth.UserManager;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Named
@Singleton
public class AuthenticationFailureBadCredentialsEventListener implements
        ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

    @Inject
    private UserManager userManager;

    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
        // ユーザが存在しない場合、何も処理しない。
        if (event.getException() instanceof UsernameNotFoundException) {
            return;
        }

        // 認証エラー時の処理を行う。
        userManager.loginFailure(event.getAuthentication().getName());
    }
}
