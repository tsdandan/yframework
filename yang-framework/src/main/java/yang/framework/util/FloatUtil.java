package yang.framework.util;

/**
 * Floatを処理するユリィリティ
 * @author VV000584
 *
 */
public class FloatUtil {

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
    public static Double toDouble(Object value) {
        if (value == null) {
            return null;
        } else if (value instanceof Number) {
            return (Double) value;
        } else if (value instanceof String) {
            return Double.parseDouble((String) value);
        } else {
            return Double.parseDouble(value.toString());
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
    public static Float toFloat(Object value) {
        if (value == null) {
            return null;
        } else if (value instanceof Number) {
            return (Float) value;
        } else if (value instanceof String) {
            return Float.parseFloat((String) value);
        } else {
            return Float.parseFloat(value.toString());
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
