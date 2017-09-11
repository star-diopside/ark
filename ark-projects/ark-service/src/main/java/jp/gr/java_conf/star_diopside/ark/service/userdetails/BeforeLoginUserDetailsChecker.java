package jp.gr.java_conf.star_diopside.ark.service.userdetails;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;

import jp.gr.java_conf.star_diopside.ark.service.UserService;

/**
 * 認証前ユーザ情報チェック処理クラス
 */
public class BeforeLoginUserDetailsChecker implements UserDetailsChecker {

    @Inject
    private UserService userService;

    @Inject
    @Named("messages")
    private MessageSourceAccessor messages;

    @Override
    public void check(UserDetails toCheck) {
        if (userService.removeInvalidUser((LoginUserDetails) toCheck)) {
            throw new AccountExpiredException(messages.getMessage("error.userInvalid"));
        }
    }
}
