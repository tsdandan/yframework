package yang.framework.validation;

import java.util.ArrayList;
import java.util.List;

import yang.framework.util.MessageUtil;

/**
 * バリデーション
 * バリデーションユニットを管理するクラス
 * ユニットの追加や、ユニットの実行を行う
 * @author VV000584
 *
 */
public class Validation {

	/**
	 * バリデーションユニットのリスト
	 */
	List<ValidationUnit> validationUnits = new ArrayList<ValidationUnit>();

	/**
	 * 新しいバリデーションユニットをリストに追加
	 * @param property
	 * @param propertyName
	 * @param validators
	 * 			可変配列 バリデーションエンジンの配列
	 * @return
	 */
	public Validation addValidation(Object property, String propertyName, FormValidator... validators){
		if (validators != null && validators.length > 0){
			ValidationUnit validationUnit = new ValidationUnit(property,propertyName,validators);
			validationUnits.add(validationUnit);
		}
		return this;
	}

	/**
	 * バリデーションを実行する
	 */
	public void excute(){
		for (ValidationUnit validationUnit : validationUnits){
			for (FormValidator validator : validationUnit.getValidators()){
				if (!validator.validate(validationUnit.getProperty())){
					String error = validationUnit.getPropertyName() + validator.getMessage();
					MessageUtil.getInstance().addError(error);
				}
			}
		}
	}

	/**
	 * バリデーションを実行して結果を返す
	 */
	public boolean validate(){
		for (ValidationUnit validationUnit : validationUnits){
			for (FormValidator validator : validationUnit.getValidators()){
				if (!validator.validate(validationUnit.getProperty())){
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * バリデーションを実行してエラーを返す
	 */
	public String validateError(){
		for (ValidationUnit validationUnit : validationUnits){
			for (FormValidator validator : validationUnit.getValidators()){
				if (!validator.validate(validationUnit.getProperty())){
					return validationUnit.getPropertyName() + validator.getMessage();
				}
			}
		}
		return "";
	}
}
