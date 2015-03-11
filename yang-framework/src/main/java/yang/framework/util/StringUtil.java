package yang.framework.util;

import java.text.SimpleDateFormat;

/**
 * Stringを処理するユリィリティ
 * @author VV000584
 *
 */
public class StringUtil {

	/**
	 * 空かどうかの判断
	 * @param text
	 * @return
	 */
    public static final boolean isEmpty(final String text) {
        return text == null || text.length() == 0;
    }

	/**
	 * 空かどうかの判断
	 * @param text
	 * @return
	 */
    public static final boolean isNotEmpty(final String text) {
        return !isEmpty(text);
    }

    /**
     * 文字列に変換します。
     *
     * @param value
     *            値
     * @return 変換された結果
     */
    public static String toString(Object value) {
        return toString(value, null);
    }

    /**
     * 文字列に変換します。
     *
     * @param value
     *            値
     * @param pattern
     *            パターン
     * @return 変換された結果
     */
    public static String toString(Object value, String pattern) {
        if (value == null) {
            return null;
        } else if (value instanceof String) {
            return (String) value;
        } else if (value instanceof java.util.Date) {
        	SimpleDateFormat format = new SimpleDateFormat(pattern);
            return format.format((java.util.Date) value);
        } else {
            return value.toString();
        }
    }

    /**
     * {@link String}の文字列表現を返します。
     *
     * @param value
     *            値
     * @return 文字列表現
     */
    public static String toText(String value) {
        if (value == null) {
            return "";
        }
        return "'" + value + "'";
    }

}
