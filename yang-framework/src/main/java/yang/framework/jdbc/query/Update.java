package yang.framework.jdbc.query;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import yang.framework.jdbc.JdbcManager;

/**
 * アップテートクエリー、アップデートをインサートを処理
 * @author VV000584
 *
 */
public class Update {

	JdbcManager jdbcManager;
	protected String sql;

    /**
     * parameter
     */
    protected Object[] param;

	protected PreparedStatement ps;

	/**
	 *
	 * @param jdbcManager
	 * @param clazz
	 */
	public Update(JdbcManager jdbcManager, Object... param){
		this.jdbcManager= jdbcManager;
		this.param = param;
	}

	/**
	 * sqlを実行
	 * @return result
	 * @throws SQLException
	 */
	public int excute(){
		int ret = 0;
		try {
			prepare();
			ret = ps.executeUpdate();
			close();
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return ret;
	}

	/**
	 * PrepareStatementを準備する
	 * @throws SQLException
	 */
	public void prepare() throws SQLException{
		jdbcManager.connect();
		prepareStatement();
		prepareParameter(param);
	}

	/**
	 * PrepareStatementのパラメータを設定
	 * @param parameters
	 */
	public void prepareParameter(Object[] parameters){
		if (parameters == null){
			return;
		}
		int offset = 0;//パラメータのインデックス
		for (Object parameter : parameters){
			//パラメータのクラスとフィルトを取る
			Class<?> clazz = parameter.getClass();
			Field fields[] = clazz.getFields();

			//フィルトのタイプ別にparamのインスタンスを作る
			List<Param> params = new ArrayList<Param>();
			for (int i = 0; i < fields.length; i++){
				try {
					Param param = new Param(fields[i].get(parameter),fields[i].getType());
					params.add(param);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
			for (int i = 0; i < params.size(); i++){
				try {
					//paramをpreparedstatementに保存
					params.get(i).valueType.bindValue(ps, 1+offset++, params.get(i).value);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		}
	}

	/**
	 * sqlを準備
	 * @param sql
	 */
	public void prepareSql(String sql){
		this.sql = sql;
	}

	/**
	 * prepareStatementを準備
	 * @throws SQLException
	 */
	public void prepareStatement() throws SQLException{
		if (ps != null){
			ps.close();
		}
		ps = jdbcManager.getConnection().prepareStatement(sql);
	}

	public void close(){
		try {
			if (ps != null){
				ps.close();
			}
			jdbcManager.disconnect();
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}
}
