package yang.framework.validation;

/**
 * バリデーションユニット
 * 一個のObjectに関するバリデーションクラス
 * Objectとバリデーションエンジンのリストを記録している
 * @author VV000584
 *
 */
public class ValidationUnit {

	/**
	 * バリデーション必要のObject
	 */
	private Object property;

	/**
	 * エラーを表示する時の名前
	 */
	private String propertyName;

	/**
	 * バリデーションエンジンのリスト
	 */
	private FormValidator[] validators;

	/**
	 * @param property
	 * @param propertyName
	 * @param validators
	 */
	public ValidationUnit(Object property, String propertyName,
			FormValidator[] validators) {
		super();
		this.property = property;
		this.propertyName = propertyName;
		this.validators = validators;
	}

	/**
	 * @return validators
	 */
	public FormValidator[] getValidators() {
		return validators;
	}

	/**
	 * @param validators セットする validators
	 */
	public void setValidators(FormValidator[] validators) {
		this.validators = validators;
	}

	/**
	 * @return property
	 */
	public Object getProperty() {
		return property;
	}

	/**
	 * @param property セットする property
	 */
	public void setProperty(Object property) {
		this.property = property;
	}

	/**
	 * @return propertyName
	 */
	public String getPropertyName() {
		return propertyName;
	}

	/**
	 * @param propertyName セットする propertyName
	 */
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}



}
