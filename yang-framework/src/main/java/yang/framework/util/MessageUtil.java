package yang.framework.util;

import java.util.ArrayList;

/**
 * セッションで保存するメッセージを処理するユティリティ
 * シングルトンパターン
 * @author VV000584
 *
 */
public class MessageUtil {

	private static MessageUtil instance = null;
	private ArrayList<String> errors = new ArrayList<String>();

	/**
	 * クラスのインスタンスを返す
	 * @return
	 */
	public static MessageUtil getInstance(){
		if (instance == null){
			instance = new MessageUtil();
		}
		return instance;
	}

	/**
	 * エラーメッセージをクリア
	 */
	public void clearError(){
		errors.clear();
	}

	/**
	 * エラーがあるかどうかを判断
	 * @return
	 */
	public boolean hasError(){
		return errors!=null&&errors.size()>0;
	}

	/**
	 * エラーを追加する
	 * @param error
	 */
	public void addError(String error){
		errors.add(error);
	}

	/**
	 * エラーを取得
	 * @return
	 */
	public ArrayList<String> getErrors(){
		return errors;
	}
}
