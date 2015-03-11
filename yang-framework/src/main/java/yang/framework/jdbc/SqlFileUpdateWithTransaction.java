package yang.framework.jdbc;

import java.util.List;

import yang.framework.jdbc.query.UpdateWithTransaction;
import yang.framework.util.FileUtil;

/**
 * sqlファイルからupdateとinsertを実行するクエリー
 * @author VV000584
 *
 */
public class SqlFileUpdateWithTransaction extends UpdateWithTransaction{

	/**
	 * コンストラクター
	 * アップデートクエリーのインスタンスを取得
	 * @param jdbcManager
	 * @param path
	 * @param param
	 */
	public SqlFileUpdateWithTransaction(JdbcManager jdbcManager, List<String> paths, List<List<Object>> param) {
		super(jdbcManager, param);
		this.sqls = FileUtil.getInstance().getStringContents(paths);
	}

}
