package yang.framework.validation.validator;

/**
 * 最大文字酢のバリデーションエンジン
 * AbstractFormValidatorを継承
 * @author VV000584
 *
 */
public class MaxLengthValidator extends AbstractFormValidator{
	private static String errorMessage ="は長すぎます";
	private int maxLength;


	public MaxLengthValidator(int maxLength){
		this.maxLength = maxLength;
	}

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
            return ((String)value).length()<=maxLength;
        }
        return false;
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
