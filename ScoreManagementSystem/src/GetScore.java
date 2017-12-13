//查询成绩辅助类
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;
public class GetScore{
	private String host;
	private Connection conn;
	private Statement stmt;
	private ResultSet rs;
	private Vector<Vector<String>> v=new Vector<Vector<String>>();//存放返回结果
	private double xuefen;
	public GetScore(String host) {
		this.host=host;
	}
	public Vector<Vector<String>> getAllScore(String stu_id) {
		v.removeAllElements();
		try {
			this.initialConnection();//初始化数据库连接
			String sql="select course.cou_name,grade.score,course.xuefen from "+
					"course,grade where grade.stu_id='"+stu_id+"' and grade.isdual=1 and "+
					"grade.cou_id=course.cou_id order by score desc;";
			rs=stmt.executeQuery(sql);
			while(rs.next()) {
				Vector<String> temp=new Vector<String>(); //临时向量
				String cou_name=new String(rs.getString(1).getBytes("utf8"));
				String score=rs.getDouble(2)+"";
				String xuefen=rs.getDouble(3)+"";
				temp.add(cou_name);
				temp.add(score);
				temp.add(xuefen); 
				v.add(temp);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			this.closeConn();
		}
		return v;
	}
	public Vector<Vector<String>> getFailScore(String stu_id) {//不及格课程信息
		v.removeAllElements();
		try {
			this.initialConnection();
			String sql="select course.cou_name,grade.score,course.xuefen from "+
					"course,grade where grade.stu_id='"+stu_id+"' and grade.cou_id=course.cou_id "+
					"and score<60 "+"and grade.isdual=1;";
			rs=stmt.executeQuery(sql);
			while(rs.next()) {
				Vector<String> temp=new Vector<String>();
				String cou_name=new String(rs.getString(1).getBytes("utf8"));
				String score=rs.getDouble(2)+"";
				String xuefen=rs.getDouble(3)+"";
				temp.add(cou_name);
				temp.add(score);
				temp.add(xuefen);
				v.add(temp);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			this.closeConn();
		}
		return v;
	}
	public double getXueFen(String stu_id) {//根据学号获得所修学分
		try {
			this.initialConnection();
			String sql="select sum(xuefen) from course,grade where stu_id='"+
					stu_id+"' and grade.cou_id=course.cou_id and grade.score>60 and isdual=1;";
			rs=stmt.executeQuery(sql);
			if(rs.next()) {
				xuefen=rs.getDouble(1);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			this.closeConn();
		}
		return xuefen;
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
}






