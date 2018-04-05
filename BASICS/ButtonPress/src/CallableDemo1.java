import java.util.concurrent.*;//并发库
import java.util.ArrayList;

public class CallableDemo1 implements Callable<Integer>{
	public Integer call() {
		int sum=0;
		for(int j=1;j<100;j++)
			sum+=j;
		try {
			System.out.println(Thread.currentThread());
			TimeUnit.MILLISECONDS.sleep(300);
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
		return sum;
	}
	public static void main(String[] args) throws Exception {

		//单线程
//		FutureTask<Integer> fu=new FutureTask<>(new CallableDemo1());
//		new Thread(fu).start();
//		try {
//			System.out.println(fu.get());//得到结果之前，线程阻塞
//		}catch(InterruptedException e) {
//			e.printStackTrace();
//		}
//		System.out.println("Started");
		//线程池
		ExecutorService exec=Executors.newCachedThreadPool();//新建一个线程池
		ArrayList<Future<Integer>> results=new ArrayList<Future<Integer>>();//创建结果数组
		for(int i=0;i<10;i++) {
			//创建10个任务
			Future<Integer> fu1=exec.submit(new CallableDemo1());
			System.out.println("task"+(i+1)+"is running");
			results.add(fu1);
		}
		exec.shutdown();
		for(Future<Integer> fu2:results) {
			try {
				System.out.println(fu2.get());
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
