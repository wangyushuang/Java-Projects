//欢迎界面
import javax.swing.*;
import java.awt.Font;
public class Welcome extends JPanel{
	private static final long serialVersionUID=1L;//序列化时保持版本的兼容性，即版本升级时保持对象的唯一性
	private final static String str="欢迎使用学生管理系统";//文字
	private final static ImageIcon icon=new ImageIcon("./pic/墨爷撕家.jpg");//图片
	private JLabel jl=new JLabel(icon);
	private JLabel jl1=new JLabel(str);	
	public Welcome() {
		this.initialFrame();
	}
	public void initialFrame() {
		this.setLayout(null);
		jl.setBounds(0,0,740, 600);
		jl1.setBounds(70,100,700,100);
		jl.setHorizontalAlignment(JLabel.CENTER);
		jl.setVerticalAlignment(JLabel.CENTER);
		Font font=new Font("隶书",Font.PLAIN,54);
		jl1.setFont(font);
		this.add(jl1);
		this.add(jl);
	}
}