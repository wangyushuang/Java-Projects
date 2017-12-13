import javax.swing.*;

import java.awt.Color;
import java.awt.event.*;
public class TeachSearchInfo extends JPanel implements ActionListener{
	private static final long serialVersionUID=1L;//序列化时保持版本的兼容性，即版本升级时保持对象的唯一性
	private String host;//主机地址
	private JLabel[] jlArray= {new JLabel("学号"),new JLabel("姓名"),
				new JLabel("性别"),new JLabel("出生日期"),new JLabel("籍贯"),
				new JLabel("学院"),new JLabel("专业"),new JLabel("班级"),
				new JLabel("入学时间"),new JLabel("年"),new JLabel("月"),
				new JLabel("日"),new JLabel("年"),new JLabel("月"),new JLabel("日")	};
	private JLabel jl=new JLabel("请输入要查询学生的学号：");
	private JTextField jtf=new JTextField();//学号输入框
	private JButton jb=new JButton("查询");
	private JLabel[] jlArray2=new JLabel[13];//显示信息
	private GetStuInfo getsi;//声明GetStuInfo引用
	public TeachSearchInfo(String host) {//构造器
		this.host=host;
		getsi=new GetStuInfo(this.host);
		this.initialFrame();
	}
	public void initialFrame() {
		this.setLayout(null);
		jl.setBounds(30,20,160,30);
		this.add(jl);
		jtf.setBounds(200,20,200,30);
		this.add(jtf);
		jtf.addActionListener(this);//为学号输入文本框注册事件监听器
		jb.setBounds(420, 20, 100, 30);
		this.add(jb);
		jb.addActionListener(this);//为查询按钮注册时间监听器
		//显示标签
		for(int i=0;i<9;i++) {
			jlArray[i].setBounds(50,100+50*i,80,30);
			this.add(jlArray[i]);
		}
		jlArray[9].setBounds(170+40,100+50*3,30,30);this.add(jlArray[9]);//出生：年
		jlArray[10].setBounds(170+60+35,100+50*3,30,30);this.add(jlArray[10]);//月
		jlArray[11].setBounds(170+70+50+35,100+50*3,30,30);this.add(jlArray[11]);//日
		jlArray[12].setBounds(170+40,50*10,30,30);this.add(jlArray[12]);//入学：年
		jlArray[13].setBounds(170+60+35,50*10,30,30);this.add(jlArray[13]);//入学：月
		jlArray[14].setBounds(170+70+50+35,50*10,30,30);this.add(jlArray[14]);//入学：日
		//学生信息
		for(int i=0;i<13;i++) {
			jlArray2[i]=new JLabel();
			if(i<3) {//学号、姓名、性别
				jlArray2[i].setBounds(170,100+50*i,300,30);
			}else if(i==3) {//出生年份
				jlArray2[i].setBounds(170,100+50*3,40,30);
			}else if(i==4) {//出生月份
				jlArray2[i].setBounds(170+70,100+50*3,40,30);
			}else if(i==5) {//出生日期
				jlArray2[i].setBounds(170+70+60,100+50*3,40,30);
			}else if(i<10) {//籍贯、学院、专业、班级
				jlArray2[i].setBounds(170,50*i,500,30);
			}else if(i==10) {//入学年份
				jlArray2[i].setBounds(170,50*10,60,30);
			}else if(i==11) {//入学月份
				jlArray2[i].setBounds(170+70,50*10,50,30);
			}else {//入学日期
				jlArray2[i].setBounds(170+70+60,50*10,50,30);
			}
			this.add(jlArray2[i]);
		}
	}
	public void setFocus() {
		this.jtf.requestFocus(true);
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==jb || e.getSource()==jtf) {//按Enter键或点击查询按钮
			String stu_id=jtf.getText();//学号
			if(stu_id.equals("")) {
				JOptionPane.showMessageDialog(this,"请输入学生学号", "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}else {//调用GetStuInfo获得指定学生的基本信息
				String[] baseinfo=getsi.getBaseInfo(stu_id);
				if(baseinfo[0]==null) {//没有该学生信息
					JOptionPane.showMessageDialog(this, "没有该学生","错误",JOptionPane.ERROR_MESSAGE);
					return ;
				}else {
					for(int i=0;i<13;i++) {//刷新
						jlArray2[i].setText(baseinfo[i]);
					}
				}
			}
		}
	}
	public static void main(String[] args) {
		TeachSearchInfo tsi=new TeachSearchInfo("127.0.0.1:3306");
		JFrame jf=new JFrame();
		jf.setBounds(10,10,700,650);
		jf.add(tsi);
		jf.setVisible(true);
	}
}