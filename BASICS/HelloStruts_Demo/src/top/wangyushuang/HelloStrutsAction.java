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
}
