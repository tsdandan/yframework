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
public class UpdateWithTransaction {

	JdbcManager jdbcManager;
	protected List<String> sqls;

    /**
     * parameter
     */
    protected List<List<Object>> param;

	protected PreparedStatement ps;

	private boolean success;
	/**
	 *
	 * @param jdbcManager
	 * @param clazz
	 */
	public UpdateWithTransaction(JdbcManager jdbcManager, List<List<Object>> param){
		this.jdbcManager= jdbcManager;
		this.param = param;
		success = false;
	}

	/**
	 * sqlを実行
	 * @return result
	 * @throws SQLException
	 */
	public int excute(){
		int ret = 0;
		if (sqls==null&&param==null){
			return 0;
		}
		if (sqls.size()!=param.size()){
			return 0;
		}
		try {
			prepare();
			for (int i = 0; i < sqls.size(); i++){
				prepareStatement(sqls.get(i));
				prepareParameter(param.get(i));
				ret = ps.executeUpdate();
			}
			success = true;
			ret = 1;
			close();
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			success = false;
			close();
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
		jdbcManager.beginTransaction();
	}

	/**
	 * PrepareStatementのパラメータを設定
	 * @param parameters
	 */
	public void prepareParameter(List<Object> parameters){
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
	public void prepareSql(List<String> sqls){
		this.sqls = sqls;
	}

	/**
	 * prepareStatementを準備
	 * @throws SQLException
	 */
	public void prepareStatement(String sql) throws SQLException{
		if (ps != null){
			ps.close();
		}
		ps = jdbcManager.getConnection().prepareStatement(sql);
	}

	public void close(){
		try {
			if (success){
				jdbcManager.commit();
			} else {
				jdbcManager.rollback();
			}
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
