package jp.gr.java_conf.stardiopside.ark.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.gr.java_conf.stardiopside.ark.data.entity.PostalCode;

/**
 * 郵便番号住所データリポジトリインタフェース
 */
public interface PostalCodeRepository extends JpaRepository<PostalCode, Long> {

}
