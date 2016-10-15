package jp.gr.java_conf.star_diopside.spark.service.userdetails;

import javax.inject.Inject;
import javax.inject.Named;

import jp.gr.java_conf.star_diopside.spark.service.logic.auth.UserManager;

import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;

/**
 * 認証前ユーザ情報チェック処理クラス
 */
public class BeforeLoginUserDetailsChecker implements UserDetailsChecker {

    @Inject
    private UserManager userManager;

    @Inject
    @Named("messages")
    private MessageSourceAccessor messages;

    @Override
    public void check(UserDetails toCheck) {
        if (userManager.removeInvalidUser((LoginUserDetails) toCheck)) {
            throw new AccountExpiredException(messages.getMessage("error.UserInvalid"));
        }
    }
}
