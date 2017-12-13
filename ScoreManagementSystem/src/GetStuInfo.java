//基本信息查询的辅助类
//根据学号查找学生信息
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Date;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class GetStuInfo{
	private String host;
	private Connection conn;
	private Statement stmt;
	private ResultSet rs;
	public GetStuInfo(String host) {
		this.host=host;
	}
	public String[] getBaseInfo(String stu_id) {
		String[] message =new String[13];
		try {
			this.initialConnection();
			String sql="select stu_id,stu_name,stu_gender,stu_birth,"+
					"nativeplace,coll_name,dept_name,class_name,cometime from "+
					"student,dept,college,class "+
					"where stu_id='"+stu_id+"' and student.coll_id=college.coll_id "+
					"and student.dept_id=dept.dept_id "+
					"and student.class_id=class.class_id;";
			rs=stmt.executeQuery(sql);
			if(rs.next()) {
				message[0]=rs.getString(1);//学号
				message[1]=new String(rs.getString(2).getBytes("utf8"));//姓名
				message[2]=new String(rs.getString(3).getBytes("utf8"));//性别
				Date stu_birth=rs.getDate(4);
				message[3]=stu_birth.toString().substring(0, 4);//出生年份
				message[4]=stu_birth.toString().substring(5, 7);//出生月份
				message[5]=stu_birth.toString().substring(8, 10);//出生日
				message[6]=new String(rs.getString(5).getBytes("utf8"));//籍贯
				message[7]=new String(rs.getString(6).getBytes("utf8"));//学院名称
				message[8]=new String(rs.getString(7).getBytes("utf8"));//专业名称
				message[9]=new String(rs.getString(8).getBytes("utf8"));//班级名称
				Date cometime=rs.getDate(9);
				message[10]=cometime.toString().substring(0, 4);
				message[11]=cometime.toString().substring(5, 7);
				message[12]=cometime.toString().substring(8, 10);
			}
			this.closeConn();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return message;
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
			if(rs!=null) rs.close(); //关闭结果集
			if(stmt!=null) stmt.close(); //关闭语句
			if(conn!=null) conn.close(); //关闭连接
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
}