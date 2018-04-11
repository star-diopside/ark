package jp.gr.java_conf.stardiopside.ark.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.gr.java_conf.stardiopside.ark.data.entity.Authority;
import jp.gr.java_conf.stardiopside.ark.data.entity.AuthorityId;

/**
 * 権限リポジトリインタフェース
 */
public interface AuthorityRepository extends JpaRepository<Authority, AuthorityId> {

    /**
     * ユーザIDを条件に権限情報を検索する。
     * 
     * @param userId ユーザID
     * @return 権限情報一覧
     */
    List<Authority> findByUserId(String userId);

}
