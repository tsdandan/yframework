package yang.framework.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.Name;
import javax.naming.NamingException;
import javax.sql.DataSource;

import yang.framework.util.ConfigUtil;

/**
 * DataSource、DBへの接続、、queryのインスタンス等を管理するクラス
 * @author VV000584
 *
 */
public class JdbcManager {

	private static JdbcManager instance = null;

	protected DataSource ds;

	private Context context = null;

	protected Connection conn;

	protected String sql;

	private JdbcManager(){
		try {
			context = new InitialContext();
			ds = (DataSource) context.lookup((String) ConfigUtil.getInstance().getValue("localName"));
		} catch (NamingException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	public static JdbcManager getInstance(){
		if (instance == null){
			instance = new JdbcManager();
		}
		return instance;
	}

	public DataSource getDataSource(){
		return ds;
	}

	public Connection getConnection(){
		return conn;
	}

	/**
	 * データベースへの接続
	 * @throws SQLException
	 */
	public void connect() throws SQLException{
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * データベースへの接続を切断
	 * @throws SQLException
	 */
	public void disconnect() throws SQLException{
		if(conn != null){
			conn.close();
		}
		conn = null;
	}

	/**
	 * トランザクション開始
	 * @throws SQLException
	 */
	public void beginTransaction() throws SQLException{
		try {
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ロールバック
	 * @throws SQLException
	 */
	public void rollback() throws SQLException{
		try {
			conn.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * コミット
	 * @throws SQLException
	 */
	public void commit() throws SQLException{
		try {
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * セレクトクエリーのインスタンスを取得
	 * @param baseClass
	 * @param path
	 * @param parameter
	 * @return
	 */
	public <T> SqlFileSelect<T> selectBySqlFile(Class<T> baseClass, String path,
            Object... parameter){
		return new SqlFileSelect<T>(this, baseClass, path, parameter);
	}

	/**
	 * アップデートクエリーのインスタンスを取得
	 * @param path
	 * @param data
	 * @return
	 */
	public SqlFileUpdate updateBySqlFile(String path, Object... data){
		return new SqlFileUpdate(this,path,data);
	}

	/**
	 * アップデートクエリーのインスタンスを取得
	 * @param path
	 * @param data
	 * @return
	 */
	public SqlFileUpdateWithTransaction updateBySqlFileWithTransaction(List<String> path, List<List<Object>> data){
		return new SqlFileUpdateWithTransaction(this,path,data);
	}
}
