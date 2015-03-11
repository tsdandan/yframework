package yang.framework.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * configを保存するのユティリティ
 * シングルトンパターンで内容を保存
 * @author VV000584
 *
 */
public class ConfigUtil {

	private static ConfigUtil instance = null;

	/**
	 * 設定
	 */
	private HashMap<String,Object> config;

	/**
	 * コンストラクター
	 */
	private ConfigUtil(){
		config = new HashMap<String, Object>();
	}

	/**
	 * クラスのインスタンスを返す
	 * @return
	 */
	public static ConfigUtil getInstance(){
		if (instance == null){
			instance = new ConfigUtil();
			ResourceBundle rb = ResourceBundle.getBundle("config");
	        Enumeration<String> keys = rb.getKeys();
	        while (keys.hasMoreElements()){
	        	String key = keys.nextElement();
	        	String value = rb.getString(key);
	        	instance.setValue(key, value);
	        }
		}
		return instance;
	}

	/**
	 * 設定を保存
	 * @param key
	 * @param value
	 */
	public void setValue(String key, Object value){
		config.put(key, value);
	}

	/**
	 * 設定を取得
	 * @param key
	 * @return
	 */
	public Object getValue(String key){
		if (config.containsKey(key)){
			return config.get(key);
		}
		return null;
	}
}
