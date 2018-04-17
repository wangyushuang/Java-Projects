package top.wangyushuang.Data;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
//表达式封装
//1.声明一个T类（不是创建）
//2.创建get和set方法
//3.在表单输入项的name属性添加对象
public class Data3Action extends ActionSupport{
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		System.out.println(user);
		return NONE;
	}
}
