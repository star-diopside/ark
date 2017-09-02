package jp.gr.java_conf.star_diopside.spark.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import jp.gr.java_conf.star_diopside.spark.data.entity.PostalCode;

public interface PostalCodeService {

    Page<PostalCode> search(Pageable pageable);

}
