package jp.gr.java_conf.star_diopside.ark.batch.item;

import javax.inject.Inject;

import org.springframework.batch.item.ItemProcessor;

import jp.gr.java_conf.star_diopside.ark.data.entity.User;
import jp.gr.java_conf.star_diopside.ark.service.UserService;

public class InvalidUserFilter implements ItemProcessor<User, User> {

    @Inject
    private UserService userService;

    @Override
    public User process(User item) throws Exception {
        return userService.checkValid(item) ? null : item;
    }
}
