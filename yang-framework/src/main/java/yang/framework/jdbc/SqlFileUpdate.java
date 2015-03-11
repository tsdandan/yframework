package yang.framework.jdbc;

import yang.framework.jdbc.query.Update;
import yang.framework.util.FileUtil;

/**
 * sqlファイルからupdateとinsertを実行するクエリー
 * @author VV000584
 *
 */
public class SqlFileUpdate extends Update{

	/**
	 * コンストラクター
	 * アップデートクエリーのインスタンスを取得
	 * @param jdbcManager
	 * @param path
	 * @param param
	 */
	public SqlFileUpdate(JdbcManager jdbcManager, String path, Object... param) {
		super(jdbcManager, param);
		this.sql = FileUtil.getInstance().getStringContent(path);
	}

}
