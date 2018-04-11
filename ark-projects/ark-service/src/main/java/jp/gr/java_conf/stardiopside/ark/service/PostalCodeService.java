package jp.gr.java_conf.stardiopside.ark.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import jp.gr.java_conf.stardiopside.ark.data.entity.PostalCode;

public interface PostalCodeService {

    Page<PostalCode> search(Pageable pageable);

}
