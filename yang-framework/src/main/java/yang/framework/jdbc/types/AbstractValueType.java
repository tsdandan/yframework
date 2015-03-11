package yang.framework.jdbc.types;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import yang.framework.jdbc.ValueType;

/**
 * ValueTypeを実装する抽象クラス
 * @author VV000584
 *
 */
public abstract class AbstractValueType implements ValueType {

    private int sqlType;

    /**
     * コンストラクター
     * @param sqlType
     */
    public AbstractValueType(int sqlType) {
        this.sqlType = sqlType;
    }

	/**
	 * PreparedStatementの指定したインデックスにnullを埋める
	 * @param ps
	 * @param index
	 * @throws SQLException
	 */
    protected void setNull(PreparedStatement ps, int index) throws SQLException {
        ps.setNull(index, sqlType);
    }

    /**
     *
     * @return
     */
    public int getSqlType() {
        return sqlType;
    }

}
