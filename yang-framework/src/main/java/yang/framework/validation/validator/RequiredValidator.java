package yang.framework.validation.validator;

import java.util.List;

import yang.framework.util.StringUtil;

/**
 * 必須パラメータのバリデーションエンジン
 * AbstractFormValidatorを継承
 * @author VV000584
 *
 */
public class RequiredValidator extends AbstractFormValidator{
	private static String errorMessage ="は必須です";

	/**
     * バリデーションを実行します。
     * @param value
     * @return
     */
	@Override
	public boolean validate(Object value) {
		if (value == null) {
            return false;
        }
        if (value instanceof String) {
            return !StringUtil.isEmpty((String) value);
        } else if (value instanceof String[]) {
            String[] values = (String[]) value;
            if (values.length != 0) {
                for (String v : values) {
                    if (StringUtil.isEmpty(v)) {
                        return false;
                    }
                }
                return true;
            }
        } else if (value instanceof List) {
            if (((List<?>) value).size() != 0) {
                for (Object o : (List<?>) value) {
                    if (o == null || StringUtil.isEmpty(o.toString())) {
                        return false;
                    }
                }
                return true;
            }
        }
        return true;
	}

	/**
     * エラーメッセージを返します。
     *
     * @return
     */
	@Override
	public String getMessage() {
		return errorMessage;
	}

}
