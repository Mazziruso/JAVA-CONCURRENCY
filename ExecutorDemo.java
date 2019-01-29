package Demo;
import java.util.concurrent.*;

public class ExecutorDemo{
	public static void main(String[] args) {
//		for(int i=0; i<5; i++) {
//			new Thread(new LiftOff()).start();
//		}
//		System.out.println("Waiting for LiftOff");
//		ExecutorService exec = Executors.newCachedThreadPool();
//		ExecutorService exec = Executors.newFixedThreadPool(5);
		ExecutorService exec = Executors.newSingleThreadExecutor();
		for(int i=0; i<5; i++) {
			exec.execute(new LiftOff());
		}
		exec.shutdown();
	}
}

class LiftOff implements Runnable {
	protected int countDown = 10;
	private static int taskCount = 0;
	private final int id = taskCount++;
	public LiftOff() {	
	}
	public LiftOff(int countDown) {
		this.countDown = countDown;
	}
	protected String status() {
		return "#" + this.id + "(" + (this.countDown>0?countDown:"liftoff!") + ")";
	}
	@Override
	public void run() {
		while(this.countDown-- > 0) {
			System.out.println(status());
			Thread.yield();
		}
		System.out.println("#" + this.id + " exit");
	}
}