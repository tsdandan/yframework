package yang.framework.validation;

/**
 * バリデーションエンジンのインタフェース
 * @author VV000584
 *
 */
public interface FormValidator {
	/**
     * バリデーションを実行します。
     * @param value
     * @return
     */
    public abstract boolean validate(Object value);

    /**
     * エラーメッセージを返します。
     *
     * @return
     */
    public abstract String getMessage();

    /**
     * ラベルの引数を返します。
     * @param label
     * @return
     */
    public abstract Object[] getArgsWithLabel(String label);
}
