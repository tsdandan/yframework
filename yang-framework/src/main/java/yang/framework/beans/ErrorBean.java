package yang.framework.beans;

import java.io.Serializable;
import java.util.ArrayList;

public class ErrorBean implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1762188218074135344L;

	private String title;
	private String[] messages;
	private String backurl;

	/**
	 * @param title
	 * @param messages
	 * @param backurl
	 */
	public ErrorBean(String title, String[] messages, String backurl) {
		super();
		this.title = title;
		this.messages = messages;
		this.backurl = backurl;
	}

	/**
	 * @param title
	 * @param messages
	 * @param backurl
	 */
	public ErrorBean(String title, ArrayList<String> messages, String backurl) {
		super();
		this.title = title;
		this.messages = new String[messages.size()];
		for (int i = 0; i < messages.size(); i++){
			this.messages[i] = messages.get(i);
		}
		this.backurl = backurl;
	}
	/**
	 * @return title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title セットする title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return messages
	 */
	public String[] getMessages() {
		return messages;
	}
	/**
	 * @param messages セットする messages
	 */
	public void setMessages(String[] messages) {
		this.messages = messages;
	}
	/**
	 * @return backurl
	 */
	public String getBackurl() {
		return backurl;
	}
	/**
	 * @param backurl セットする backurl
	 */
	public void setBackurl(String backurl) {
		this.backurl = backurl;
	}


}
