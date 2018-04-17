package top.wangyushuang.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork.ActionSupport;

public class Form2ActionDemo extends ActionSupport {
	@Override
	public String execute() throws Exception {
		// 使用ServletActionContext
		//1.获取Request对象
		HttpServletRequest request=ServletActionContext.getRequest();
		String name=request.getParameter("name");
		
		System.out.println(name);
		
		return NONE;
	}
}
