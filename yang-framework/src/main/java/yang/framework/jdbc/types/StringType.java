package yang.framework.jdbc.types;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import yang.framework.util.StringUtil;

/**
 * AbstractValueTypeを継承した実装クラス
 * Stringクラスのパラメータを処理する
 * @author VV000584
 *
 */
public class StringType extends AbstractValueType {

	/**
	 * クラスのタイプをStringに設定
	 */
    public StringType() {
        super(Types.VARCHAR);
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
		return resultSet.getString(index);
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
		return resultSet.getString(columnName);
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
            ps.setString(index, StringUtil.toString(value));
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
