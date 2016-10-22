package jp.gr.java_conf.star_diopside.spark.batch.logic;

import java.util.List;

import javax.inject.Inject;

import org.springframework.batch.item.ItemWriter;

import jp.gr.java_conf.star_diopside.spark.data.entity.User;
import jp.gr.java_conf.star_diopside.spark.service.UserService;

public class BA01J01S02Writer implements ItemWriter<User> {

    @Inject
    private UserService userService;

    @Override
    public void write(List<? extends User> items) throws Exception {
        items.forEach(userService::removeUser);
    }
}
