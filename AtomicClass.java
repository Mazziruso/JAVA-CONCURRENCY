package Demo;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

public class AtomicClass implements Runnable {
	private AtomicInteger i = new AtomicInteger(0);
	public int getValue() {
		return i.get();
	}
	public void evenInc() {
		i.addAndGet(2);
	}
	@Override
	public void run() {
		while(true) {
			evenInc();
		}
	}
	public static void main(String[] args) {
		new Timer().schedule(new TimerTask() {
			public void run() {
				System.err.println("Aborting");
				System.exit(0);
			}
		}, 5000); // terminate after 5 seconds
		ExecutorService exec = Executors.newCachedThreadPool();
		AtomicClass ait = new AtomicClass();
		exec.execute(ait);
		int val;
		while(true) {
			val = ait.getValue();
			if(val % 2 == 1) {
				System.out.println(val);
				System.exit(0);
			}
		}
	}
}