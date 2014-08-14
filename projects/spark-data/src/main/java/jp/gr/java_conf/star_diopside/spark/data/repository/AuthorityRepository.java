package jp.gr.java_conf.star_diopside.spark.data.repository;

import java.util.List;

import jp.gr.java_conf.star_diopside.spark.data.entity.Authority;
import jp.gr.java_conf.star_diopside.spark.data.entity.AuthorityId;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

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

    /**
     * ユーザIDを条件に権限情報を削除する。
     * 
     * @param userId ユーザID
     */
    @Modifying
    @Query("delete from Authority a where a.userId = ?1")
    void deleteByUserId(String userId);

}
