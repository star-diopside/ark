package jp.gr.java_conf.star_diopside.spark.web.event;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import jp.gr.java_conf.star_diopside.spark.service.logic.auth.UserManager;
import jp.gr.java_conf.star_diopside.spark.service.userdetails.LoginUserDetails;

import org.springframework.context.ApplicationListener;
import org.springframework.security.web.session.HttpSessionDestroyedEvent;

@Named
@Singleton
public class HttpSessionDestroyedEventListener implements ApplicationListener<HttpSessionDestroyedEvent> {

    @Inject
    private UserManager userManager;

    @Override
    public void onApplicationEvent(HttpSessionDestroyedEvent event) {
        event.getSecurityContexts().stream().map(context -> context.getAuthentication().getPrincipal())
                .filter(principal -> principal instanceof LoginUserDetails)
                .forEach(principal -> userManager.logout((LoginUserDetails) principal));
    }
}
