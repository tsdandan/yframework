package yang.framework.jdbc.query;

import java.lang.reflect.Field;

import yang.framework.jdbc.ValueType;

/**
 * PreparedStatementのparameterのクラス
 * ValueTypeで値のタイプによって管理します
 * @author VV000584
 *
 */
public class Param {
	/**
     * パラメータの値です。
     */
    public Object value;

    /**
     * パラメータのクラスです。
     */
    public Class<?> paramClass;

    /**
     * 値タイプです。
     */
    public ValueType valueType;

    /**
     * フィールドです。
     */
    public Field field;

    /**
     * {@link Param}を作成します。
     */
    public Param() {
    }

    /**
     * {@link Param}を作成します。
     *
     * @param value
     *            パラメータの値です。
     * @param paramClass
     *            パラメータのクラスです。
     */
    public Param(Object value, Class<?> paramClass) {
        this.value = value;
        this.paramClass = paramClass;
        try {
			if (paramClass.getSimpleName().equals("int")){
				this.valueType = (ValueType) Class.forName("yang.framework.jdbc.types.IntegerType").newInstance();
			} else {
				this.valueType = (ValueType) Class.forName("yang.framework.jdbc.types." + paramClass.getSimpleName() + "Type").newInstance();
			}
		} catch (InstantiationException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
    }
}
