import java.util.concurrent.*;
import static net.mindview.util.Print.*;

class DualSync{
	private Object syncObject=new Object();
	public synchronized void f() {
		//在this上同步
		for(int i=0;i<5;i++) {
			print("f()");
			Thread.yield();			
		}
	}
	public void g() {
		synchronized(syncObject) {
			for(int i=0;i<5;i++) {
				print("g()");
			}
		}
	}
}
public class SyncObject{
	public static void main(String[] args) throws InterruptedException{
		final DualSync ds=new DualSync();
		new Thread() {
			public void run() {
				ds.f();
			}
		}.start();
		TimeUnit.MICROSECONDS.sleep(10);
		ds.g();
	}
}