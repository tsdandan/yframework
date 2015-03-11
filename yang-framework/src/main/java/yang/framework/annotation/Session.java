package yang.framework.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * セッションに保存するDtoのアノテーション
 * @author VV000584
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Session {
	String type() default "";
}
