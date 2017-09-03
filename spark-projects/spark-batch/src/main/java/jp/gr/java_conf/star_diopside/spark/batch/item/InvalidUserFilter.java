package jp.gr.java_conf.star_diopside.spark.batch.item;

import javax.inject.Inject;

import org.springframework.batch.item.ItemProcessor;

import jp.gr.java_conf.star_diopside.spark.data.entity.User;
import jp.gr.java_conf.star_diopside.spark.service.UserService;

public class InvalidUserFilter implements ItemProcessor<User, User> {

    @Inject
    private UserService userService;

    @Override
    public User process(User item) throws Exception {
        return userService.checkValid(item) ? null : item;
    }
}
