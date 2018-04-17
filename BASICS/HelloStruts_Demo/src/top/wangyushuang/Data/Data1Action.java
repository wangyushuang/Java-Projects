package top.wangyushuang.Data;

import com.opensymphony.xwork.ActionSupport;

//属性封装
public class Data1Action extends ActionSupport {
	private String no1;
	private String no2;
	
	public void setNo1(String no1) {
		this.no1 = no1;
	}

	public void setNo2(String no2) {
		this.no2 = no2;
	}

	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		System.out.println(no1+"  "+no2);
		return NONE;
	}
}
