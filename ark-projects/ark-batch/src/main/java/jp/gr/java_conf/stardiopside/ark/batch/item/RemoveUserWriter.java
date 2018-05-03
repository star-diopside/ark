package jp.gr.java_conf.stardiopside.ark.batch.item;

import java.util.List;

import org.springframework.batch.item.ItemWriter;

import jp.gr.java_conf.stardiopside.ark.data.entity.User;
import jp.gr.java_conf.stardiopside.ark.service.UserService;

public class RemoveUserWriter implements ItemWriter<User> {

    private final UserService userService;

    public RemoveUserWriter(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void write(List<? extends User> items) throws Exception {
        items.forEach(userService::removeUser);
    }
}
