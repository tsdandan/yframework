package yang.framework.jdbc.query;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import yang.framework.jdbc.JdbcManager;
import yang.framework.util.StringUtil;

/**
 * セレクトクエリー
 * メソッドチェンを実現するために自分自身をエクステンドしています
 * @author VV000584
 *
 * @param <T>
 * @param <S> メソッドチェンを実行するためのエクステンド
 */
public class Select<T, S extends Select<T, S>> {

	JdbcManager jdbcManager;
	protected Class<T> resultClass;
	protected String sql;
	ResultSet rs;
    /**
     * max row
     */
    protected int maxRows = 0;

    /**
     * fetch
     */
    protected int fetchSize = 0;

    /**
     * offset
     */
    protected int offset = 0;

    /**
     * limit
     */
    protected int limit = 0;

    /**
     * page
     */
    protected int page = 0;

    /**
     * order by
     */
    protected String orderBy = "";

    /**
     * order順
     */
    protected String order = "asc";

    /**
     * parameter
     */
    protected Object[] param;

	protected PreparedStatement ps;

	/**
	 * マックスローを設定するメソッドチェン
	 * @param maxRows
	 * @return
	 */
	@SuppressWarnings("unchecked")
    public S maxRows(int maxRows) {
        this.maxRows = maxRows;
        return (S) this;
    }

	/**
	 * フィッチサイズを設定するメソッドチェン
	 * @param fetchSize
	 * @return
	 */
    @SuppressWarnings("unchecked")
    public S fetchSize(int fetchSize) {
        this.fetchSize = fetchSize;
        return (S) this;
    }

    /**
     * リミットを設定するメソッドチェン
     * @param limit
     * @return
     */
    @SuppressWarnings("unchecked")
    public S limit(int limit) {
        this.limit = limit;
        return (S) this;
    }

    /**
     * オフサイトを設定するメソッドチェン
     * @param offset
     * @return
     */
    @SuppressWarnings("unchecked")
    public S offset(int offset) {
        this.offset = offset;
        return (S) this;
    }

    @SuppressWarnings("unchecked")
	public S page(int page){
    	this.page = page;
    	return (S) this;
    }

    /**
     * 並び順のカラムを設定するメソッドチェン
     * @param orderBy
     * @return
     */
    @SuppressWarnings("unchecked")
	public S orderBy(String orderBy){
    	this.orderBy = orderBy;
    	return (S) this;
    }

    /**
     * 並び順を設定するメソッドチェン
     * @param orderBy
     * @param order
     * @return
     */
    public S orderBy(String orderBy, String order){
    	this.order = order;
    	return orderBy(orderBy);
    }

	/**
	 * コンストラクター
	 * @param jdbcManager
	 * @param clazz
	 * @param param 可変配列、複数パラメータ対応
	 */
	public Select(JdbcManager jdbcManager, Class<T> clazz, Object... param){
		this.jdbcManager= jdbcManager;
		this.resultClass = clazz;
		this.param = param;
	}

	/**
	 * シングルのリザルトをResultSetから取得
	 * @return result
	 * @throws SQLException
	 */
	public T getSingleResult(){
		T ret = null;
		try {
			prepare();
			rs = ps.executeQuery();
			ret = createSingleEntity(resultClass);
			close();
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return ret;
	}

	/**
	 * リザルトのインスタンスを作る
	 * @param clazz リザルトのクラス
	 * @param rs
	 * @return
	 */
	private T createEntity(Class<T> clazz){
		T result = null;
		try {
			result = clazz.newInstance();
		} catch (InstantiationException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		Field fields[] = clazz.getFields();
		for (int i = 0; i < fields.length; i++){
			Param param;
			try {
				//リザルトのクラスのフィルトのタイプによってparamのインスタンスを作る
				param = new Param(fields[i].get(result),fields[i].getType());

				//paramのインスタンスのgetValueのメソッドでrsから取る
				fields[i].set(result, param.valueType.getValue(rs, fields[i].getName()));
			} catch (IllegalArgumentException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * リザルトクラスのシングルのエンティティを取得、
	 * シングルではない場合はnullを返す
	 * @param clazz
	 * @param rs
	 * @return
	 */
	private T createSingleEntity(Class<T> clazz){
		T result = null;
		try {
			result = clazz.newInstance();
		} catch (InstantiationException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		try {
			if (rs.next()){
				result = createEntity(clazz);
				while (rs.next()){
					//シングルリザルトではない場合はエラーと認識して、nullを返す
					return null;
				}
			} else {
				return null;
			}
		} catch (SQLException e) {
				// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * リストのリザルトを取得
	 * @return
	 */
	public List<T> getResultList(){
		List<T> result = null;
		try {
			prepare();
			rs = ps.executeQuery();
			result = createListEntity(resultClass);
			close();
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * リザルトクラスのエンティティリストを取得
	 * @param clazz
	 * @param rs
	 * @return
	 */
	private List<T> createListEntity(Class<T> clazz){
		List<T> results = new ArrayList<T>();
		try {
			if (rs.next()){
				results.add(createEntity(clazz));
				while (rs.next()){
					results.add(createEntity(clazz));
				}
			} else{
				return null;
			}
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return results;
	}

	/**
	 * PrepareStatementを準備する
	 * @throws SQLException
	 */
	public void prepare() throws SQLException{
		jdbcManager.connect();
		prepareSql();
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
	 * 設定した制限でsql分を再構築
	 */
	public void prepareSql(){
		//ページ数によってオフセットを計算する
		//リミットが設定されてない時は10にして計算する
		if (page > 0){
			if (limit > 0){
				offset = (page-1)*limit;
			} else{
				offset = (page-1)*10;
			}
		}
		//リミットがあるsql文を構築
		if (limit > 0){
			sql = "select A.*, rownum r from (" + sql + ") A where rownum <= " + StringUtil.toString(limit);
			if (offset > 0){
				sql = "select * from (" + sql + ") B where r > 0";
			}
		}
		//オーダーバイがあるsql文を構築
		if (orderBy!=null&&!orderBy.equals("")){
			sql = sql + " order by " + orderBy + " " + order;
		}
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
		ps.setMaxRows(maxRows);
		ps.setFetchSize(fetchSize);
	}

	public void close(){
		try {
			if (rs != null){
				rs.close();
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
