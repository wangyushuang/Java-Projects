package top.wangyushuang;

import com.opensymphony.xwork2.ActionSupport;

public class HelloStrutsAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String execute() {
		System.out.println("hello struts action..............");
		return "ok";
	}
	public String book_add() {
		System.out.println("add..........");
		return "add";
	}
	public String book_update() {
		System.out.println("update.............");
		return "update";
	}
	
}
