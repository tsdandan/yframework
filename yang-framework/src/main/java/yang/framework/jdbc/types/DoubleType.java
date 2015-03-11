package yang.framework.jdbc.types;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import yang.framework.util.FloatUtil;
import yang.framework.util.StringUtil;

/**
 * AbstractValueTypeを継承した実装クラス
 * Doubleクラスのパラメータを処理する
 * @author VV000584
 *
 */
public class DoubleType extends AbstractValueType {

	/**
	 * クラスのタイプをDoubleに設定
	 */
    public DoubleType() {
        super(Types.DOUBLE);
    }

	/**
	 * レザルトセットからインデックスの位置の値を取得
	 * @param resultSet
	 * @param index
	 * @return
	 * @throws SQLException
	 */
	@Override
	public Object getValue(ResultSet resultSet, int index) throws SQLException {
		return resultSet.getDouble(index);
	}

	/**
	 * レザルトセットから指定した名前の値を取得
	 * @param resultSet
	 * @param columnName
	 * @return
	 * @throws SQLException
	 */
	@Override
	public Object getValue(ResultSet resultSet, String columnName)
			throws SQLException {
		return resultSet.getDouble(columnName);
	}

	/**
	 * PreparedStatementの指定したインデックスに値を埋める
	 * @param ps
	 * @param index
	 * @param value
	 * @throws SQLException
	 */
	@Override
	public void bindValue(PreparedStatement ps, int index, Object value)
			throws SQLException {
		if (value == null) {
            setNull(ps, index);
        } else {
            ps.setDouble(index, FloatUtil.toDouble(value));
        }

	}

    /**
     * 値を文字列に変更
     * @param value
     * @return
     */
	@Override
	public String toText(Object value) {
        if (value == null) {
            return "";
        }
        String var = StringUtil.toString(value);
        return StringUtil.toText(var);
	}

}
