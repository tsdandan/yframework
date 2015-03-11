package yang.framework.util;

import javax.servlet.http.HttpServletRequest;

public class HttpServletUtil {
	/**
	 * リクエストからパラメタ―をゲット
	 * @param paramName
	 * @param req
	 * @return
	 */
	public static String getParameter(String paramName, HttpServletRequest req){
		String temp = req.getParameter(paramName);
		if (temp == null)
			temp = "";
		return temp;
	}

	/**
	 * リクエストからパラメタ―をゲットして、数字型を返す
	 * @param paramName
	 * @param req
	 * @return
	 */
	public static int getParameterInt(String paramName, HttpServletRequest req) {
		String temp = req.getParameter(paramName);
		if (temp == null)
			return -1;
		try{
			return Integer.parseInt(temp);
		}catch(NumberFormatException e){
			return -1;
		}
	}

	/**
	 * リクエストからパラメタ―をゲットして、数字型を返す
	 * @param paramName
	 * @param req
	 * @return
	 */
	public static Long getParameterLong(String paramName, HttpServletRequest req) {
		String temp = req.getParameter(paramName);
		if (temp == null)
			return (long)-1;
		try{
			return Long.parseLong(temp);
		}catch(NumberFormatException e){
			return (long)-1;
		}
	}
}

