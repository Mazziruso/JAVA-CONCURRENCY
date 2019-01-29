package Demo;
import java.util.concurrent.*;
import java.io.*;

public class TestBlockingQueue {
	//IO Blocking 
	static void getKey() {
		try {
			new BufferedReader(new InputStreamReader(System.in)).readLine();
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
	}
	static void getKey(String message) {
		System.out.println(message);
		getKey();
	}
	static void test(String msg, BlockingQueue<LiftOff> q) {
		System.out.println(msg);
		LiftOffRunner runner = new LiftOffRunner(q);
		Thread t = new Thread(runner);
		t.start();
		for(int i=0; i<5; i++) {
			runner.add(new LiftOff(5));
		}
		getKey("Please 'Enter' (" + msg + ")");
		t.interrupt();
		System.out.println("Finish " + msg + " test");
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		test("LinkedBlockingQueue", new LinkedBlockingQueue<LiftOff>());
		test("ArrayBlockingQueue", new ArrayBlockingQueue<LiftOff>(3));
		test("SynchronousQueue", new SynchronousQueue<LiftOff>());
	}

}

class LiftOffRunner implements Runnable {
	private BlockingQueue<LiftOff> rokets;
	public LiftOffRunner(BlockingQueue<LiftOff> q) {
		this.rokets = q;
	}
	public void add(LiftOff lo) {
		try {
			this.rokets.put(lo);
		} catch(InterruptedException e) {
			System.out.println("Interrupted during put(");
		}
	}
	@Override
	public void run() {
		try {
			while(!Thread.interrupted()) {
				LiftOff roket = this.rokets.take();
				roket.run();
			}
		} catch(InterruptedException e) {
			System.out.println("Interruted from take()");
		}
		System.out.println("Exiting LiftOffRunner");
	}
}

//class LiftOff implements Runnable {
//	protected int countDown = 10;
//	private static int taskCount = 0;
//	private final int id = taskCount++;
//	public LiftOff() {
//	}
//	public LiftOff(int countDown) {
//		this.countDown = countDown;
//	}
//	@Override
//	public void run() {
//		while(countDown-- > 0) {
//			System.out.println(status());
//			Thread.yield();
//		}
//	}
//	public String status() {
//		return "#" + this.id + "(" + (countDown>0 ? countDown : "LiftOff!") + ")";
//	}
//}