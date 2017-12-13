import java.util.Map;
import java.util.Set;
import java.util.Iterator;
import java.util.HashMap;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;
import javax.swing.*;
import java.awt.event.*;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
public class NewStu extends JPanel implements ActionListener{
	private static final long serialVersionUID=1L;//序列化时保持版本的兼容性，即版本升级时保持对象的唯一性
	private String host;
	private Connection conn;
	private Statement stmt;
	private ResultSet rs;
	private String coll_id;
	//存放专业名和专业号，键是专业名，值是专业号
	private Map<String,String> map_dept=new HashMap<String,String>();
	//存放专业号和班的集合，班的集合也用Map,键是班名，值是班号
	private Map<String,Map<String,String>> map_class=new HashMap<String,Map<String,String>>();
	static int year=Calendar.getInstance().get(Calendar.YEAR);//new Date().getYear()+1900;//年号
	private JLabel[] jlArray= {new JLabel("学号"),new JLabel("姓名"),new JLabel("性别"),
			new JLabel("出生日期"),new JLabel("籍贯"),new JLabel("学院"),new JLabel("专业"),
			new JLabel("班级"),new JLabel("入学时间"),new JLabel("年"),new JLabel("月"),new JLabel("日")};
	private JTextField[] jtfArray= {new JTextField(),new JTextField(),new JTextField()};
	String[] str_gender= {"男","女"};
	static String[] str_year1=new String[20];
	//初始化出生年份
	static {
		for(int i=15;i<35;i++){
			str_year1[i-15]=year-i+"";
		}
	}
	String[] str_year= {year+"",year-1+"",year-2+"",year-3+"",year-4+"",year-5+""};//入学年份
	static final String[] str_month=new String[12];//月份数组
	static {
		for(int i=0;i<12;i++)
			str_month[i]=i+1+"";
	}
	static final String[] str_day=new String[31];
	static {
		for(int i=0;i<31;i++)
			str_day[i]=i+1+"";
	}
	private JComboBox[] jcbArray= {new JComboBox(str_gender),new JComboBox(str_year1),new JComboBox(str_month),
			new JComboBox(str_day),new JComboBox(),new JComboBox(),new JComboBox(),new JComboBox(str_year),
			new JComboBox(str_month),new JComboBox(str_day)};
	private JButton[] jbArray= {new JButton("提交"),new JButton("重置")};
	public NewStu(String coll_id,String host) {
		this.host=host;
		this.coll_id=coll_id;
		this.initialData();
		this.initialFrame();
		this.addListener();
	}
	public void initialData() {
		try {
			this.initialConnection();//初始化数据库连接
			String sql1="select coll_name from college where coll_id='"+coll_id+"'";//sql语句
			rs=stmt.executeQuery(sql1);
			if(rs.next()) {
				String coll_name=new String(rs.getString(1).getBytes("utf8"));//new String(rs.getString(1).getBytes("ISO-8859-1"));
				jcbArray[4].addItem(coll_name);
			}
			rs.close();
			//获取专业名称与专业标号
			String sql2="select dept_name,dept_id from dept where coll_id='"+coll_id+"'";
			rs=stmt.executeQuery(sql2);
			while(rs.next()) {
				String dept_name=new String(rs.getString(1).getBytes("utf8"));//new String(rs.getString(1).getBytes("ISO-8859-1"));//转码
				String dept_id=rs.getString(2);
				map_dept.put(dept_name,dept_id);
			}
			rs.close();
			Set<String> keyset=map_dept.keySet();
			Iterator<String> ii=keyset.iterator();
			int i=0;
			String initial_dept_name=null;
			while(ii.hasNext()) {
				String dept_name=(String)ii.next();
				if(i==0)
					initial_dept_name=dept_name;
				jcbArray[5].addItem(dept_name);
				String dept_id=map_dept.get(dept_name);
				String sql3="select class_id,class_name from class where dept_id='"+dept_id+"'";
				rs=stmt.executeQuery(sql3);
				Map<String,String> class_map=new HashMap<String,String>();
				while(rs.next()) {
					String class_id=rs.getString(1);
					String class_name=new String(rs.getString(2).getBytes("utf8"));//new String(rs.getString(2).getBytes("ISO-8859-1"));
					class_map.put(class_name, class_id);
				}
				rs.close();
				map_class.put(dept_id, class_map);
				i++;
			}
			this.closeConn();
			jcbArray[5].setSelectedItem(initial_dept_name);//下拉列表初始值
			String initial_dept_id=map_dept.get(initial_dept_name);
			Map classmap=(HashMap)map_class.get(initial_dept_id);
			Set keyset1=classmap.keySet();
			Iterator ii1=keyset1.iterator();
			while(ii1.hasNext()) {
				String s=(String) ii1.next();
				jcbArray[6].addItem(s);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void initialFrame() {
		this.setLayout(null);
		for(int i=0;i<jlArray.length-3;i++) {//标签
			jlArray[i].setBounds(30,50+50*i,100,30);
			this.add(jlArray[i]);
		}
		for(int i=0;i<2;i++) {//学号和姓名输入框
			jtfArray[i].setBounds(150,50+50*i,200,30);
			this.add(jtfArray[i]);
		}
		jtfArray[2].setBounds(150,50+50*4,500,30);//籍贯输入框
		this.add(jtfArray[2]);
		jcbArray[0].setBounds(150,50+50*2,50,30);//性别组合框
		this.add(jcbArray[0]);
		jcbArray[1].setBounds(150,50+50*3,60,30);//组合框:出生年份
		this.add(jcbArray[1]);
		jcbArray[2].setBounds(150+90,50+50*3,50,30);//组合框：出生月份
		this.add(jcbArray[2]);
		jcbArray[3].setBounds(150+90+80,50+50*3,50,30);//组合框：出生日
		this.add(jcbArray[3]);
		int i=jlArray.length-3;
		jlArray[i].setBounds(150+65,50+50*3,30,30);//年
		this.add(jlArray[i]);
		i++;
		jlArray[i].setBounds(150+90+55,50+50*3,30,30);//月
		this.add(jlArray[i]);
		i++;
		jlArray[i].setBounds(150+90+80+55,50+50*3,30,30);//日
		this.add(jlArray[i]);
		jcbArray[4].setBounds(150,50+50*5,200,30);//学院组合框
		this.add(jcbArray[4]);
		jcbArray[5].setBounds(150,50+50*6,200,30);//专业组合框
		this.add(jcbArray[5]);
		jcbArray[6].setBounds(150,50+50*7,200,30);//班级
		this.add(jcbArray[6]);
		jcbArray[7].setBounds(150,50+50*8,60,30);//组合框：入学年份
		this.add(jcbArray[7]);
		jcbArray[8].setBounds(150+90,50+50*8,50,30);//组合框：入学月份
		this.add(jcbArray[8]);
		jcbArray[9].setBounds(150+90+80,50+50*8,50,30);//组合框：入学日
		this.add(jcbArray[9]);
		JLabel jlYear=new JLabel("年");
		jlYear.setBounds(150+65,50+50*8,30,30);//年
		this.add(jlYear);
		JLabel jlMonth=new JLabel("月");
		jlMonth.setBounds(150+90+55,50+50*8,30,30);//月
		this.add(jlMonth);
		JLabel jlDay=new JLabel("日");
		jlDay.setBounds(150+90+80+55,50+50*8,30,30);//日
		this.add(jlDay);
		jbArray[0].setBounds(150,510,80,30);//提交按钮
		this.add(jbArray[0]);
		jbArray[1].setBounds(280,510,80,30);//重置按钮
		this.add(jbArray[1]);
	}
	public void addListener() {
		jtfArray[0].addActionListener(this);
		jtfArray[1].addActionListener(this);
		jbArray[0].addActionListener(this);
		jbArray[1].addActionListener(this);
		jcbArray[5].addActionListener(this);
	}
	public void actionPerformed(ActionEvent e) {
		 if(e.getSource()==jcbArray[5]) {//改变专业
			 String deptname=(String) jcbArray[5].getSelectedItem();//专业名
			 String deptid=map_dept.get(deptname);//专业号
			 Map<String,String> classmap=(HashMap<String,String>)map_class.get(deptid);
			 Set keyset=classmap.keySet();//该专业号对应的班级
			 Iterator ii=keyset.iterator();
			 jcbArray[6].removeAllItems();//清空下拉列表
			 while(ii.hasNext()) {
				 String s=(String)ii.next();
				 jcbArray[6].addItem(s);//添加下拉列表
			 }
		 }else if(e.getSource()==this.jbArray[0]) {//提交
			 this.submitStu();
		 }else if(e.getSource()==this.jbArray[1]) {//重置
			 for(int i=0;i<jtfArray.length;i++) {
				 jtfArray[i].setText("");//清空输入框
			 }
		 }else if(e.getSource()==jtfArray[0]){//输入学号，按Enter后
			 jtfArray[1].requestFocus(true);//切换输入焦点
		 }else if(e.getSource()==jtfArray[1]) {
			 jcbArray[0].requestFocus(true);//切换输入焦点
		 }
	}
	public void submitStu() {
		String stu_id=jtfArray[0].getText().trim();
		String patternStr="[0-9]{12}";
		if(stu_id.equals("")) {
			JOptionPane.showMessageDialog(this, "请输入学号","错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		if(!stu_id.matches(patternStr)) {
			JOptionPane.showMessageDialog(this, "学号必须是十二位数字","错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		String stu_name=jtfArray[1].getText().trim();
		if(stu_name.equals("")) {
			JOptionPane.showMessageDialog(this, "请输入姓名","错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		if(stu_name.length()>10) {
			JOptionPane.showMessageDialog(this,"输入名字过长，请检查是否正确","错误",JOptionPane.ERROR_MESSAGE);
			return ;
		}
		String stu_gender=((String)jcbArray[0].getSelectedItem()).trim();//性别
		String bir_year=((String)jcbArray[1].getSelectedItem()).trim();//出生年份
		String bir_month=((String)jcbArray[2].getSelectedItem()).trim();//出生月份
		String bir_day=((String)jcbArray[3].getSelectedItem()).trim();//出生日期
		String stu_birth=bir_year+"-"+bir_month+"-"+bir_day;//拼凑成日期格式字符串
		String nativeplace=jtfArray[2].getText().trim();//获得籍贯 
		if(nativeplace.equals("")) {
			JOptionPane.showMessageDialog(this, "请输入籍贯","错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		if(nativeplace.length()>30) {
			JOptionPane.showMessageDialog(this,"籍贯长度过长，请简写","错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		String coll_id=this.coll_id;//学院号
		String dept_id=map_dept.get(((String)jcbArray[5].getSelectedItem()).trim());//专业号
		String class_id=((String)(((HashMap)map_class.get(dept_id)).get(jcbArray[6].getSelectedItem()))).trim();//班号
		String come_year=(String)jcbArray[7].getSelectedItem();//入学年份
		String come_month=(String)jcbArray[8].getSelectedItem();//入学月份
		String come_day=(String)jcbArray[9].getSelectedItem();//入学日期
		String cometime=come_year+"-"+come_month+"-"+come_day;//拼凑成日期格式字符串
		this.initialConnection();
		try {
			String sql="insert into student values('"+stu_id+"','"+
					new String(stu_name.getBytes("utf8"))+"','"+//new String(stu_name.getBytes("ISO-8859-1"))
					new String(stu_gender.getBytes("utf8"))+"','"+//new String(stu_gender.getBytes("ISO-8859-1"))
					java.sql.Date.valueOf(stu_birth)+"','"+
					new String(nativeplace.getBytes("utf8"))+"','"+//new String(nativeplace.getBytes("ISO-8859-1"))
					coll_id+"','"+dept_id+"','"+class_id+"','"+
					java.sql.Date.valueOf(cometime)+"')";			
			conn.setAutoCommit(false);//取消自动提交模式
			boolean i=stmt.execute(sql);//插入新纪录
			String sql1="insert into user_stu values('"+stu_id+"','"+stu_id+"')";//默认密码为学号
			int j=stmt.executeUpdate(sql1);//更新
			if(!i && 1==j) {//添加学生成功
				conn.commit();//提交
				conn.setAutoCommit(true);//恢复自动提交模式
				JOptionPane.showMessageDialog(this, "添加成功", "提示", JOptionPane.INFORMATION_MESSAGE);//弹出提示信息
			}else {//添加失败
				conn.rollback();//回滚
				conn.setAutoCommit(true);
				JOptionPane.showMessageDialog(this, "添加失败", "错误", JOptionPane.ERROR_MESSAGE);
			}
		}catch(SQLException ea) {
			ea.printStackTrace();
		}catch(UnsupportedEncodingException eb) {
			eb.printStackTrace();
		}finally {
			this.closeConn();
		}
	}
	public void initialConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url="jdbc:mysql://"+host+"/test"
					+ "?useUnicode=true&characterEncoding=utf-8&useSSL=false";//要访问的数据库名
			conn=DriverManager.getConnection(url,"root","000623");
			stmt=conn.createStatement();
		}catch(SQLException e) {
			e.printStackTrace();
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	public void closeConn() {
		try {
			if(rs!=null) rs.close();
			if(stmt!=null) stmt.close();
			if(conn!=null) conn.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	public void setFocus() {
		this.jtfArray[0].requestFocus(true);
	}
	public static void main(String[] args) {
		NewStu ns=new NewStu("01","127.0.0.1:3306");
		JFrame jframe=new JFrame();
		jframe.setBounds(70,20,700,650);
		ns.setBounds(10,20,500,500);
		jframe.add(ns);
		jframe.setVisible(true);
	}
}





