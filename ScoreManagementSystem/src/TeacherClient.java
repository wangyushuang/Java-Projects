import java.awt.*;
import java.awt.event.*; 
import javax.swing.*; 
import javax.swing.tree.*;
public class TeacherClient extends JFrame{
	private static final long serialVersionUID=1L;//序列化时保持版本的兼容性，即版本升级时保持对象的唯一性
	public String host;//数据库主机IP+":"+端口号
	String coll_id;//学院编号
	private DefaultMutableTreeNode dmtnRoot=//创建根节点
			new DefaultMutableTreeNode(new MyNode("操作选项","0"));
	private DefaultMutableTreeNode dmtn1=//创建系统选项节点
			new DefaultMutableTreeNode(new MyNode("系统选项","1"));
	private DefaultMutableTreeNode dmtn2=//创建学生信息管理节点
			new DefaultMutableTreeNode(new MyNode("学生信息管理","2"));
	private DefaultMutableTreeNode dmtn3=//创建课程管理节点
			new DefaultMutableTreeNode(new MyNode("课程管理","3"));
	private DefaultMutableTreeNode dmtn4=//创建班级设置节点
			new DefaultMutableTreeNode(new MyNode("班级设置","4"));
	private DefaultMutableTreeNode dmtn11=//创建退出节点
			new DefaultMutableTreeNode(new MyNode("退出","11"));
	private DefaultMutableTreeNode dmtn13=//创建密码修改节点
			new DefaultMutableTreeNode(new MyNode("密码修改","13"));
	private DefaultMutableTreeNode dmtn21=//创建新生报到节点
			new DefaultMutableTreeNode(new MyNode("新生报到","21"));
	private DefaultMutableTreeNode dmtn22=//创建学生信息查询节点
			new DefaultMutableTreeNode(new MyNode("学生信息查询","22"));
	private DefaultMutableTreeNode dmtn221=//创建基本信息查询节点
			new DefaultMutableTreeNode(new MyNode("基本信息查询","221"));
	private DefaultMutableTreeNode dmtn222=//创建成绩查询节点
			new DefaultMutableTreeNode(new MyNode("成绩查询","222"));
	private DefaultMutableTreeNode dmtn31=//创建开课选项设置节点
			new DefaultMutableTreeNode(new MyNode("开课选项设置","31"));
	private DefaultMutableTreeNode dmtn32=//课程成绩录入节点
			new DefaultMutableTreeNode(new MyNode("课程成绩录入","32"));
	private DefaultMutableTreeNode dmtn34=//创建添加课程
			new DefaultMutableTreeNode(new MyNode("添加课程","34"));
	private DefaultMutableTreeNode dmtn42=//创建添加班级节点
			new DefaultMutableTreeNode(new MyNode("添加班级","42"));
	private DefaultTreeModel dtm=new DefaultTreeModel(dmtnRoot);//创建根节点
	private JTree jt=new JTree(dtm);//创建树状列表控件
	private JScrollPane jspz=new JScrollPane(jt);//创建滚动窗口
	private JPanel jpy=new JPanel();
	private JSplitPane jsp1=new JSplitPane(//创建分割窗格
			JSplitPane.HORIZONTAL_SPLIT,jspz,jpy);
	//声明功能模块引用
	private ChangePwdTeacher changepwdteacher;
	private NewStu newstu;
	private TeachSearchInfo teachsearchinfo;
	private StuScore stuscore;
	
	CardLayout cl;
	public TeacherClient(String coll_id,String host){ 
		this.host=host;
		this.coll_id=coll_id;
		this.initialTree();
		this.initialPanel();
		this.addListener();
		this.initialJpy();
		this.initialFrame();
	}
	public void initialPanel(){
		//初始化各个功能模块
		changepwdteacher=new ChangePwdTeacher(host);
		newstu=new NewStu("01",host);//第一个参数是专业号
		teachsearchinfo=new TeachSearchInfo(host);
		stuscore=new StuScore(host);
	}
	public void initialTree(){
		dmtnRoot.add(dmtn1);
		dmtnRoot.add(dmtn2);
		dmtnRoot.add(dmtn3);
		dmtnRoot.add(dmtn4);
		dmtn1.add(dmtn11);
		dmtn1.add(dmtn13);
		dmtn2.add(dmtn21);
		dmtn2.add(dmtn22);
		dmtn22.add(dmtn221);
		dmtn22.add(dmtn222);
		dmtn3.add(dmtn31);
		dmtn3.add(dmtn32);
		dmtn3.add(dmtn34);
		dmtn4.add(dmtn42);
	}
	public void initialJpy(){//将各功能模块添加到面板中
		jpy.setLayout(new CardLayout());
		cl=(CardLayout)jpy.getLayout();
		jpy.add(changepwdteacher,"changepwdteacher");
		jpy.add(newstu,"newstu");
		jpy.add(teachsearchinfo, "teachsearchinfo");
		jpy.add(stuscore,"stuscore");
	}
	public void initialFrame(){
		this.add(jsp1);
		jsp1.setDividerLocation(200);
		jsp1.setDividerSize(4);
//		Image image=new ImageIcon("ico.gif").getImage();
//		this.setIconImage(image);
		this.setTitle("教师客户端");
		Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
		int centerX=screenSize.width/2;
		int centerY=screenSize.height/2;
		int w=900;
		int h=650;
		this.setBounds(centerX-w/2,centerY-h/2-30,w,h);
		this.setVisible(true);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}
	public void addListener(){
		jt.addMouseListener(
			new MouseAdapter(){
				public void mouseClicked(MouseEvent e){
					DefaultMutableTreeNode dmtntemp=(DefaultMutableTreeNode)jt.getLastSelectedPathComponent();
					MyNode mynode=(MyNode)dmtntemp.getUserObject();
					String id=mynode.getId();
					if(id.equals("0")){/*欢迎页面*/}
					else if(id.equals("11")){
						//退出系统
						int i=JOptionPane.showConfirmDialog(jpy,"您确定退出系统吗？","询问",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
						if(i==0){System.exit(0);}
					}else if(id.equals("13")){//更改密码
						cl.show(jpy, "changepwdteacher");
						changepwdteacher.setFocus();
					}else if(id.equals("21")) {//添加学生
						cl.show(jpy, "newstu");
						newstu.setFocus();
					}else if(id.equals("221")) {//学生信息查询
						cl.show(jpy, "teachsearchinfo");
						teachsearchinfo.setFocus();
					}else if(id.equals("222")) {//成绩查询
						cl.show(jpy, "stuscore");
						stuscore.setFocus();
					}else if(id.equals("31")) {//选课管理
						
					}else if(id.equals("32")) {//成绩录入
						
					}else if(id.equals("34")) {//添加课程
						
					}else if(id.equals("42")) {//添加班级
						
					}
				}
			});
		jt.setToggleClickCount(1);
	}
	class MyNode{
		private String values;
		private String id;
		public MyNode(String values,String id){
			this.values=values;
			this.id=id;
		}
		public String toString(){ 
			return this.values;
		}
		public String getId(){
			return this.id;
		}
	}
	public static void main(String[] args){
		new TeacherClient("01","127.0.0.1:3306");
	}
}