package yang.framework.servlet;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import yang.framework.annotation.ResponseType;
import yang.framework.annotation.Authority;
import yang.framework.annotation.Resource;
import yang.framework.annotation.Session;
import yang.framework.annotation.Validate;
import yang.framework.dto.AuthDto;
import yang.framework.model.AuthModel;
import yang.framework.util.ConvertUtil;
import yang.framework.util.DigestUtil;
import yang.framework.util.IntegerUtil;
import yang.framework.util.MessageUtil;
import yang.framework.validation.FormValidator;
import yang.framework.validation.Operation;
import yang.framework.validation.ValidationManager;

/**
 * 前処理と後処理を含めているのフロントコントローラーです
 * 前処理：URLの解析、パラメーターの取得、バリデーション
 * 後処理：セッションに結果を保存、フォーワード処理
 * @author VV000584
 *
 */
public class ActionServlet extends HttpServlet{

	/**
	 *
	 */
	private static final long serialVersionUID = -6262998001788325670L;

	private static final String key_action_package="action_package";
	private static final String key_error_page="error_page";
	private static final String key_index_page="index_page";

    private String packagePath;
    private String requestUri;
    private String actionUrl;
    private String forwardUrl;
    private String backUrl;
    private Object handler;
    private String errorpage;
    private String indexpage;
    private HttpSession session;

    /**
     * パラメータの初期化
     * action.propertiesからactionのpackageを取得
     */
    @Override
    public void init() throws ServletException {
        ResourceBundle rb = ResourceBundle.getBundle("actions");
        if (rb.containsKey(key_action_package)){
        	this.packagePath = rb.getString(key_action_package);
        } else {
        	this.packagePath = "";
        }
        if (rb.containsKey(key_error_page)){
        	this.errorpage = rb.getString(key_error_page);
        } else {
        	this.errorpage = "/WEB-INF/error.jsp";
        }
        if (rb.containsKey(key_index_page)){
        	this.indexpage = rb.getString(key_index_page);
        } else {
        	this.indexpage = "/WEB-INF/index.jsp";
        }
        this.forwardUrl = errorpage;
        this.requestUri = "";
        this.actionUrl = "";
        this.handler = null;
        this.backUrl = "";
    }

    /**
     * サーブレットの処理を実行する
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
	private void process(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException{
			processPrepare(request,response);
			processPath(request,response);
			if(request.getSession(false)==null){
				session = request.getSession(true);
				forwardUrl = indexpage;
				processForward(request,response);
			} else {
				session = request.getSession();
				handler = processClass(request,response);
				if (handler != null) {
					processResource(request,response);
					if (!MessageUtil.getInstance().hasError()){
						processAction(request,response);
						processSession(request,response);
					}
					if (!MessageUtil.getInstance().hasError()){
						processResponse(request,response);
					}
				} else {
					forwardUrl = errorpage;
					MessageUtil.getInstance().addError("不明なURL");
				}
				if (MessageUtil.getInstance().hasError()){
					processError(request,response);
					processForward(request,response);
				}
			}
	}

	/**
	 * アクションクラスのフィルトを解析して、ResourceのAnnotationが付いている
	 * フィルトに、リクエストから取ったパラメータをセットする
	 * @param request
	 * @param response
	 */
	private void processResource(HttpServletRequest request, HttpServletResponse response){
		for (Field field : handler.getClass().getFields()){
			Resource resourceAnnotation = (Resource) field.getAnnotation(Resource.class);
			if (resourceAnnotation != null){
				Object resource;
				try {
					resource = field.getType().newInstance();
					for (Field formField : field.getType().getFields()){
						//リスト系の処理
						if (formField.getType().isAssignableFrom(List.class)){
							//postしてないパラメータはスギップ
							if (request.getParameterValues(formField.getName())==null){
								continue;
							}
						} else {
							//postしてないパラメータはスギップ
							if (request.getParameter(formField.getName())==null){
								continue;
							}
							//コンバートメソッドを取得
							String typeName = formField.getType().getSimpleName();
							if (typeName == "int"){
								typeName = "Integer";
							}
							Method convertMethod = ConvertUtil.class.getMethod(typeName, Object.class);
							//リクエストから取ったパラメータをターゲットフィルトのクラスにコンバートする
							Object value;
							try{
								value = convertMethod.invoke(ConvertUtil.class.newInstance(), request.getParameter(formField.getName()));
							} catch (NumberFormatException e){
						    	MessageUtil.getInstance().addError("パラメータが不正です");
					            forwardUrl = errorpage;
					            return;
							}
							formField.set(resource, value);
						}
					}
					processValidation(resource);
					field.set(handler, resource);
				} catch (Exception e) {
					MessageUtil.getInstance().addError("パラメータが不正です");
		            forwardUrl = errorpage;
		            return;
				}
			}
		}
	}

	/**
	 * Resourceのフィルトのクラスのフィルトに、ValidateのAnnotationが付いている
	 * フィルトのバリデーションを実行
	 * @param resource
	 * @return
	 */
	private void processValidation(Object resource){
		for (Field field : resource.getClass().getFields()){
			Validate validate = (Validate) field.getAnnotation(Validate.class);
			if (validate != null){
		        try {
		        	//バリデーションのメソッド名を取得
		        	String[] methodNames = validate.method();

		        	for (String methodName : methodNames){
		        		Method executor = null;
		        		FormValidator validator = null;

		        		//メソッドの名前から実際のメソッドを取得
			            executor = new Operation().getClass().getMethod(methodName);
			            if (executor != null){
			            	validator = (FormValidator) executor.invoke(new Operation());
			            }

			            //メソッドの実行
			            if	(validator != null){
							ValidationManager.validation(field.get(resource), validate.propertyName(), validator).excute();
						}
		        	}
		        } catch (Exception e) {
		            e.printStackTrace();
		            forwardUrl = errorpage;
		        }
			}
		}
	}

