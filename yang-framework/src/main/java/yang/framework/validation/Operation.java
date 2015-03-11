package yang.framework.validation;

import yang.framework.validation.validator.MaxLengthValidator;
import yang.framework.validation.validator.MinLengthValidator;
import yang.framework.validation.validator.PatternValidator;
import yang.framework.validation.validator.RequiredValidator;

import java.util.regex.Pattern;

/**
 * オペレーション
 * Annotationのメソッドから実際のバリデーションエンジンにマッピングするクラス
 * @author VV000584
 *
 */
public class Operation {

	/**
	 * メソッド名は"Required"の時にRequiredのバリデーションエンジンを返す
	 * @return
	 */
	public static FormValidator Required(){
		return new RequiredValidator();
	}

	/**
	 * 正規表現のバリデーション、メソッド名はPattern
	 * @param errorMessage
	 * @param patterns
	 * @return
	 */
	public static PatternValidator Pattern(String errorMessage, Pattern... patterns){
		return new PatternValidator(errorMessage,patterns);
	}

	/**
	 * 長さ限定のバリデーション
	 * @param maxLength
	 * @return
	 */
	public static MaxLengthValidator MaxLength(int maxLength){
		return new MaxLengthValidator(maxLength);
	}

	/**
	 * 長さ限定のバリデーション
	 * @param maxLength
	 * @return
	 */
	public static MinLengthValidator MinLength(int minLength){
		return new MinLengthValidator(minLength);
	}
}
