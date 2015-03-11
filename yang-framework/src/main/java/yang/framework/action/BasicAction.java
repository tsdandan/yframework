package yang.framework.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * アクションクラスの親クラス
 * @author VV000584
 *
 */
public abstract class BasicAction{

	public abstract String execute(HttpServletRequest request, HttpServletResponse response);
}
