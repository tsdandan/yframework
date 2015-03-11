package yang.framework.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * オソーリティのアノテーション
 * @author VV000584
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Authority {

	/**
	 * リクエストに保存するユーザーIDのkey
	 * @return
	 */
	String key_id() default "id";
	/**
	 * 必要なユーザータイプ
	 * @return
	 */
	String[] type() default "user";
}
