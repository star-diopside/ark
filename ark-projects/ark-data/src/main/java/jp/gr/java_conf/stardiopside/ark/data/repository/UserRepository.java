package jp.gr.java_conf.stardiopside.ark.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.gr.java_conf.stardiopside.ark.data.entity.User;

/**
 * ユーザリポジトリインタフェース
 */
public interface UserRepository extends JpaRepository<User, String> {

}
