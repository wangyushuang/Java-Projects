//选课设置代码主框架
import java.util.Vector;
import java.io.UnsupportedEncodingException;
import javax.swing.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.*;
public class CourseManage extends JPanel implements ActionListener{
	private static final long serialVersionUID=1L;//序列化时保持版本的兼容性，即版本升级时保持对象的唯一性
	private String host;
	private Connection conn;
	private Statement stmt;
	private ResultSet rs;
	private String coll_id;//学院号
	private Vector<String> columnNames1=new Vector<String>();//已选课程表格的表头
	private Vector<String> columnNames2=new Vector<String>();//可选课程表格的表头
	private Vector<Vector<String>> rowData1=new Vector<Vector<String>>();//已选课程表的数据
	private Vector<Vector<String>> rowData2=new Vector<Vector<String>>();//可选课程表的数据
	private JLabel[] jlArray= {new JLabel("现有课程表:"),new JLabel("已安排课程表:"),
				new JLabel("请输入您要安排的课程的课程号："),new JLabel("老师："),new JLabel("上课时间:"),
				new JLabel("星期"),new JLabel("讲次"),new JLabel("请输入您要移除的课程的课程号:"),
				new JLabel("星期"),new JLabel("讲次")};
	private static final String[] weekNum= {"1","2","3","4","5","6","7"};//星期
	private static final String[] courseNum= {"1","2","3","4","5"};//讲次
	private JButton[] jbArray= {new JButton("提交该课程"),new JButton("移除该课程"),
				new JButton("允许选课"),new JButton("停止选课")};
	private JTextField[] jtfArray= {new JTextField(),new JTextField(),new JTextField()};//文本输入框数组
	private JComboBox[] jcbArray= {new JComboBox(weekNum),new JComboBox(courseNum),
									new JComboBox(weekNum),new JComboBox(courseNum)};
	private JTable jt1;//上面表格
	private JTable jt2;//下面表格
	private JScrollPane jsp1;//上面滚动窗体
	private JScrollPane jsp2;//下面滚动窗体
	public CourseManage(String coll_id, String host) {
		this.host=host;
		this.coll_id=coll_id;
		this.initialData();//初始化两个表格中的数据数据
		this.initialFrame();//初始化界面
		this.initialListener();//为相应控件注册监听器
	}
	public void initialData() {
		this.initialHead();//初始化表头
		this.initialData1();//初始化课程表格数据
		this.initialData2();//初始化可选课程表格数据
	}
	public void initialData1() {//初始化课程表格
		try {
			String sql="select cou_id,cou_name,xuefen,dept.dept_name from "+
					"course,dept where dept.dept_id=course.dept_id and "+
					"course.coll_id='"+coll_id+"'";
			this.initialConnection();
			rs=stmt.executeQuery(sql);
			while(rs.next()) {
				Vector<String> v=new Vector<String>();
				String cou_id=rs.getString(1);//课程ID
				String cou_name=new String(rs.getString(2).getBytes("utf8"));//课程名称
				String xuefen=rs.getDouble(3)+"";
				String dept_name=new String(rs.getString(4).getBytes("utf8"));
				v.add(cou_id);v.add(cou_name);v.add(xuefen);v.add(dept_name);
				this.rowData1.add(v);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}catch(UnsupportedEncodingException e) {
			e.printStackTrace();
		}finally {
			this.closeConn();
		}
	}
	public void initialData2() {//初始化可选课程表格
		try {
			String sql="select courseinfo.cou_id,course.cou_name,cou_day,"+
						"cou_time,teacher from courseinfo,course where "+
						"course.coll_id='"+coll_id+"' and courseinfo.cou_id="+
						"course.cou_id";
			this.initialConnection();
			rs=stmt.executeQuery(sql);
			while(rs.next()) {
				Vector<String> v=new Vector<String>();
				String cou_id=rs.getString(1);//课程ID
				String cou_name=new String(rs.getString(2).getBytes("utf8"));//课程名
				String cou_day=rs.getString(3);
				String cou_time=rs.getString(4);
				String teacher=new String(rs.getString(5).getBytes("utf8"));
				v.add(cou_id);v.add(cou_name);v.add(cou_day);
				v.add(cou_time);v.add(teacher);
				this.rowData2.add(v);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}catch(UnsupportedEncodingException e) {
			e.printStackTrace();
		}finally {
			this.closeConn();
		}
	}
	public void initialHead() {//初始化表头
		this.columnNames1.add("课程号");this.columnNames1.add("课程名");
		this.columnNames1.add("学分");this.columnNames1.add("所属专业");
		this.columnNames2.add("课程号");this.columnNames2.add("课程名");
		this.columnNames2.add("星期");this.columnNames2.add("讲次");
		this.columnNames2.add("任课老师");
	}
	public void initialFrame() {//初始化窗体
		this.setLayout(null);
		jt1=new JTable(new DefaultTableModel(rowData1,columnNames1));
		jt2=new JTable(new DefaultTableModel(rowData2,columnNames2));
		jsp1=new JScrollPane(jt1);//将表格加入滚动窗体
		jsp2=new JScrollPane(jt2);
		//标签
		jlArray[0].setBounds(30,10,150,30);this.add(jlArray[0]);//现有课程表
		jlArray[1].setBounds(30,210,150,30);this.add(jlArray[1]);//已安排课程表
		jlArray[2].setBounds(30,430,200,30);this.add(jlArray[2]);//请输入要安排的课程号
		jlArray[3].setBounds(430,430,60,30);this.add(jlArray[3]);//老师
		jlArray[4].setBounds(30,470,100,30);this.add(jlArray[4]);//上课时间
		jlArray[5].setBounds(150,470,60,30);this.add(jlArray[5]);//星期
		jlArray[6].setBounds(260,470,60,30);this.add(jlArray[6]);//讲次
		jlArray[7].setBounds(30,510,200,30);this.add(jlArray[7]);//请输入要移除的课程号
		jlArray[8].setBounds(30,550,60,30);this.add(jlArray[8]);//星期
		jlArray[9].setBounds(140,550,60,30);this.add(jlArray[9]);//讲次
		//表格
		jsp1.setBounds(30,45,600,170);this.add(jsp1);//现有课程列表
		jsp2.setBounds(30,245,600,170);this.add(jsp2);//已安排课程列表
		//文本框
		jtfArray[0].setBounds(230,430,180,30);this.add(jtfArray[0]);//要安排的课程号
		jtfArray[1].setBounds(480,430,150,30);this.add(jtfArray[1]);//老师
		jtfArray[2].setBounds(230,510,180,30);this.add(jtfArray[2]);//要移除的课程号
		//下拉列表
		jcbArray[0].setBounds(180,470,60,25);this.add(jcbArray[0]);//星期
		jcbArray[1].setBounds(290,470,60,25);this.add(jcbArray[1]);//讲次
		jcbArray[2].setBounds(60,550,60,25);this.add(jcbArray[2]);//星期
		jcbArray[3].setBounds(170,550,60,25);this.add(jcbArray[3]);//讲次
		//按钮
		jbArray[0].setBounds(370,470,100,25);this.add(jbArray[0]);//提交课程
		jbArray[1].setBounds(250,550,100,30);this.add(jbArray[1]);//移除课程
		jbArray[2].setBounds(530,470,100,50);this.add(jbArray[2]);//允许选课
		jbArray[3].setBounds(530,530,100,50);this.add(jbArray[3]);//停止选课
	}
	public void initialListener() {//为按钮注册事件监听器
		for(int i=0;i<jbArray.length;i++)
			jbArray[i].addActionListener(this);
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==jbArray[0]) {//提交课程
			this.submitCourse();
		}else if(e.getSource()==jbArray[1]) {//移除课程
			this.removeCourse();
		}else if(e.getSource()==jbArray[2]) {//允许选课
			this.permitChose();
		}else if(e.getSource()==jbArray[3]) {//停止选课
			this.stopChose();
		}
	}
	public void submitCourse() {//提交课程
		try {
			this.initialConnection();//连接数据库
			String cou_id=jtfArray[0].getText().trim();//课程ID
			if(cou_id.equals("")) {
				JOptionPane.showMessageDialog(this, "请输入课程号","错误",JOptionPane.ERROR_MESSAGE);
				return ;
			}else {
				Vector<String> v=new Vector<String>();
				String sql1="select dept_name,cou_name from dept,course "+
						"where dept.dept_id=course.dept_id and "+
						"course.cou_id='"+cou_id+"' and course.coll_id='"+coll_id+
						"';";
				rs=stmt.executeQuery(sql1);
				if(rs.next()) {
					String dept_name=new String(rs.getString(1).getBytes("utf8"));
					String cou_name=new String(rs.getString(2).getBytes("utf8"));
					v.add(dept_name);v.add(cou_name);
				}else {
					JOptionPane.showMessageDialog(this, "本学院没有该课程","错误",JOptionPane.ERROR_MESSAGE);
					return ;
				}
				rs.close();
				String cou_day=(String)jcbArray[0].getSelectedItem();//获得开课星期
				String cou_time=(String)jcbArray[1].getSelectedItem();//获得讲次
				String teacher=jtfArray[1].getText().trim();//教师姓名
				if(teacher.equals("")) {
					JOptionPane.showMessageDialog(this, "请输入教师","错误",JOptionPane.ERROR_MESSAGE);
					return ;
				}
				String sql="insert into courseinfo values('"+cou_id+"','"+cou_day+"','"+
						cou_time+"','"+new String(teacher.getBytes("utf8"))+"','0');";
				int i=stmt.executeUpdate(sql);//执行更新
				if(i!=1) {//添加课程失败
					JOptionPane.showMessageDialog(this,"更新失败，请检查课程是否重复","错误",JOptionPane.ERROR_MESSAGE);
					return ;
				}else {//添加课程成功
					v.add(cou_day);v.add(cou_time);v.add(teacher);
					DefaultTableModel temp=(DefaultTableModel)jt2.getModel();
					((Vector)temp.getDataVector()).add(v);//添加数据至表格模型
					//更新表格
					((DefaultTableModel)jt2.getModel()).fireTableStructureChanged();
				}
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}catch(UnsupportedEncodingException e) {
			e.printStackTrace();
		}finally {
			this.closeConn();
		}
	}
	public void removeCourse() {//移除课程
		try {
			this.initialConnection();
			String cou_id=jtfArray[2].getText().trim();//要移除的课程ID
			if(cou_id.equals("")) {
				JOptionPane.showMessageDialog(this, "请输入课程号","错误",JOptionPane.ERROR_MESSAGE);
				return ;
			}
			String sql="delete from courseinfo where cou_id='"+cou_id+"';";
			int i=stmt.executeUpdate(sql);
			if(i!=1) {
				JOptionPane.showMessageDialog(this, "移除失败，请检查课程号","错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			String sql1="select courseinfo.cou_id,cou_name,weekNum,courseNum,teacher from course,"+
					"courseinfo where courseinfo.cou_id='"+cou_id+"' and course.id=courseinfo.id";
			ResultSet rs=stmt.executeQuery(sql1);
			Vector<String> v=new Vector<String>();
			if(rs.next()) {
				String cou_name=new String(rs.getString(2).getBytes("utf8"));
				String weekNum=rs.getString(3);
				String courseNum=rs.getString(4);
				String teacher=new String(rs.getString(5).getBytes("utf8"));
				v.add(cou_id);v.add(cou_name);v.add(weekNum);v.add(courseNum);v.add(teacher);
			}
			DefaultTableModel temp=(DefaultTableModel)jt2.getModel();
			temp.getDataVector().add(v);
			((DefaultTableModel)jt2.getModel()).fireTableStructureChanged();
		}catch(SQLException e) {
			e.printStackTrace();
		}catch(UnsupportedEncodingException e) {
			e.printStackTrace();
		}finally {
			this.closeConn();
		}
	}
	public void permitChose() {//选课时间控制
		String sql="update courseinfo,course set onchosing='1' where "+
					"couseinfo.cou_id=course.cou_id and course.coll_id='"+coll_id+"';";
		try {
			this.initialConnection();
			stmt.executeUpdate(sql);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			this.closeConn();
		}
	}
	public void stopChose() {
		String sql="update courseinfo,course set onchosing='0' where "+
					"courseinfo.cou_id=course.cou_id and course.coll_id='"+coll_id+"';";
		try {
			this.initialConnection();
			stmt.executeUpdate(sql);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			this.closeConn();
		}
	}
	public void updateTable() {//更新表格1数据
		this.initialConnection();
		rowData1.removeAllElements();
		this.initialData1();
		((DefaultTableModel)jt1.getModel()).setDataVector(rowData1,columnNames1);
		((DefaultTableModel)jt1.getModel()).fireTableStructureChanged();
		this.closeConn();
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
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void closeConn() {
		try {
			if(rs!=null) rs.close();
			if(stmt!=null) stmt.close();
			if(conn!=null) conn.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void setFocus() {
		this.jtfArray[0].requestFocus(true);
	}
	public static void main(String[] args) {
		CourseManage cm=new CourseManage("01","127.0.0.1:3306");
		JFrame jf=new JFrame();
		jf.setBounds(10,10,700,650);
		jf.add(cm);
		jf.setVisible(true);
		
	}
}




