package yang.framework.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DigestUtil {
	public static final String SHA256 = "SHA-256"; //Digest.SHA256のように指定する
	public static final String SHA384 = "SHA-384";
	public static final String SHA512 = "SHA-512"; //その他MD-5なども対応可
	private MessageDigest messageDigest;
	//コンストラクタでアルゴリズム指定
	public DigestUtil(String algorithm) {
		super();
		//アルゴリズムが空ならヌルポを投げる
		if (algorithm == null) {
			throw new NullPointerException("no Algorithm");
		}
		try {
			//メッセージダイジェストを指定アルゴリズムで作成
			this.messageDigest = MessageDigest.getInstance(algorithm);
		}catch(NoSuchAlgorithmException e){
			// エラー処理
			System.err.println(e.getLocalizedMessage());
		}
	}

	public String hex(String message) {
		//メッセージが指定されなかったらヌルポを投げる
		if (message == null){
			throw new NullPointerException("no message");
		}
		if (this.messageDigest == null) {
			return "";
		}
		StringBuilder builder = new StringBuilder(); //応答文字列作成用
		try{
			messageDigest.reset();
			messageDigest.update(message.getBytes("UTF-8"));
			//メッセージダイジェスト
			byte[] digest = messageDigest.digest();
			for (int i = 0; i < digest.length; i++) {
				int d = digest[i] & 0xff;//Byte型(からInt型に拡張する時に、足りない部分の24ビット分をプラスマイナスの符号を変えないように埋めるためffとANDをとる
				String hex = Integer.toHexString(d);//16進整数形式の文字列に変換
				if (hex.length() == 1) { //一桁だったら0アペンド
					builder.append("0");
				}
				builder.append(hex);
			}
		}catch(UnsupportedEncodingException e){
			System.err.println(e.getLocalizedMessage());
		}
		return builder.toString(); //文字列化して返す
	}
}