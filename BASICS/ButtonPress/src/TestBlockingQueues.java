import java.util.concurrent.*;
import java.io.*;
import static net.mindview.util.Print.*;

class LiftOffRunner implements Runnable{
	private BlockingQueue<LiftOff> rockets;//队列
	public LiftOffRunner(BlockingQueue<LiftOff> queue) {
		rockets=queue;
	}
	public void add(LiftOff lo) {		
		try {
			rockets.put(lo);
		}catch(InterruptedException e) {
			print("put() interrupted.");
		} 
	}
	public void run() {
		try {
			while(!Thread.interrupted()) {
				LiftOff rocket=rockets.take();
				rocket.run();
			}
		}catch(InterruptedException e) {
			print("waking from take()");
		}
		print("exiting LiftOffRunner.");
	}
}

public class TestBlockingQueues{
	static void getkey() {
		try {
			new BufferedReader(new InputStreamReader(System.in)).readLine();
		}catch(java.io.IOException e) {
			throw new RuntimeException(e);
		}
	}
	static void getkey(String message) {
		print(message);
		getkey();
	}
	static void test(String msg, BlockingQueue<LiftOff> queue) {
		print(msg);
		LiftOffRunner runner=new LiftOffRunner(queue);
		Thread t=new Thread(runner);
		t.start();
		for(int i=0;i<5;i++)
			runner.add(new LiftOff(i));
		getkey("press enter '("+msg+")");
		t.interrupt();
		print("Finished"+msg+"test");
	}
	public static void main(String[] args) {
		test("LinkedBlockingQueue",new LinkedBlockingQueue<LiftOff>());
	}
}
class LiftOff implements Runnable{
	protected int countDown=6;
	private static int taskCount=0;
	private final int id=taskCount++;
	public LiftOff() {}
	public LiftOff(int countDown) {
		this.countDown=countDown;
	}
	public String status() {
		return "#"+id+"("+(countDown>0?countDown:"LiftOff!")+"),";
	}
	public void run() {
		try {			
			if(!Thread.interrupted()) {
				while(countDown-->0) {
					System.out.print(status());
					Thread.yield();
					TimeUnit.MILLISECONDS.sleep(100);
				}
			}
		}catch(InterruptedException e) {
			System.out.println("liftoff interrupted");
			System.exit(0);
		}
	}
}






