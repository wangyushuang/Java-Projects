import java.awt.Font;
import java.awt.event.*;
import java.io.UnsupportedEncodingException;

import javax.swing.*;
import java.sql.*;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.HashMap;
public class NewClass extends JPanel implements ActionListener{
	private JLabel[] jlArray= {new JLabel("专  业"),new JLabel("班  号"),new JLabel("班  名")};
	private JComboBox jcb=new JComboBox();
	private JTextField[] jtfArray= {new JTextField(),new JTextField()};
	private JButton jb1=new JButton("提交");
	private JButton jb2=new JButton("重置");
	private String coll_id;//学院号
	private String host;
	private Connection conn;
	private Statement stmt;
	private ResultSet rs;
	private Map<String,String> map_dept=new HashMap<String,String>();//专业名-专业号
	public NewClass(String coll_id, String host) {
		this.host=host;
		this.coll_id=coll_id;
		this.initialData();
		this.initialFrame();
		this.addListener();
	}
	public void initialData() {//根据学院号，初始化专业列表
		try {
			String sql="select dept_name,dept_id from dept where coll_id='"+coll_id+"';";
			this.initialConnection();
			rs=stmt.executeQuery(sql);
			while(rs.next()) {
				String dept_name=new String(rs.getString(1).getBytes("utf8"));
				String dept_id=rs.getString(2);
				map_dept.put(dept_name, dept_id);
				jcb.addItem(dept_name);
			}
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
		jcb.setBounds(250,100,300,40);jcb.setFont(font);this.add(jcb);
		jtfArray[0].setBounds(250,200,300,40);jtfArray[0].setFont(font);this.add(jtfArray[0]);
		jtfArray[1].setBounds(250,300,300,40);jtfArray[1].setFont(font);this.add(jtfArray[1]);
		jb1.setBounds(120, 400, 150, 50);jb1.setFont(font);this.add(jb1);
		jb2.setBounds(370, 400, 150, 50);jb2.setFont(font);this.add(jb2);
	}
	public void addListener() {
		jcb.addActionListener(this);
		jtfArray[0].addActionListener(this);
		jtfArray[1].addActionListener(this);
		jb1.addActionListener(this);
		jb2.addActionListener(this);
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==jcb) {//选择专业
			jtfArray[0].requestFocus(true);
		}else if(e.getSource()==jtfArray[0]) {//填写好班号
			jtfArray[1].requestFocus(true);
		}else if(e.getSource()==jtfArray[1]) {//填写好班名
			jb1.requestFocus(true);
		}else if(e.getSource()==jb1) {//点击提交按钮
			String dept_name=(String)jcb.getSelectedItem();
			if(dept_name.equals("")) {
				JOptionPane.showMessageDialog(this,"请选择专业","错误",JOptionPane.ERROR_MESSAGE);
				return ;
			}
			String class_id=jtfArray[0].getText().trim();
			if(class_id.equals("")) {
				JOptionPane.showMessageDialog(this, "请输入班号","错误",JOptionPane.ERROR_MESSAGE);
				jtfArray[0].requestFocus(true);
				return ;
			}
			String class_name=jtfArray[1].getText().trim();
			if(class_name.equals("")) {
				JOptionPane.showMessageDialog(this, "请输入班级名称","错误",JOptionPane.ERROR_MESSAGE);
				jtfArray[1].requestFocus(true);
				return ;
			}
			try {
				//根据专业号获得专业ID
				String dept_id=map_dept.get(dept_name);				
				String sql="insert into class values('"+class_id+",',"+dept_id+"','"+this.coll_id+
							"','"+class_name+"');";
				int i=stmt.executeUpdate(sql);
				if(i==1) {
					JOptionPane.showMessageDialog(this, "添加成功","提示",JOptionPane.INFORMATION_MESSAGE);
					return ;
				}else {
					JOptionPane.showMessageDialog(this, "添加失败，请检查","错误",JOptionPane.ERROR_MESSAGE);
				}
			}catch(SQLException ea) {
				ea.printStackTrace();
			}catch(Exception eb) {
				eb.printStackTrace();
			}finally {
				this.closeConn();
			}
		}else if(e.getSource()==jb2) {//点击重置按钮
			jtfArray[0].setText("");
			jtfArray[1].setText("");
			jtfArray[0].requestFocus(true);
		}
	}
	public void GetFocus() {
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
	public void setFocus() {
		jtfArray[0].requestFocus(true);
	}
	public static void main(String[] args) {
		NewClass nc=new NewClass("01","127.0.0.1:3306");
		JFrame jf=new JFrame();
		jf.setBounds(10,10,700,650);
		jf.add(nc);
		jf.setVisible(true);
	}
}