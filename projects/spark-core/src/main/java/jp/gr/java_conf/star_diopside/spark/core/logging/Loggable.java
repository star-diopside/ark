package jp.gr.java_conf.star_diopside.spark.core.logging;

import java.util.stream.Stream;

/**
 * ログ出力情報取得機能を持つクラスが実装するインタフェース
 */
public interface Loggable {

    /**
     * ログ出力用オブジェクトのストリームを生成する。
     * 
     * @return ログ出力用オブジェクトのストリーム
     */
    Stream<?> streamLoggingObjects();
}
