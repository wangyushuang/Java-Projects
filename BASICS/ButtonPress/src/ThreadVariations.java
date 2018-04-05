import java.util.concurrent.*;

//使用内部类创建并运行线程
class InnerThread1{
	private int countDown=5;
	private Inner inner;
	private class Inner extends Thread{
	//内部类
		Inner(String name){
			super(name);
			start();
		}
		public void run() {
			try {
				while(true) {
					System.out.println(this);
					if(countDown--==0)
						return;
					sleep(10);
				}
			}catch(InterruptedException e) {
				System.out.println("Interrupted.");
			}
		}
		public String toString() {
			return getName()+":"+countDown;
		}
	}
	public InnerThread1(String name) {
		inner=new Inner(name);
	}
}
//使用匿名内部类创建并运行线程
class InnerThread2{
	public InnerThread2(String name) {
		Thread th=new Thread(name) {//匿名内部类
			private int countDown=5;
			public void run(){
				try {
					while(true) {
						System.out.println(this);
						if(countDown--==0)
							return;
						sleep(10);
					}
				}catch(InterruptedException e) {
					System.out.println("Interrupted sleep()");//Thread有sleep()方法
				}
			}
			public String toString() {
				return getName()+":"+countDown;
			}
		};
		th.start();
	}
}
//实现Runnable接口
class InnerRunnable1{
	private int countDown=5;
//	private Inner inner;
	private class Inner implements Runnable{
		private Thread th;
		public void run() {
			try {
				while(true) {
					System.out.println(this);
					if(countDown--==0)
						return;
					TimeUnit.MILLISECONDS.sleep(10);//Runnable没有sleep（）方法
				}
			}catch(InterruptedException e) {
				System.out.println("Interrupted");
			}
		}
		public Inner(String name) {
			th=new Thread(this,name);
			th.start();
		}
		public String toString() {
			return th.getName()+":"+countDown;
		}
	}
	public InnerRunnable1(String name) {
		new Inner(name);
	}
}
//匿名实现Runnable接口
class InnerRunnable2{
	public InnerRunnable2(String name) {
		Thread th=new Thread(new Runnable() {
			private int countDown=5;
			public void run() {
				try {
					while(true) {
						System.out.println(this);
						if(countDown--==0)
							return;
						TimeUnit.MILLISECONDS.sleep(10);
					}
				}catch(InterruptedException e) {
					System.out.println("Interrupted");
				}
			}
			public String toString() {
				return Thread.currentThread().getName()+":"+countDown;
			}
		},name);
		th.start();
	}
}
//用一个单独的方法运行一段代码作为任务
class ThreadMethod{
	private int countDown=5;
	private Thread th;
	private String name;
	public ThreadMethod(String name) {
		this.name=name;
	}
	public void runTask() {
		if(th==null) {
			th=new Thread(name){
				public void run() {
					try {
						while(true) {
							System.out.println(this);
							if(countDown--==0)
								return;
							sleep(10);
						}
					}catch(InterruptedException e) {
						System.out.println("Interrupted");
					}
				}
				public String toString() {
					return name+":"+countDown;
				}
			};
			th.start();
		}
	}
}
public class ThreadVariations{
	public static void main(String[] args) {
		new InnerThread1("InnerThread1");
		new InnerThread2("InnerThread2");
		new InnerRunnable1("InnerRunnable1");
		new InnerRunnable2("InnerRunnable2");
		new ThreadMethod("ThreadMethod").runTask();
	}
}















