//将成绩录入数据库
import javax.swing.event.*;
import java.awt.event.*;
import java.util.Vector;
import java.util.Map;
import java.util.HashMap;
import javax.swing.*;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;
import javax.swing.table.*;
import java.io.UnsupportedEncodingException;
public class GradeInDB extends JPanel implements ActionListener{
	private String host;
	private Connection conn;
	private Statement stmt;
	private ResultSet rs;
	private String coll_id;//学院号
	private Map<String,String> map_dept=new HashMap<String,String>();//
	private Vector<String> v_dept=new Vector<String>();//课程列表
	private JComboBox<Vector> jcb=new JComboBox(v_dept);
	private JLabel jl=new JLabel("请选择课程：");
	private Vector<String> v_head=new Vector<String>();//表头
	private Vector<Vector<String>> v_data=new Vector<Vector<String>>();//表格数据
	private JTable jt;//声明表格引用
	private JScrollPane jsp;//声明滚动窗体
	//创建动作按钮，只有公布后的成绩学生才可见
	private JButton jb=new JButton("公布该科成绩");
	public GradeInDB(String coll_id, String host) {
		this.host=host;
		this.coll_id=coll_id;
		this.initialData();//初始化数据
		this.initialFrame();//初始化窗体
		this.initialListener();//注册监听器 
	}
	public void initialData() {//初始化数据
		v_head.add("课程号");
		v_head.add("学分");
		v_head.add("姓名");
		v_head.add("成绩(分)");
		String sql="select distinct cou_name,course.cou_id from course,grade where "+
					"course.coll_id='"+coll_id+"' and course.cou_id=grade.cou_id and isdual='0'";
		try {
			this.initialConnection();
			rs=stmt.executeQuery(sql);
			while(rs.next()) {
				String cou_name=new String(rs.getString(1).getBytes("utf8"));
				String cou_id=rs.getString(2);
				map_dept.put(cou_name,cou_id);
				v_dept.add(cou_name);//添加课程名称
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}catch(UnsupportedEncodingException e){
			e.printStackTrace();
		}finally {
			this.closeConn();
		}
	}
	public void initialFrame() {
		this.setLayout(null);
		jl.setBounds(30,20,80,30);this.add(jl);
		jcb.setBounds(120,20,150,30);this.add(jcb);
		jb.setBounds(350, 20, 150, 30);this.add(jb);
		jt=new JTable(new DefaultTableModel(v_data,v_head));
		jsp=new JScrollPane(jt);
		jsp.setBounds(30,70,500,500);this.add(jsp);
	}
	public void initialListener() {
		jcb.addActionListener(this);
		jb.addActionListener(this);
		TableChangeListener tl=new TableChangeListener(stmt);//创建表格监听器对象
		jt.getSelectionModel().addListSelectionListener(tl);
		jt.getColumnModel().addColumnModelListener(tl);
		jt.getModel().addTableModelListener(tl);
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==jcb) {//下拉列表选择内容变化
			v_data.removeAllElements();
			String cur_cou_name=(String)jcb.getSelectedItem();//课程名称
			String cur_cou_id=(String)map_dept.get(cur_cou_name);//课程ID
			String sql="select grade.cou_id,grade.stu_id,student.stu_name,score from "+
						"grade,student where grade.stu_id=student.stu_id and isdual='0'"+
						"and grade.cou_id='"+cur_cou_id+"';";
			try {
				this.initialConnection();
				rs=stmt.executeQuery(sql);
				while(rs.next()) {
					Vector<String> v=new Vector<String>();
					String cou_id=rs.getString(1);
					String stu_id=rs.getString(2);
					String stu_name=new String(rs.getString(3).getBytes("utf8"));
					String score=rs.getDouble(4)+"";
					v.add(cou_id);v.add(stu_id);v.add(stu_name);v.add(score);
					v_data.add(v);
				}
				DefaultTableModel temp1=(DefaultTableModel)jt.getModel();
				temp1.setDataVector(v_data,v_head);
				temp1.fireTableStructureChanged();
			}catch(SQLException ea) {
				ea.printStackTrace();
			}catch(UnsupportedEncodingException eb) {
				eb.printStackTrace();
			}finally {
				this.closeConn();
			}
		}else if(e.getSource()==jb){//按下“公布该成绩单”按钮
			try {
				String cur_cou_name=(String)jcb.getSelectedItem();
				if(cur_cou_name==null) {
					JOptionPane.showMessageDialog(this, "请选择课程","错误",JOptionPane.ERROR_MESSAGE);
					return ;
				}else {
					this.initialConnection();
					String cur_cou_id=(String)map_dept.get(cur_cou_name);
					String sql="update grade set isdual='1' where cou_id='"+
								cur_cou_id+"' and isdual='0';";
					int i=stmt.executeUpdate(sql);
				}
			}catch(SQLException ea) {
				ea.printStackTrace();
			}finally {
				this.closeConn();
			}
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
		this.jb.requestFocus(true);
	}
	class TableChangeListener implements ListSelectionListener,TableModelListener,
						TableColumnModelListener{
		int rowNum,colNum;//声明行数和列数
		Statement statement;//声明语句引用
		public TableChangeListener(Statement statement) {
			this.statement=statement;
		}
		public void valueChanged(ListSelectionEvent e) {//更新行值
			rowNum=jt.getSelectedRow();
		}
		public void columnSelectionChanged(ListSelectionEvent e) {//更新列值
			colNum=jt.getSelectedColumn();
		}
		public void tableChanged(TableModelEvent e) {
			if(colNum==3) {//更改“分数”列
				String str=(String)jt.getValueAt(rowNum, colNum);//获得输入数据
				String cou_id=(String)jt.getValueAt(rowNum,0);//获得课程号
				String stu_id=(String)jt.getValueAt(rowNum, 1);//获得学号
				try {
					Double d=Double.parseDouble(str);//分数
					if(d<0 || d>100) {//分数超过范围
						jt.setValueAt("0", rowNum, colNum);
					}
				}catch(Exception ea) {
					jt.setValueAt("0", rowNum, colNum);//不是数字
				}
				//更新到数据库
				String sql="update grade set score='"+str+"' where cou_id='"+cou_id+
							"' and stu_id='"+stu_id+"';";
				try {
					statement.executeUpdate(sql);					
				}catch(SQLException ea) {
					ea.printStackTrace();
				}
			}
		}
		//实现接口中的方法
		public void columnMoved(TableColumnModelEvent e) {
			
		}
		public void columnRemoved(TableColumnModelEvent e) {
			
		}
		public void columnAdded(TableColumnModelEvent e) {
			
		}
		public void columnMarginChanged(ChangeEvent e){
			
		}
	}
	public static void main(String[] args) {
		GradeInDB gi=new GradeInDB("01","127.0.0.1:3306");
		JFrame jf=new JFrame();
		jf.setBounds(10,10,700,650);
		jf.add(gi);
		jf.setVisible(true);
	}
}




