package yang.framework.util;

/**
 * コンバートユティリティ
 * @author VV000584
 *
 */
public class ConvertUtil {

	/**
	 * objectをStringに変換
	 * @param value
	 * @return
	 */
	public static String String(Object value){
		return StringUtil.toString(value);
	}

	/**
	 * objectをLongに変換
	 * @param value
	 * @return
	 */
	public static Long Long(Object value){
		return IntegerUtil.toLong(value);
	}

	public static int Integer(Object value){
		return IntegerUtil.toInt(value);
	}
}
