package yang.framework.servlet;

import java.util.Enumeration;
import java.util.ResourceBundle;

import yang.framework.util.ConfigUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * セッティングなどを自動的にロードしてConfigUtilに保存するサーブレット
 * @author VV000584
 *
 */
public class AutoloadServlet extends HttpServlet {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * config.propertiesからセッティングを取得してConfigUtilに保存
	 */
    @Override
    public void init() throws ServletException {
    }

}
