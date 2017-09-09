package jp.gr.java_conf.star_diopside.spark.batch.item;

import javax.inject.Inject;

import org.springframework.batch.item.ItemProcessor;

import jp.gr.java_conf.star_diopside.spark.data.entity.User;
import jp.gr.java_conf.star_diopside.spark.data.repository.UserRepository;

public class UserVersionCheckProcessor implements ItemProcessor<User, User> {

    @Inject
    private UserRepository userRepository;

    @Override
    public User process(User item) throws Exception {
        User user = userRepository.findOne(item.getUserId());
        return user.getVersion() == item.getVersion() ? user : null;
    }
}