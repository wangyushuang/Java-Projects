import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
public class test{
	public static void main(String[] args) {
		Connection con;
		String driver="com.mysql.jdbc.Driver" ;//����������
		String url="jdbc:mysql://localhost:3306/emp"
				+ "?useUnicode=true&characterEncoding=utf-8&useSSL=false";//Ҫ���ʵ����ݿ���
		String user="root";//mysql�û���
		String pwd="000623";//mysql����
		try {
			//������������
			Class.forName(driver);//������������
			con=DriverManager.getConnection(url,user,pwd);//�������ݿ�
			if(!con.isClosed()) {
				System.out.println("�ɹ���������");
			}
			//����statement����ִ��sql���
			Statement statement=con.createStatement();
			String sql="select * from emp";
			ResultSet rs=statement.executeQuery(sql);
			System.out.println("----------------");
			System.out.println("����\tְ��");
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
			//���ݿ��������쳣����
			System.out.println("Sorry!can't find the driver.");
			e.printStackTrace();
		}catch(SQLException e) {
			e.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			System.out.println("���ݿ����ݻ�ȡ��ϣ�");
		}
	}
}