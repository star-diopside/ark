package jp.gr.java_conf.star_diopside.spark.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.gr.java_conf.star_diopside.spark.data.entity.PostalCode;

/**
 * 郵便番号住所データリポジトリインタフェース
 */
public interface PostalCodeRepository extends JpaRepository<PostalCode, Long> {

}