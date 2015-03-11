/**
 *
 */
package yang.framework.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Actionクラスに付けるアノーテションです
 * このアクションの処理結果がどういう形｛forward,redirect,ajax｝で返すのを指定する
 * @author VV000584
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ResponseType {
	public String type() default "forward";
}
