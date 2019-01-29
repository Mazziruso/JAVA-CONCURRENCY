package Demo;

import java.util.concurrent.*;

public class ThreadSleep{
	public static void main(String[] args) {
		ExecutorService exec = Executors.newCachedThreadPool();
		for(int i=0; i<5; i++) {
			exec.execute(new SleepingTask());
		}
		exec.shutdown();
	}
}

class SleepingTask extends LiftOff {
	@Override
	public void run() {
		try {
			while(countDown-- > 0) {
				System.out.println(status());
				//old-style
				//Thread.sleep(100);
				TimeUnit.MILLISECONDS.sleep(100);
			}
		} catch(InterruptedException e) {
			System.err.println("Interrupted");
		}
	}
}