package Demo;

import java.util.*;
import java.util.concurrent.*;

public class DaemonThread {
	public static void main(String[] args) throws InterruptedException {
		//通过编写定制的ThreadFactory可以定制由Executor创建的线程的属性(后台/优先级/名称)
//		ExecutorService exec = Executors.newCachedThreadPool(new DaemonExecutor());
		ExecutorService exec = Executors.newCachedThreadPool(new ThreadFactory() {
			@Override
			public Thread newThread(Runnable r) {
				Thread t = new Thread(r);
				t.setDaemon(true);
				return t;
			}
		});
		for(int i=0; i<10; i++) {
			exec.execute(new DaemonClass());
		}
		System.out.println("All daemon threads start");
		TimeUnit.MILLISECONDS.sleep(500);
		System.out.println("Done!");
	}
}

class DaemonClass implements Runnable {
	public void run() {
		try {
			while(true) {
				TimeUnit.MILLISECONDS.sleep(100);
				System.out.println(Thread.currentThread() + ": " + this);
			}
		} catch(InterruptedException e) {
			System.out.println("Interrupted");
		}
	}
}

class DaemonExecutor implements ThreadFactory {
	@Override
	public Thread newThread(Runnable r) {
		Thread t = new Thread(r);
		t.setDaemon(true);
		return t;
	}
}