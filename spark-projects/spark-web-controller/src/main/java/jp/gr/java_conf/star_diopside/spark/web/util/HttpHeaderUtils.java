package jp.gr.java_conf.star_diopside.spark.web.util;

import java.io.UncheckedIOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.http.HttpHeaders;

public final class HttpHeaderUtils {

    private HttpHeaderUtils() {
    }

    public enum ContentDispositionType {
        ATTACHMENT("attachment"), INLINE("inline");

        private String name;

        private ContentDispositionType(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }

    public static HttpHeaders createContentDispositionHeader(String filename) {
        return createContentDispositionHeader(filename, ContentDispositionType.ATTACHMENT);
    }

    public static HttpHeaders createContentDispositionHeader(String filename, ContentDispositionType type) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION,
                    type.toString() + "; filename*=utf-8''" + URLEncoder.encode(filename, "UTF-8").replace("+", "%20"));
            return headers;
        } catch (UnsupportedEncodingException e) {
            throw new UncheckedIOException(e);
        }
    }
}
