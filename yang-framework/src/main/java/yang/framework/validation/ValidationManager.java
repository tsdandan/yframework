package yang.framework.validation;

/**
 * バリデーションエンジン
 * バリデーションを管理するクラス
 * バリデーションインスタンスを作る
 * @author VV000584
 *
 */
public class ValidationManager {

	/**
	 * バリデーションインスタンスを作ってバリデーションユニットを追加
	 * @param property
	 * @param propertyName
	 * @param formValidators
	 * @return
	 */
	public static Validation validation(Object property, String propertyName, FormValidator... formValidators){
		return new Validation().addValidation(property, propertyName, formValidators);
	}
}
