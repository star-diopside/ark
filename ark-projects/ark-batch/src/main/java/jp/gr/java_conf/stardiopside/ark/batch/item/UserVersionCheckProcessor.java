package jp.gr.java_conf.stardiopside.ark.batch.item;

import javax.inject.Inject;

import org.springframework.batch.item.ItemProcessor;

import jp.gr.java_conf.stardiopside.ark.data.entity.User;
import jp.gr.java_conf.stardiopside.ark.data.repository.UserRepository;

public class UserVersionCheckProcessor implements ItemProcessor<User, User> {

    @Inject
    private UserRepository userRepository;

    @Override
    public User process(User item) throws Exception {
        return userRepository.findById(item.getUserId())
                .filter(user -> user.getVersion() == item.getVersion())
                .orElse(null);
    }
}
