package jp.gr.java_conf.stardiopside.ark.service;

import jp.gr.java_conf.stardiopside.ark.data.entity.PostalCode;
import jp.gr.java_conf.stardiopside.ark.data.repository.PostalCodeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.transaction.Transactional;

@Named
@Singleton
public class PostalCodeServiceImpl implements PostalCodeService {

    private final PostalCodeRepository postalCodeRepository;

    public PostalCodeServiceImpl(PostalCodeRepository postalCodeRepository) {
        this.postalCodeRepository = postalCodeRepository;
    }

    @Override
    @Transactional
    public Page<PostalCode> search(Pageable pageable) {
        Sort sort = Sort.by("localGovernmentCode", "postalCode", "kanaPrefectureName",
                "kanaMunicipalityName", "kanaAreaName").ascending();
        return postalCodeRepository.findAll(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort));
    }
}
