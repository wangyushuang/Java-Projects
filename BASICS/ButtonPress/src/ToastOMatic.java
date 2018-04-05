import java.util.concurrent.*;
import java.util.*;
import static net.mindview.util.Print.*;

class Toast{
	public enum Status{
		DRY, BUTTRED, JAMMED
	}
	private Status status=Status.DRY;
	private final int id;
	public Toast(int id) {
		this.id=id;
	}
	public void butter() {
		status=Status.BUTTRED;
	}
	public void jam() {
		status=Status.JAMMED;
	}
	public Status getStatus() {
		return status;
	}
	public int getId() {
		return id;
	}
	public String toString() {
		return "Toast:"+id+":"+status;
	}
}
class ToastQueue extends LinkedBlockingQueue<Toast>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;}
class Toaster implements Runnable {
	private ToastQueue toastQueue;
	private int count=0;
	private Random rand=new Random(47);
	public Toaster(ToastQueue toastQueue) {
		this.toastQueue=toastQueue;
	}
	public void run() {
		try {
			while(!Thread.interrupted()) {
				TimeUnit.MICROSECONDS.sleep(100+rand.nextInt(500));
				Toast t=new Toast(count++);
				print(t);
				toastQueue.put(t);
			}
		}catch(InterruptedException e) {
			print("Toaster interrupted");
		}
		print("Toaster off");
	}
}
class Buttered implements Runnable{
	private ToastQueue dryQueue, butteredQueue;
	public Buttered(ToastQueue dry, ToastQueue buttered) {
		dryQueue=dry;
		butteredQueue=buttered;
	}
	public void run() {
		try {
			while(!Thread.interrupted()) {
				Toast t=dryQueue.take();
				t.butter();
				print(t);
				butteredQueue.put(t);
			}
		}catch(InterruptedException e) {
			print("Butterer Interrupted.");
		}
		print("Butterer Off");
	}
}
class Jammer implements Runnable{
	private ToastQueue butteredQueue, finishedQueue;
	public Jammer(ToastQueue buttered, ToastQueue finished) {
		butteredQueue=buttered;
		finishedQueue=finished;
	}
	public void run() {
		try {
			while(!Thread.interrupted()) {
				Toast t=butteredQueue.take();
				t.jam();
				print(t);
				finishedQueue.put(t);
			}
		}catch(InterruptedException e) {
			print("jam interrupted.");
		}
		print("Jammer off");
	}
}
class Eater implements Runnable{
	private ToastQueue finishedQueue;
	private int counter=0;
	public Eater(ToastQueue finished) {
		finishedQueue=finished;
	}
	public void run() {
		try {
			while(!Thread.interrupted()) {
				Toast t=finishedQueue.take();
				if(t.getId()!=counter++ || t.getStatus()!=Toast.Status.JAMMED) {
					print(">>>Error "+t);
					System.exit(1);
				}else {
					print("chomp "+t);
				}
			}
		}catch(InterruptedException e) {
			print("Eater interrupted");
		}
		print("Eater off");
	}
}
public class ToastOMatic{
	public static void main(String[] args) throws InterruptedException{
		ToastQueue dryQueue=new ToastQueue();
		ToastQueue butteredQueue=new ToastQueue();
		ToastQueue finishedQueue=new ToastQueue();
		ExecutorService exec=Executors.newCachedThreadPool();
		exec.execute(new Toaster(dryQueue));
		exec.execute(new Buttered(dryQueue,butteredQueue));
		exec.execute(new Jammer(butteredQueue, finishedQueue));
		exec.execute(new Eater(finishedQueue));
		TimeUnit.SECONDS.sleep(5);
		exec.shutdownNow();
	}
}


















