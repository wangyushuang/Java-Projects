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
	private Vector<Vector> v=new Vector<Vector>();//存放返回结果
	private double xuefen;
	public GetScore(String host) {
		this.host=host;
	}
	public Vector getAllScore(String stu_id) {
		v.removeAllElements();
		try {
			this.initialConnection();//初始化数据库连接
			
		}
		return v;
	}
}