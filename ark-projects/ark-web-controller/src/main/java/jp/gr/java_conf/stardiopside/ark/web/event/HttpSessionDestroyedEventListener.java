package jp.gr.java_conf.stardiopside.ark.web.event;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.springframework.context.ApplicationListener;
import org.springframework.security.web.session.HttpSessionDestroyedEvent;

import jp.gr.java_conf.stardiopside.ark.service.UserService;
import jp.gr.java_conf.stardiopside.ark.service.userdetails.LoginUserDetails;

@Named
@Singleton
public class HttpSessionDestroyedEventListener implements ApplicationListener<HttpSessionDestroyedEvent> {

    @Inject
    private UserService userService;

    @Override
    public void onApplicationEvent(HttpSessionDestroyedEvent event) {
        event.getSecurityContexts().stream().map(context -> context.getAuthentication().getPrincipal())
                .filter(LoginUserDetails.class::isInstance).map(LoginUserDetails.class::cast)
                .forEach(userService::logout);
    }
}
