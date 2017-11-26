import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
public class test{
	public static void main(String[] args) {
		Connection con;
		String driver="com.mysql.jdbc.Driver" ;//驱动程序名
		String url="jdbc:mysql://localhost:3306/emp"
				+ "?useUnicode=true&characterEncoding=utf-8&useSSL=false";//要访问的数据库名
		String user="root";//mysql用户名
		String pwd="000623";//mysql密码
		try {
			//加载驱动程序
			Class.forName(driver);//加载驱动程序
			con=DriverManager.getConnection(url,user,pwd);//连接数据库
			if(!con.isClosed()) {
				System.out.println("成功连接数库");
			}
			//创建statement对象，执行sql语句
			Statement statement=con.createStatement();
			String sql="select * from emp";
			ResultSet rs=statement.executeQuery(sql);
			System.out.println("----------------");
			System.out.println("姓名\t职称");
			System.out.println("----------------");
			String job;
			String id;
			while(rs.next()) {
				job=rs.getString("job");
				id=rs.getString("ename");
				System.out.println(id+"\t"+job);
			}
			System.out.println("----------------");
			rs.close();
			con.close();
		}catch(ClassNotFoundException e) {
			//数据库驱动类异常处理
			System.out.println("Sorry!can't find the driver.");
			e.printStackTrace();
		}catch(SQLException e) {
			e.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			System.out.println("数据库数据获取完毕！");
		}
	}
}