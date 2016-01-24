package jp.gr.java_conf.star_diopside.spark.batch.logic;

import javax.inject.Inject;

import jp.gr.java_conf.star_diopside.spark.data.entity.User;
import jp.gr.java_conf.star_diopside.spark.data.repository.UserRepository;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.batch.item.ItemProcessor;

public class BA01J01S02Processor implements ItemProcessor<Pair<String, Integer>, User> {

    @Inject
    private UserRepository userRepository;

    @Override
    public User process(Pair<String, Integer> item) throws Exception {
        User user = userRepository.findOne(item.getLeft());
        return user.getVersion() == item.getRight() ? user : null;
    }
}
