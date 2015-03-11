package yang.framework.jdbc;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import yang.framework.util.ClassUtil;
import yang.framework.util.ConfigUtil;
import yang.framework.util.StringUtil;

/**
 * データソースのインターフェースを実装するクラス
 * @author VV000584
 *
 */
public class DataSourceImpl implements DataSource {

	private String driverClassName;

    private String url;

    private String username;

    private String password;

    private int loginTimeout;

    private static DataSourceImpl dataSource = null;

    /**
     *	コンストラクター
     */
    public DataSourceImpl(){
    	this.driverClassName = (String) ConfigUtil.getInstance().getValue("driverClassName");
    	this.url = (String) ConfigUtil.getInstance().getValue("url");
    	this.username = (String) ConfigUtil.getInstance().getValue("username");
    	this.password = (String) ConfigUtil.getInstance().getValue("password");
    }

    /**
     * クラスのインスタンスを返す
     * @return
     */
    public static DataSourceImpl getInstance(){
    	if (dataSource == null){
    		dataSource = new DataSourceImpl();
    	}
    	return dataSource;
    }
	/**
	 * @return driverClassName
	 */
	public String getDriverClassName() {
		return driverClassName;
	}

	/**
	 * @param driverClassName セットする driverClassName
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws SQLException
	 */
	public void setDriverClassName(String driverClassName) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		this.driverClassName = driverClassName;
        if (driverClassName != null && driverClassName.length() > 0) {
            DriverManager.registerDriver(
            		(Driver) ClassUtil.newInstance(
            				ClassUtil.forName(driverClassName)));
        }
	}

	/**
	 * @return url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url セットする url
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return user
	 */
	public String getUserName() {
		return username;
	}

	/**
	 * @param user セットする user
	 */
	public void setUserName(String user) {
		this.username = user;
	}

	/**
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password セットする password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * データベースへの接続をする
	 */
	@Override
	public Connection getConnection() throws SQLException {
		Properties info = new Properties();
        if (StringUtil.isNotEmpty(username)) {
            info.put("username", username);
        }
        if (StringUtil.isNotEmpty(password)) {
            info.put("password", password);
        }
        int currentLoginTimeout = DriverManager.getLoginTimeout();
        try {
            DriverManager.setLoginTimeout(loginTimeout);
            Connection con = DriverManager.getConnection(url, username, password);
            return con;
        } finally {
            try {
                DriverManager.setLoginTimeout(currentLoginTimeout);
            } catch (Exception e) {
            }
        }
	}

	@Override
	public Connection getConnection(String username, String password)
			throws SQLException {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public PrintWriter getLogWriter() throws SQLException {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public void setLogWriter(PrintWriter out) throws SQLException {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public int getLoginTimeout() throws SQLException {
		// TODO 自動生成されたメソッド・スタブ
		return this.loginTimeout;
	}

	@Override
	public void setLoginTimeout(int seconds) throws SQLException {
		// TODO 自動生成されたメソッド・スタブ
		this.loginTimeout = seconds;
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

}