	/**
	 * エラーメッセージをセッションに保存
	 * @param request
	 * @param response
	 */
	private void processError(HttpServletRequest request, HttpServletResponse response){
		session.setAttribute("errors", MessageUtil.getInstance().getErrors());
        forwardUrl = errorpage;
        backUrl = request.getHeader("Referer");
        session.setAttribute("backurl", backUrl);
	}

	/**
	 * アクションを実行
	 * @param request
	 * @param response
	 */
	private void processAction(HttpServletRequest request, HttpServletResponse response){
		Class<? extends Object> handlerClass = handler.getClass();
        Method executor = null;
        try {
        	//実際のActionクラスのexecuteのメソッドを取得
            executor = handlerClass.getMethod("execute", new Class[] {
                    HttpServletRequest.class, HttpServletResponse.class });
        } catch (Exception e) {
            e.printStackTrace();
            forwardUrl = errorpage;
        }

        //ActionクラスがAuthorityのテェックが必要の時にテェックする
        Authority authority = (Authority)executor.getAnnotation(Authority.class);
        if (authority!=null){
        	DigestUtil digest = new DigestUtil(DigestUtil.SHA512);
		    String sessionId = digest.hex(session.getId());
		    AuthDto auth = (AuthDto) session.getAttribute("auth");
		    if (auth == null){
		    	MessageUtil.getInstance().addError("権限がありません");
	            forwardUrl = errorpage;
	            return;
		    }
		    Long id;
		    try{
		    	id = IntegerUtil.toLong(request.getParameter(authority.key_id()));
		    } catch (NumberFormatException e){
		    	MessageUtil.getInstance().addError("パラメータが不正です");
	            forwardUrl = errorpage;
	            return;
		    }

		    //CSRF対応
		    if (request.getParameter("token")!=null){
		    	if (!request.getParameter("token").equals(sessionId)){
		    		MessageUtil.getInstance().addError("変な事をしないてください");
		    		forwardUrl = errorpage;
		    		return;
		    	}
		    }
		    //権限がない時はエラーを返す
		    if (id!=null){
		    	if (!AuthModel.isAuthed(auth, id, authority.type(), sessionId)){
		    		MessageUtil.getInstance().addError("権限がありません");
		    		forwardUrl = errorpage;
		    		return;
		    	}
		    } else {
		    	if (!AuthModel.isAuthed(auth, authority.type(), sessionId)){
		    		MessageUtil.getInstance().addError("権限がありません");
		    		forwardUrl = errorpage;
		    		return;
		    	}
		    }
        }

        try {
        	//Actionクラスのexecuteを実行して、行先のjspのurlを取得
        	forwardUrl = (String) executor.invoke(handler, new Object[] { request,
                    response });
        } catch (Exception e) {
            e.printStackTrace();
            MessageUtil.getInstance().addError("不明なエラーです");
            forwardUrl = errorpage;
        }
	}

	/**
	 * URLを分析して、アクションの名前を取る
	 * @param request
	 * @param response
	 */
	private void processPath(HttpServletRequest request, HttpServletResponse response){
		requestUri = request.getRequestURI();

		actionUrl = requestUri.substring(
                requestUri.lastIndexOf('/') + 1, requestUri.length());
	}

	/**
	 * フォーワードする
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void processForward(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        request.getRequestDispatcher(forwardUrl).forward(request, response);
	}

	/**
	 * クラスのインスタンスを生成
	 * @param request
	 * @param response
	 * @return
	 */
	private Object processClass(HttpServletRequest request, HttpServletResponse response){
		String classPath = this.packagePath + "." + actionUrl + "Action";
		try {
			return Class.forName(classPath).newInstance();
		} catch (InstantiationException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * セッションに出力内容を保存、あるいはセッションのクリア
	 * @param request
	 * @param response
	 */
	private void processSession(HttpServletRequest request, HttpServletResponse response){
		//csrf
		DigestUtil digest = new DigestUtil(DigestUtil.SHA512);
	    String token = digest.hex(session.getId());
	    session.setAttribute("token", token);
		for (Field field : handler.getClass().getFields()){
			if (field.getAnnotation(Session.class) != null){
				try {
					session.setAttribute(field.getName(), field.get(handler));
				} catch (IllegalArgumentException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}
			}
		}

		//セッション廃棄のAnnotationが付いているメソッドがあれば（ログアウトなど）、セッションを廃棄します。
		for (Method method : handler.getClass().getMethods()){
			Session se = (Session) method.getAnnotation(Session.class);
			if (se!=null){
				if (se.type().equals("destroy")){
					session.invalidate();
				}
			}
		}
	}

	/**
	 * 準備行動をする
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	private void processPrepare(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		response.setContentType("text/html;charset=UTF-8;pageEncoding=UTF-8");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		MessageUtil.getInstance().clearError();
	}

	private void processResponse(HttpServletRequest request, HttpServletResponse response){
        ResponseType responseType = handler.getClass().getAnnotation(ResponseType.class);
        String type = "forward";
        if (responseType!=null){
        	type = responseType.type();
        }
        if (type.equals("ajax")){
        	return;
        }
        if (type.equals("redirect")){
        	try {
				response.sendRedirect(forwardUrl);
			} catch (IOException e) {
				forwardUrl = errorpage;
				e.printStackTrace();
			}
        	return;
        }
        try {
			processForward(request,response);
		} catch (Exception e) {
			forwardUrl = errorpage;
			e.printStackTrace();
		}
	}

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	process(request, response);
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	process(request, response);
    }
}
