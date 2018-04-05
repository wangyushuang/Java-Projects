import java.util.concurrent.*;

abstract class IntGenerator{
	private volatile boolean canceled=false;
	public abstract int next();
	public void cancel() {
		canceled=true;
	}
	public boolean isCanceled() {
		return canceled;
	}
}
class EvenGenerator extends IntGenerator{
	private int currentevenValue=0;
	@Override
	public synchronized int next() {
		currentevenValue++;
		Thread.yield();
		currentevenValue++;
		return currentevenValue;
	}
	
}
public class EvenCheck implements Runnable{
	private EvenGenerator generator;
	private final int id;
	public EvenCheck(EvenGenerator g, int ident) {
		generator=g;
		id=ident;
	}
	public void run() {
		while(!generator.isCanceled()) {
			int val=generator.next();
			if(val%2!=0) {
				System.out.println("id="+id);
				System.out.println(val+" not even");				
				System.out.println(Thread.currentThread());
				generator.cancel();
			}else {
				System.out.println(Thread.currentThread());
				System.out.println(val+" is even");
			}
		}
	}
	public static void test(EvenGenerator gp, int count) {
		System.out.println("Press Ctrl+C to exit" );
		ExecutorService exec=Executors.newCachedThreadPool();
		for(int i=0;i<count;i++) {
			exec.execute(new EvenCheck(gp,i));
		}
		exec.shutdown();
	}
	public static void test(EvenGenerator gp) {
		test(gp,10);
	}
	public static void main(String[] args) {
		EvenCheck.test(new EvenGenerator());
	}
}