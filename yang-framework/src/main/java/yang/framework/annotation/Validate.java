package yang.framework.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * バリデーション必要のパラメータのアノテーション
 * @author VV000584
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Validate {
	/**
	 * パラメータの表示名
	 * 表示名は、エラーメッセージを組む時に使う
	 * @return
	 */
	String propertyName() default "";

	/**
	 * バリデーションメソッドの名前
	 * @return
	 */
	String[] method() default "Required";

	/**
	 * 正規表現の場合のパターン
	 * @return
	 */
	String pattern() default "";
}
