//选课设置代码主框架
import java.util.Vector;
import javax.swing.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
public class CourseManage extends JPanel implements ActionListener{
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
				new JLabel("请输入您要安排的课程的课程号："),new JLabel("老师："),new JLabel("上课时间"),
				new JLabel("星期"),new JLabel("讲次"),new JLabel("请输入您要移除的课程的课程号"),
				new JLabel("星期"),new JLabel("讲次")};
	private static final String[] weekNum= {"1","2","3","4","5","6","7"};//星期
	private static final String[] courseNum= {"1","2","3","4","5"};//讲次
	private JButton[] jbArray= {new JButton("提交该课程"),new JButton("移除该课程"),
				new JButton("允许选课"),new JButton("停止选课")};
	
}