package yang.framework.validation.validator;

import java.util.ArrayList;
import java.util.List;

import yang.framework.validation.FormValidator;

/**
 * AbstractFormValidator
 * バリデーションエンジンのインタフェースを実装する抽象クラス
 * @author VV000584
 *
 */
public abstract class AbstractFormValidator implements FormValidator {
	private static String errorMessage = "エラーがあります";

	@Override
	public Object[] getArgsWithLabel(String label) {
		return null;
	}

    /**
     * Formの値をString配列で返します。String以外の値は捨てます。
     * @param value
     * @return
     */
    protected String[] getStringArray(Object value) {
        if (value instanceof String) {
            return new String[] { (String) value };
        } else if (value instanceof String[]) {
            return (String[]) value;
        } else if (value instanceof List) {
            List<String> list = new ArrayList<String>();
            for (Object obj : (List<?>) value) {
                if (obj != null) {
                    list.add(obj.toString());
                } else {
                    list.add(null);
                }
            }
            return list.toArray(new String[list.size()]);
        }
        return new String[0];
    }

}
