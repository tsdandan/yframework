package yang.framework.jdbc;

import yang.framework.jdbc.query.Select;
import yang.framework.util.FileUtil;

/**
 * sqlファイルからselectを実行するクエリー
 * @author VV000584
 *
 * @param <T>
 */
public class SqlFileSelect<T> extends Select<T, SqlFileSelect<T>>{

	/**
	 * コンストラクター
	 * セレクトクエリーのインスタンスを取得
	 * @param jdbcManager
	 * @param clazz
	 * @param path
	 * @param param
	 */
	public SqlFileSelect(JdbcManager jdbcManager, Class<T> clazz, String path, Object... param) {
		super(jdbcManager, clazz, param);
		this.sql = FileUtil.getInstance().getStringContent(path);
	}

}
