package jp.gr.java_conf.star_diopside.spark.web.util;

import java.util.Iterator;
import java.util.List;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import lombok.Getter;

@Getter
public class PageWrapper<T> implements Page<T> {

    private static final int AROUND_PAGE_SIZE = 5;
    private Page<T> page;
    private int startPageNumber;
    private int endPageNumber;

    public PageWrapper(Page<T> page) {
        this.page = page;

        int viewPageSize = AROUND_PAGE_SIZE * 2;
        int start = page.getNumber() - AROUND_PAGE_SIZE;

        if (start <= 0) {
            startPageNumber = 0;
            int end = startPageNumber + viewPageSize;
            endPageNumber = (end < page.getTotalPages() ? end : page.getTotalPages() - 1);
        } else {
            int end = page.getNumber() + AROUND_PAGE_SIZE;
            endPageNumber = (end < page.getTotalPages() ? end : page.getTotalPages() - 1);
            start = endPageNumber - viewPageSize;
            startPageNumber = (start <= 0 ? 0 : start);
        }
    }

    @Override
    public int getNumber() {
        return page.getNumber();
    }

    @Override
    public int getSize() {
        return page.getSize();
    }

    @Override
    public int getNumberOfElements() {
        return page.getNumberOfElements();
    }

    @Override
    public List<T> getContent() {
        return page.getContent();
    }

    @Override
    public boolean hasContent() {
        return page.hasContent();
    }

    @Override
    public Sort getSort() {
        return page.getSort();
    }

    @Override
    public boolean isFirst() {
        return page.isFirst();
    }

    @Override
    public boolean isLast() {
        return page.isLast();
    }

    @Override
    public boolean hasNext() {
        return page.hasNext();
    }

    @Override
    public boolean hasPrevious() {
        return page.hasPrevious();
    }

    @Override
    public Pageable nextPageable() {
        return page.nextPageable();
    }

    @Override
    public Pageable previousPageable() {
        return page.previousPageable();
    }

    @Override
    public Iterator<T> iterator() {
        return page.iterator();
    }

    @Override
    public int getTotalPages() {
        return page.getTotalPages();
    }

    @Override
    public long getTotalElements() {
        return page.getTotalElements();
    }

    @Override
    public <S> Page<S> map(Converter<? super T, ? extends S> converter) {
        return page.map(converter);
    }
}
