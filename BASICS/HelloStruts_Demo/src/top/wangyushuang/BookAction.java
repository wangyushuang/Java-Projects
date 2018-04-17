package top.wangyushuang;

import com.opensymphony.xwork.ActionSupport;

public class BookAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String execute() {
		System.out.println("book action ,..........");
		return "ok";
	}
}
