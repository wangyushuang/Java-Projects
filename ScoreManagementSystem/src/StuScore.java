//成绩查询模块
import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.*;
import java.util.Vector;
public class StuScore extends JPanel implements ActionListener{
	private static final long serialVersionUID=1L;//序列化时保持版本的兼容性，即版本升级时保持对象的唯一性
	private String host;
	private JLabel jl=new JLabel("请输入学生学号");
	private JLabel jl1=new JLabel("已修学分");
	private JLabel jl2=new JLabel("");//显示学分
	private JTextField jtf=new JTextField();//学号输入框
	private JButton jb=new JButton("查询");//查询按钮
	private JTable jt;//声明表格引用
	private JScrollPane jsp;//创建滚动窗体
	private Vector<String> v_head=new Vector<String>();//存放表头
	private Vector<Vector<String>> v_data=new Vector<Vector<String>>();//存放表格数据
	private GetScore gs;
	public StuScore(String host) {
		this.host=host;
		gs=new GetScore(this.host);
		this.initialData();
		this.initialFrame();
	}
	public void initialData() {//初始化表头
		v_head.add("课程名");
		v_head.add("分数");
		v_head.add("学分");		
	}
	public void initialFrame() {
		this.setLayout(null);
		jl.setBounds(60,20,150,30);this.add(jl);
		jtf.setBounds(195,20,150,30);;this.add(jtf);
		jtf.addActionListener(this);//为文本框注册事件监听器
		jb.setBounds(350, 20, 100, 30);this.add(jb);
		jb.addActionListener(this);
		DefaultTableModel dtm=new DefaultTableModel(v_data,v_head);
		jt=new JTable(dtm);//创建表格
		jsp=new JScrollPane(jt);//创建分隔窗体
		this.add(jsp);
		jl1.setBounds(60,570,130,30);this.add(jl1);
		jl2.setBounds(160,570,130,20);this.add(jl2);
	}
	public void setFocus() {
		this.requestFocus(true);
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==jb || e.getSource()==jtf) {//按下回车或者按下查询按钮
			String stu_id=jtf.getText().trim();//学号
			if(stu_id.equals("")) {
				JOptionPane.showMessageDialog(this, "请输入学生学号", "错误", JOptionPane.ERROR_MESSAGE);
				return ;
			}else {
				v_data=gs.getAllScore(stu_id);//根据学号获得成绩信息
				DefaultTableModel dtm=new DefaultTableModel(v_data,v_head);
				jt.setModel(dtm);
				((DefaultTableModel)jt.getModel()).fireTableStructureChanged();//更新显示
				String xuefen=gs.getXueFen(stu_id)+"";
				jl2.setText(xuefen);
			}
		}
	}
	public static void main(String[] args) {
		StuScore ss=new StuScore("127.0.0.1:3306");
		JFrame jf=new JFrame();
		jf.setBounds(10,10,700,650);jf.add(ss);
		jf.setVisible(true);
	}
}





