//添加课程
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.*;
import java.io.UnsupportedEncodingException;
import java.util.Vector;
public class NewCourse extends JPanel implements ActionListener{
	private String host;
	private Connection conn;
	private Statement stmt;
	private ResultSet rs;
	private String coll_id;//学院号
	Vector<String> v_dept=new Vector<String>();//存放专业名称
	private JLabel[] jlArray= {new JLabel("课 程 号"), new JLabel("课 程 名"), new JLabel("学    分"),
								new JLabel("所属专业")};
	private JTextField[] jtfArray= {new JTextField(),new JTextField(),new JTextField()};
	private JComboBox jcb=new JComboBox();//所属专业下拉列表
	JButton jb1=new JButton("添加");
	JButton jb2=new JButton("重置");
	public NewCourse(String coll_id, String host) {
		this.coll_id=coll_id;
		this.host=host;
		this.initialData();//初始化数据
		this.initialFrame();//初始化窗体
		this.addListener();//为控件注册监听器
	}
	public void initialData() {//初始化专业下拉列表
		try {
			this.initialConnection();
			String sql="select dept_name from dept where coll_id='"+coll_id+"';";
			rs=stmt.executeQuery(sql);
			while(rs.next()) {
				String dept_name=new String(rs.getString(1).getBytes("utf8"));
				v_dept.add(dept_name);
			}
			jcb=new JComboBox(v_dept);
		}catch(SQLException e) {
			e.printStackTrace();
		}catch(UnsupportedEncodingException e) {
			e.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			this.closeConn();
		}
	}
	public void initialFrame() {
		this.setLayout(null);
		Font font=new Font("黑体",Font.PLAIN,24);
		jlArray[0].setBounds(80,100,200,30);jlArray[0].setFont(font);this.add(jlArray[0]);
		jlArray[1].setBounds(80,200,200,30);jlArray[1].setFont(font);this.add(jlArray[1]);
		jlArray[2].setBounds(80,300,200,30);jlArray[2].setFont(font);this.add(jlArray[2]);
		jlArray[3].setBounds(80,400,100,30);jlArray[3].setFont(font);this.add(jlArray[3]);
		jtfArray[0].setBounds(300,100,300,40);jtfArray[0].setFont(font);this.add(jtfArray[0]);
		jtfArray[1].setBounds(300,200,300,40);jtfArray[1].setFont(font);this.add(jtfArray[1]);
		jtfArray[2].setBounds(300,300,300,40);jtfArray[2].setFont(font);this.add(jtfArray[2]);
		jcb.setBounds(300,400,300,40);jcb.setFont(font);this.add(jcb);
		jb1.setBounds(120, 500, 150, 50);jb1.setFont(font);this.add(jb1);
		jb2.setBounds(400, 500, 150, 50);jb2.setFont(font);this.add(jb2);
	}
	public void addListener() {
		jtfArray[0].addActionListener(this);
		jtfArray[1].addActionListener(this);
		jtfArray[2].addActionListener(this);
		jcb.addActionListener(this);
		jb1.addActionListener(this);
		jb2.addActionListener(this);
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==jb1) {//按下添加按钮，将数据同步到数据库
			String cou_id=jtfArray[0].getText().trim();//课程ID
			if(cou_id.equals("")) {
				JOptionPane.showMessageDialog(this, "请输入课程号","错误",JOptionPane.ERROR_MESSAGE);
				jtfArray[0].requestFocus(true);
				return ;
			}
			String cou_name=jtfArray[1].getText().trim();//课程名
			if(cou_name.equals("")) {
				JOptionPane.showMessageDialog(this, "请输入课程名称","错误",JOptionPane.ERROR_MESSAGE);
				jtfArray[1].requestFocus(true);
				return ;
			}
			String xuefen=jtfArray[2].getText().trim();//学分
			if(xuefen.equals("")) {
				JOptionPane.showMessageDialog(this, "请输入学分","错误",JOptionPane.ERROR_MESSAGE);
				jtfArray[2].requestFocus(true);
				return ;
			}
			String dept_name=(String)jcb.getSelectedItem();//专业名称
			if(dept_name.equals("")) {
				JOptionPane.showMessageDialog(this,"请选择专业","错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				this.initialConnection();
				String sql="select dept_id from dept where dept_name='"+dept_name+"';";
				rs=stmt.executeQuery(sql);
				if(rs.next()) {
					String dept_id=rs.getString(1);
					String sql1="insert into course values('"+cou_id+"','"+cou_name+"','"+xuefen+
								"','"+this.coll_id+"','"+dept_id+"');";
					int i=stmt.executeUpdate(sql1);
					if(i==1) {
						JOptionPane.showMessageDialog(this, "添加成功","提示",JOptionPane.INFORMATION_MESSAGE);
					}
					else {
						JOptionPane.showMessageDialog(this, "添加失败，请检查","错误",JOptionPane.ERROR_MESSAGE);
					}
				}
			}catch(SQLException ea) {
				ea.printStackTrace();
			}finally {
				this.closeConn();
			}
		}else if(e.getSource()==jb2) {//按下重置按钮
			jtfArray[0].setText("");
			jtfArray[1].setText("");
			jtfArray[2].setText("");
			jtfArray[0].requestFocus();
		}else if(e.getSource()==jtfArray[0]) {//输入课程号后按下Enter键
			this.jtfArray[1].requestFocus(true);
		}else if(e.getSource()==jtfArray[1]) {//输入课程名后按下Enter键
			this.jtfArray[2].requestFocus(true);
		}else if(e.getSource()==jtfArray[2]) {//输入学分后按下Enter键
			this.jcb.requestFocus(true);
		}else if(e.getSource()==jcb) {//选择专业后
			jb1.requestFocus(true);
		}
	}
	public void setFocus() {
		this.jtfArray[0].requestFocus(true);
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
		}catch(SQLException e) {
			e.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		NewCourse nc=new NewCourse("01","127.0.0.1:3306");
		JFrame jf=new JFrame();
		jf.setBounds(10,10,700,650);
		jf.add(nc);		
		jf.setVisible(true);
	}
}



