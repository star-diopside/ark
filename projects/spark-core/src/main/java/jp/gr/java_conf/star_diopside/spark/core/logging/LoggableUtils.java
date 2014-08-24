package jp.gr.java_conf.star_diopside.spark.core.logging;

import java.util.Collection;
import java.util.stream.Stream.Builder;

/**
 * ログ出力情報編集用ユーティリティクラス
 */
public final class LoggableUtils {

    private LoggableUtils() {
    }

    /**
     * ログ出力用文字列を編集し、ログストリームに追加する。
     * 
     * @param builder ログ出力用文字列を設定するストリームビルダー
     * @param item ログ出力項目
     * @param itemName ログ出力項目名称
     * @param enabled ログ出力が有効な場合はtrue、無効な場合はfalse。
     */
    public static void addLog(Builder<String> builder, Object item, String itemName, boolean enabled) {
        if (enabled) {
            addLog(builder, item, itemName);
        }
    }

    /**
     * ログ出力用文字列を編集し、ログストリームに追加する。
     * 
     * @param builder ログ出力用文字列を設定するストリームビルダー
     * @param item ログ出力項目
     * @param itemName ログ出力項目名称
     */
    public static void addLog(Builder<String> builder, Object item, String itemName) {
        if (item instanceof Loggable) {
            ((Loggable) item).streamLoggingObjects().map(s -> itemName + "." + s).forEach(builder::add);
        } else {
            builder.add(itemName + " = " + item);
        }
    }

    /**
     * リスト項目をログ出力用文字列に編集し、ログストリームに追加する。
     * 
     * @param builder ログ出力用文字列を設定するストリームビルダー
     * @param itemList ログ出力リスト項目
     * @param itemName ログ出力リスト項目名称
     * @param enabled ログ出力が有効な場合はtrue、無効な場合はfalse。
     */
    public static void addLogList(Builder<String> builder, Collection<?> itemList, String itemName, boolean enabled) {
        if (enabled) {
            addLogList(builder, itemList, itemName);
        }
    }

    /**
     * リスト項目をログ出力用文字列に編集し、ログストリームに追加する。
     * 
     * @param builder ログ出力用文字列を設定するストリームビルダー
     * @param itemList ログ出力リスト項目
     * @param itemName ログ出力リスト項目名称
     */
    public static void addLogList(Builder<String> builder, Collection<?> itemList, String itemName) {
        if (itemList == null) {
            addLog(builder, itemList, itemName);
        } else {
            int count = 0;
            for (Object o : itemList) {
                addLog(builder, o, itemName + "[" + count++ + "]");
            }
        }
    }
}
