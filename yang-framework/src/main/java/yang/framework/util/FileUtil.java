package yang.framework.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * ファイルの処理のユティリティ
 * シングルトンパターン
 * @author VV000584
 *
 */
public class FileUtil {

	private static FileUtil instance;

	/**
	 * クラスのインスタンスを返す
	 * @return
	 */
	public static FileUtil getInstance(){
		if (instance==null){
			instance = new FileUtil();
		}
		return instance;
	}

	/**
	 * sql文をファイルから読み出す
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public String getStringContent(String path){
		String content = null;
		try {
			InputStream sqlFileIn =
					getClass().getClassLoader().getResourceAsStream(path);
			StringBuffer sqlSb = new StringBuffer();
			byte[] buff = new byte[1024];
			int byteRead = 0;
			while ((byteRead = sqlFileIn.read(buff)) != -1) {
			sqlSb.append(new String(buff, 0, byteRead,"utf-8"));
			}
			sqlFileIn.close();
			content = sqlSb.toString();
		}  catch (Exception ex) {
			//TODO
			ex.printStackTrace();
		}
		return content;
	}

	/**
	 * sql文をファイルから読み出す
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public List<String> getStringContents(List<String> paths){
		List<String> content = new ArrayList<String>();
		try {
			for (int i = 0; i < paths.size(); i++){
				InputStream sqlFileIn =
					getClass().getClassLoader().getResourceAsStream(paths.get(i));
				StringBuffer sqlSb = new StringBuffer();
				byte[] buff = new byte[1024];
				int byteRead = 0;
				while ((byteRead = sqlFileIn.read(buff)) != -1) {
					sqlSb.append(new String(buff, 0, byteRead,"utf-8"));
				}
				sqlFileIn.close();
				content.add(sqlSb.toString());
			}
		}  catch (Exception ex) {
			//TODO
			ex.printStackTrace();
			content = null;
		}
		return content;
	}

}
