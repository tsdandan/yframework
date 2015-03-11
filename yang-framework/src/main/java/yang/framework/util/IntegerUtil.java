package yang.framework.util;

/**
 * Integerを処理するユリィリティ
 * @author VV000584
 *
 */
public class IntegerUtil {

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
     * 数字に変換します。
     *
     * @param value
     *            値
     * @param pattern
     *            パターン
     * @return 変換された結果
     */
    public static Long toLong(Object value) throws NumberFormatException{
        if (value == null) {
            return null;
        } else if (value instanceof Number) {
            return (Long) value;
        } else if (value instanceof java.util.Date) {
            return ((java.util.Date) value).getTime();
        } else if (value instanceof String) {
            return Long.parseLong((String) value);
        } else {
            return Long.parseLong(value.toString());
        }
    }

    /**
     * 数字に変換します。
     *
     * @param value
     *            値
     * @param pattern
     *            パターン
     * @return 変換された結果
     */
    public static Integer toInt(Object value) {
        if (value == null) {
            return 0;
        } else if (value instanceof Number) {
            return (Integer) value;
        } else if (value instanceof String) {
            return Integer.parseInt((String) value);
        } else {
            return Integer.parseInt(value.toString());
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
