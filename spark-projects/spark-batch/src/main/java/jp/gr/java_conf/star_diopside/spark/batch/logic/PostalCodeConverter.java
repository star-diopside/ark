package jp.gr.java_conf.star_diopside.spark.batch.logic;

import org.springframework.batch.item.ItemProcessor;

import jp.gr.java_conf.star_diopside.spark.batch.bean.PostalCodeAddress;
import jp.gr.java_conf.star_diopside.spark.data.entity.PostalCode;

public class PostalCodeConverter implements ItemProcessor<PostalCodeAddress, PostalCode> {

    @Override
    public PostalCode process(PostalCodeAddress item) throws Exception {
        PostalCode entity = new PostalCode();

        entity.setLocalGovernmentCode(item.getLocalGovernmentCode());
        entity.setOldPostalCode(item.getOldPostalCode());
        entity.setPostalCode(item.getPostalCode());
        entity.setKanaPrefectureName(item.getKanaPrefectureName());
        entity.setKanaMunicipalityName(item.getKanaMunicipalityName());
        entity.setKanaAreaName(item.getKanaAreaName());
        entity.setKanjiPrefectureName(item.getKanjiPrefectureName());
        entity.setKanjiMunicipalityName(item.getKanjiMunicipalityName());
        entity.setKanjiAreaName(item.getKanjiAreaName());

        return entity;
    }
}
