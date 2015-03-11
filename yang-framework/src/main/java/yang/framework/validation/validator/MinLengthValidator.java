package yang.framework.validation.validator;

/**
 * 最大文字酢のバリデーションエンジン
 * AbstractFormValidatorを継承
 * @author VV000584
 *
 */
public class MinLengthValidator extends AbstractFormValidator{
	private static String errorMessage ="は短すぎます";
	private int minLength;


	public MinLengthValidator(int minLength){
		this.minLength = minLength;
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
            return ((String)value).length()>=minLength;
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
