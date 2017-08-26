package jp.gr.java_conf.star_diopside.spark.batch.logic;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class UserKeyMapper implements FieldSetMapper<Pair<String, Integer>> {

    @Override
    public Pair<String, Integer> mapFieldSet(FieldSet fieldSet) throws BindException {
        return Pair.of(fieldSet.readString("userId"), fieldSet.readInt("version"));
    }
}
