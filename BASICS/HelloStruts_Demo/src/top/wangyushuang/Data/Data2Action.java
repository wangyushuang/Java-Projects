package top.wangyushuang.Data;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
//模型驱动封装
//1.实现ModelDriven接口,注意泛型
//2.实现getModel方法
//3.创建对象成员变量（对象属性名称要与表单中name属性一致），并初始化
public class Data2Action extends ActionSupport implements ModelDriven<User> {

	private User user=new User();
	@Override
	public User getModel() {
		// TODO Auto-generated method stub
		return user;
	}
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		System.out.println(user);
		return NONE;
	}
}
