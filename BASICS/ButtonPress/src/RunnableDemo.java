import java.util.concurrent.*;

public class RunnableDemo implements Runnable{
	private static int num=1;
	public void run() {
		try {
			System.out.println(Thread.currentThread());
			TimeUnit.MILLISECONDS.sleep(500);
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		//单个线程
//		Thread th=new Thread(new RunnableDemo());
//		th.start();
//		System.out.println("Started");
		//线程池
		ExecutorService exec=Executors.newCachedThreadPool();
		for(int i=1;i<10;i++) {
			exec.execute(new RunnableDemo());
		}
		System.out.println("Started");
	}
}