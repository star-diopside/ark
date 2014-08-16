package jp.gr.java_conf.star_diopside.spark.batch.logic;

import javax.inject.Inject;

import jp.gr.java_conf.star_diopside.spark.data.entity.User;
import jp.gr.java_conf.star_diopside.spark.service.logic.auth.UserManager;

import org.springframework.batch.item.ItemProcessor;

public class BA01J01S01Processor implements ItemProcessor<User, User> {

    @Inject
    private UserManager userManager;

    @Override
    public User process(User item) throws Exception {
        return userManager.checkValid(item) ? null : item;
    }
}
