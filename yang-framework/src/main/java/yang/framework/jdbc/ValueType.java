package yang.framework.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * preparedStatementのパラメータを管理するインタフェース
 * パラメータを処理するメソッドが含めています
 * @author VV000584
 *
 */
public interface ValueType {

	/**
	 * レザルトセットからインデックスの位置の値を取得
	 * @param resultSet
	 * @param index
	 * @return
	 * @throws SQLException
	 */
	Object getValue(ResultSet resultSet, int index) throws SQLException;

	/**
	 * レザルトセットから指定した名前の値を取得
	 * @param resultSet
	 * @param columnName
	 * @return
	 * @throws SQLException
	 */
	Object getValue(ResultSet resultSet, String columnName) throws SQLException;

	/**
	 * PreparedStatementの指定したインデックスに値を埋める
	 * @param ps
	 * @param index
	 * @param value
	 * @throws SQLException
	 */
    void bindValue(PreparedStatement ps, int index, Object value)
            throws SQLException;

    /**
     * 値を文字列に変更
     * @param value
     * @return
     */
    String toText(Object value);
}
