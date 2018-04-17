package top.wangyushuang.form;

import java.util.Map;
import java.util.Set;

import org.apache.struts2.dispatcher.Parameter;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class Form1ActionDemo extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String execute() throws Exception {
		System.out.println("action.......");
		//使用ActionContext类获取
		//1.获取ActionContext对象
		ActionContext context=ActionContext.getContext();
		//2.调用方法得到表单数据
		//Map key-"name" value-输入值
		Map<String, Parameter> map=context.getParameters();
		Set<String> keys=map.keySet();
		for(String key:keys) {
			Parameter obj=map.get(key);
			System.out.println(obj.getValue());
		}
		return NONE;
	}
	
}
