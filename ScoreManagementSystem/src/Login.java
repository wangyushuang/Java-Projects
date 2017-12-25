import javax.swing.*;
import java.sql.*;
import java.awt.event.*;
import java.awt.*;
public class Login extends JFrame implements ActionListener{
	private String host;
	private Connection conn;
	private Statement stmt;
	private ResultSet rs;
	private JPanel jp=new JPanel();
	private JLabel[] jlArray= {new JLabel("数据库IP"),new JLabel("端  口  号"),new JLabel("用  户  名"),
			new JLabel("密        码"),new JLabel("")};
	private JTextField jtfHostPort=new JTextField();//主机端口号输入框
	private JTextField jtfHostAddr=new JTextField();//主机地址输入框
	private JTextField jtfName=new JTextField();//用户名输入框
	private JPasswordField jPwd=new JPasswordField();//用户密码
	private JRadioButton[] jrbArray= {new JRadioButton("学生"),new JRadioButton("教师")};
	private ButtonGroup bg=new ButtonGroup();
	private JButton jb1=new JButton("登 陆");
	private JButton jb2=new JButton("重 置");
	public Login() {
		this.addListener();
		this.initialFrame();
	}
	public void addListener() {
		this.jb1.addActionListener(this);
		this.jb2.addActionListener(this);
		this.jtfName.addActionListener(this);
		this.jPwd.addActionListener(this);
		this.jtfHostPort.addActionListener(this);
		this.jtfHostAddr.addActionListener(this);
	}
	public void initialFrame() {
		jp.setLayout(null);
		Font font=new Font("楷书",Font.BOLD,16);
		bg.add(jrbArray[0]);bg.add(jrbArray[1]);
		jrbArray[0].setSelected(true);//默认为学生登陆
		//标签
		this.jlArray[0].setBounds(30,20,110,30);this.jlArray[0].setFont(font);this.jp.add(jlArray[0]);
		this.jlArray[1].setBounds(30, 60, 110, 30);this.jlArray[1].setFont(font);this.jp.add(jlArray[1]);
		this.jlArray[2].setBounds(30, 100, 110, 30);this.jlArray[2].setFont(font);this.jp.add(jlArray[2]);
		this.jlArray[3].setBounds(30,140,110,30);this.jlArray[3].setFont(font);this.jp.add(jlArray[3]);
		this.jlArray[4].setBounds(80, 180, 220, 30);this.jlArray[4].setFont(font);this.jp.add(jlArray[4]);//正在登陆
		//按钮
		this.jrbArray[0].setBounds(80,220,70,30);this.jrbArray[0].setFont(font);this.jp.add(jrbArray[0]);
		this.jrbArray[1].setBounds(150,220,80,30);this.jrbArray[1].setFont(font);this.jp.add(jrbArray[1]);
		this.jb1.setBounds(40, 270, 80, 30);this.jb1.setFont(font);this.jp.add(jb1);
		this.jb2.setBounds(160, 270, 80, 30);this.jb2.setFont(font);this.jp.add(jb2);
		//文本输入框
		this.jtfHostAddr.setBounds(110,20,150,30);this.jtfHostAddr.setFont(font);this.jp.add(jtfHostAddr);
		this.jtfHostPort.setBounds(110,60,150,30);this.jtfHostPort.setFont(font);this.jp.add(jtfHostPort);
		this.jtfName.setBounds(110,100,150,30);this.jtfName.setFont(font);this.jp.add(jtfName);
		this.jPwd.setBounds(110,140,150,30);this.jPwd.setFont(font);this.jp.add(jPwd);
		this.add(jp);
		this.setTitle("登陆");
//		Image image=new ImageIcon().getImage();
		this.setResizable(false);
		Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();//获得屏幕尺寸
		int centerX=screenSize.width/2;
		int centerY=screenSize.height/2;
		int w=300,h=350;
		this.setBounds(centerX-w/2,centerY-h/2,w,h);
		this.setVisible(true);
		this.jtfHostAddr.requestFocus(true);
		this.jtfHostAddr.setText("127.0.0.1");
		this.jtfHostPort.setText("3306");
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.jb1) {//按下登陆按钮
			this.jlArray[4].setText("正在验证，请稍后......");//设置提示信息
			String hostaddr=this.jtfHostAddr.getText().trim();//获得主机地址
			if(hostaddr.equals("")) {
				JOptionPane.showMessageDialog(this, "请输入主机地址","错误",JOptionPane.ERROR_MESSAGE);
				jlArray[4].setText("");
				return ;
			}
			String hostport=this.jtfHostPort.getText().trim();//获得主机端口号
			if(hostport.equals("")) {
				JOptionPane.showMessageDialog(this, "请输入主机端口号","错误",JOptionPane.ERROR_MESSAGE);
				jlArray[4].setText("");
				return ;
			}
			this.host=hostaddr+":"+hostport;//主机
			String name=this.jtfName.getText().trim();//获得用户名
			if(name.equals("")) {
				JOptionPane.showMessageDialog(this, "请输入用户名","错误",JOptionPane.ERROR_MESSAGE);
				jlArray[4].setText("");
				return ;
			}
			String pwd=String.valueOf(this.jPwd.getPassword());//密码
			if(pwd.equals("")) {
				JOptionPane.showMessageDialog(this, "请输入密码","错误",JOptionPane.ERROR_MESSAGE);
				jlArray[4].setText("");
				return ;
			}
			int type=this.jrbArray[0].isSelected()?0:1;//获取登陆类型
			try {
				this.initialConnection();
				if(type==0) {//学生登陆
					String sql="select * from user_stu where stu_id='"+name+"' and pwd='"+pwd+"';";
					rs=stmt.executeQuery(sql);
					if(rs.next()) {
						//登陆学生客户端
					}
				}else {//教师登陆
					String sql="select coll_id from user_teacher where uid='"+name+"' and pwd='"+pwd+"';";
					System.out.println(sql);
					rs=stmt.executeQuery(sql);
					if(rs.next()) {
						String coll_id=rs.getString(1);
						new TeacherClient(coll_id,host);
						this.dispose();//关闭登陆窗口并释放资源
					}else {
						JOptionPane.showMessageDialog(this,"用户名或密码错误","错误",JOptionPane.ERROR_MESSAGE);
						jlArray[4].setText("");
					}
				}
				this.closeConn();
			}catch(SQLException ea) {
				ea.printStackTrace();
			}
		}else if(e.getSource()==this.jb2) {//按下重置按钮
			this.jtfName.setText("");
			this.jPwd.setText("");
			this.jtfName.requestFocus(true);
		}else if(e.getSource()==this.jtfName) {//输入用户名并按下Enter键
			this.jPwd.requestFocus(true);
		}else if(e.getSource()==this.jPwd){//输入密码并按下Enter键
			this.jb1.requestFocus(true);
		}else if(e.getSource()==this.jtfHostAddr) {//输入主机地址并按下Enter键
			this.jtfHostPort.requestFocus(true);
		}else if(e.getSource()==this.jtfHostPort) {//输入端口号并按下Enter键
			this.jtfName.requestFocus(true);
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
		}catch(SQLException e) {
			e.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		Login login=new Login();
		login.setVisible(true);
	}
}