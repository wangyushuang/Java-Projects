//import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
public class ChangePwdTeacher extends JPanel implements ActionListener{
	private static final long serialVersionUID=1L;//序列化时保持版本的兼容性，即版本升级时保持对象的唯一性
	private String host;
	private Connection conn;//连接数据库
	private Statement stmt;//执行sql语句
	private ResultSet rs;//保存查询结果
	private JLabel[] jlArray={new JLabel("用户名"),new JLabel("原始密码"),new JLabel("新秘密"),new JLabel("确认新密码")};
	private JTextField jtf=new JTextField();
	private JPasswordField[] jpfArray={new JPasswordField(),new JPasswordField(),new JPasswordField()};
	private JButton[] jbArray={new JButton("确认"),new JButton("重置")};

	public ChangePwdTeacher(String host){
		this.host=host;
		this.initialFrame();
		this.addListener();
	}

	public void addListener(){
		jtf.addActionListener(this);
		jpfArray[0].addActionListener(this);
		jpfArray[1].addActionListener(this);
		jpfArray[2].addActionListener(this);
		jbArray[0].addActionListener(this);
		jbArray[1].addActionListener(this);
	}
	public void initialFrame(){
		this.setLayout(null);
		//将控件放入相应位置
		for(int i=0;i<jlArray.length;i++){
			jlArray[i].setBounds(30,20+50*i,150,30);
			this.add(jlArray[i]);
			if(i==0){
				jtf.setBounds(130,20+50*i,150,30);
				this.add(jtf);
			}
			else{
				jpfArray[i-1].setBounds(130,20+50*i,150,30);
				this.add(jpfArray[i-1]);
			}
		}
		jbArray[0].setBounds(40,230,100,30);
		this.add(jbArray[0]);
		jbArray[1].setBounds(170,230,100,30);
		this.add(jbArray[1]);
	}
	public void actionPerformed(ActionEvent e){
		if(e.getSource()==jtf){//输入用户名并按Enter键
			jpfArray[0].requestFocus(true);			
		}else if(e.getSource()==jpfArray[0])//输入原始密码并按Enter键
			jpfArray[1].requestFocus(true);
		else if(e.getSource()==jpfArray[1])//输入新密码
			jpfArray[2].requestFocus(true);
		else if(e.getSource()==jpfArray[2])//确认新密码
			jbArray[0].requestFocus(true);
		else if(e.getSource()==jbArray[1]){//重置
			for(int i=0;i<jpfArray.length;i++)
				jpfArray[i].setText("");//清空
			jtf.setText("");
		}else if(e.getSource()==jbArray[0]){//确认键 
			String patternStr="^[0-9A-Za-z]{1,16}$";
			String user_name=jtf.getText().trim();//获取用户名
			if(user_name.equals("")){
				JOptionPane.showMessageDialog(this,"请输入用户名","错误",JOptionPane.ERROR_MESSAGE);
				return ;
			}
			String oldPwd=String.valueOf(jpfArray[0].getPassword());
			if(oldPwd.equals("")){
				JOptionPane.showMessageDialog(this,"请输入原始密码","错误",JOptionPane.ERROR_MESSAGE);
				return ;
			}
			String newPwd=String.valueOf(jpfArray[1].getPassword());
			if(newPwd.equals("")){
				JOptionPane.showMessageDialog(this,"请输入新密码","错误",JOptionPane.ERROR_MESSAGE);
			}
			if(!newPwd.matches(patternStr)){
				JOptionPane.showMessageDialog(this,"密码只能是6-12位的字母或数字","错误",JOptionPane.ERROR_MESSAGE);
				return ;
			}
			String newPwd1=String.valueOf(jpfArray[2].getPassword());
			if(!newPwd1.equals(newPwd)){
				JOptionPane.showMessageDialog(this,"确认密码与新密码不符","错误",JOptionPane.ERROR_MESSAGE);
				return ;
			}
			try{//连接数据库并更改密码
				this.initialConnection();
				String sql="update user_teacher set pwd='"+newPwd +"'"+"where uid='"+user_name+"'"+"and pwd='"+oldPwd+"'";
				int i=stmt.executeUpdate(sql);
				if(i==0){
					JOptionPane.showMessageDialog(this,"修改失败，检查您的用户名或密码是否正确","错误",JOptionPane.ERROR_MESSAGE);
				}else if(i==1){
					JOptionPane.showMessageDialog(this,"密码修改成功","提示",JOptionPane.INFORMATION_MESSAGE);
				}
				this.closeConn();//断开连接
			}catch(Exception ea){
				ea.printStackTrace();
			}
		}

	}
	public void setFocus(){
		jtf.requestFocus(true);//用户名文本框得到焦点
	}
	public void initialConnection(){
		try{
			Class.forName("com.mysql.jdbc.Driver");//加载驱动
			String url="jdbc:mysql://"+host+"/test"
					+ "?useUnicode=true&characterEncoding=utf-8&useSSL=false";//要访问的数据库名
			//conn=DriverManager.getConnection("jdbc:mysql://"+host+"/test","","");//获得连接
			conn=DriverManager.getConnection(url,"root","000623");
//			if(!conn.isClosed()) {
//				System.out.println("成功连接数库");
//			}
//			else
//				System.out.println("未成功连接数库");
			stmt=conn.createStatement();
		}catch(SQLException e){
			e.printStackTrace();
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
	}
	public void closeConn(){
		try{
			if(rs!=null){rs.close();}
			if(stmt!=null){stmt.close();}
			if(conn!=null){conn.close();}
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	public static void main(String[] args){
		ChangePwdTeacher cpt=new ChangePwdTeacher("127.0.0.1:3306");
		JFrame jframe=new JFrame();
		jframe.add(cpt);
		jframe.setBounds(70,20,700,650);
		jframe.setVisible(true);
	}
}