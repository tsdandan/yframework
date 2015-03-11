package yang.framework.model;

import javax.servlet.http.HttpServletRequest;

import yang.framework.dto.AuthDto;
import yang.framework.util.DigestUtil;

/**
 * Authorityを操作するモデル
 * @author VV000584
 *
 */
public class AuthModel {

	/**
	 * 新しいAuthDtoを取得
	 * @param id
	 * @param type
	 * @param sessionid
	 * @return
	 */
	public static AuthDto createAuth(Long id, String type, String sessionid){
		return new AuthDto(id,type,sessionid);
	}

	/**
	 * 新しいAuthDtoを取得
	 * @param id
	 * @param type
	 * @param sessionid
	 * @return
	 */
	public static AuthDto createAuth(Long id, String type, HttpServletRequest request){
    	DigestUtil digest = new DigestUtil(DigestUtil.SHA512);
	    String sessionId = digest.hex(request.getSession().getId());
		return new AuthDto(id,type,sessionId);
	}

	/**
	 * Authorityが正しいかどうかを判断する
	 * @param auth
	 * @param id
	 * @param type
	 * @param sessionid
	 * @return
	 */
	public static boolean isAuthed(AuthDto auth, Long id, String[] type, String sessionid){
		boolean flag = false;
		if (!auth.getId().equals(id)){
			return flag;
		}
		if (!auth.getSessionId().equals(sessionid)){
			return flag;
		}
		for (int i = 0; i < type.length; i++){
			if (type[i].equals(auth.getType())){
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * Authorityが正しいかどうかを判断する
	 * @param auth
	 * @param id
	 * @param type
	 * @param sessionid
	 * @return
	 */
	public static boolean isAuthed(AuthDto auth, String[] type, String sessionid){
		boolean flag = false;
		if (!auth.getSessionId().equals(sessionid)){
			return flag;
		}
		for (int i = 0; i < type.length; i++){
			if (type[i].equals(auth.getType())){
				flag = true;
			}
		}
		return flag;
	}
}
