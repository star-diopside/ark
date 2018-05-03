package jp.gr.java_conf.stardiopside.ark.service.userdetails;

import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;

import jp.gr.java_conf.stardiopside.ark.service.UserService;

/**
 * 認証前ユーザ情報チェック処理クラス
 */
public class BeforeLoginUserDetailsChecker implements UserDetailsChecker {

    private final UserService userService;
    private final MessageSourceAccessor messages;

    public BeforeLoginUserDetailsChecker(UserService userService, MessageSourceAccessor messages) {
        this.userService = userService;
        this.messages = messages;
    }

    @Override
    public void check(UserDetails toCheck) {
        if (userService.removeInvalidUser((LoginUserDetails) toCheck)) {
            throw new AccountExpiredException(messages.getMessage("error.userInvalid"));
        }
    }
}
