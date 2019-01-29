package Demo;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import java.util.*;

public class MultiThread{
	public static void main(String[] args) throws InterruptedException {
//		Runnable hello = new DisplayMessage("Hello");
//		Thread thread1 = new Thread(hello);
//		thread1.setDaemon(true);
//		thread1.setName("hello");
//		System.out.println("Starting hello thread...");
//		thread1.start();
//		
//		Runnable goodbye = new DisplayMessage("goodbye");
//		Thread thread2 = new Thread(goodbye);
//		thread2.setPriority(Thread.MIN_PRIORITY);
//		thread2.setDaemon(true);
//		thread2.setName("goodbye");
//		System.out.println("Starting goodbye thread...");
//		thread2.start();
		
//		System.out.println("Starting thread3...");
//		Thread thread3 = new GuessNum(27);
//		thread3.setName("thread-3");
//		thread3.start();
//		try {
//			thread3.join();
//		} catch(InterruptedException e) {
//			System.out.println("Thread interrupted.");
//		}
		
//		System.out.println("Starting thread4...");
//		Thread thread4 = new GuessNum(75);
//		thread4.setName("thread-4");
//		thread4.start();
//		try {
//			thread3.join();
//			thread4.join();
//		} catch(InterruptedException e) {
//			System.out.println("Thread interrupted.");
//		}
//		System.out.println("main() is ending...");
		ExecutorService exec = Executors.newCachedThreadPool();
		LockedClass lc = new LockedClass();
		exec.execute(new Runner(lc, true));
		exec.execute(new Runner(lc, false));
		TimeUnit.SECONDS.sleep(1);
		exec.shutdownNow();
	}
}

class Runner implements Runnable {
	private LockedClass lc;
	private boolean flag;
	public Runner(LockedClass lc, boolean flag) {
		this.lc = lc;
		this.flag = flag;
	}
	@Override
	public void run() {
//		try {
			while(!Thread.interrupted()) {
//				TimeUnit.MILLISECONDS.sleep(100);
				if(flag) {
					lc.put();
				} else {
					lc.get();
				}
			}
//		} catch(InterruptedException e) {
//			System.out.println((flag?"put":"get") + "interrupted");
//		}
	}
}

class LockedClass {
	private Lock putLock = new ReentrantLock();
	private Lock getLock = new ReentrantLock();
	private volatile double d;
	public void put() {
		putLock.lock();
		try {
			System.out.println("put before");
			for(int i=0; i<10000000; i++) {
				d += (Math.PI + Math.E) / (double)i;
				if(i%100==0) {
					Thread.yield();
				}
			}
			System.out.println("put");
		}finally {
			putLock.unlock();
		}
	}
	public void get() {
		getLock.lock();
		try {
			System.out.println("get before");
			for(int i=0; i<10000000; i++) {
				d += (Math.PI + Math.E) / (double)i;
				if(i%100==0) {
					Thread.yield();
				}
			}
			System.out.println("get");
		} finally {
			getLock.unlock();
		}
	}
}

//class DisplayMessage implements Runnable {
//	private String message;
//	
//	public DisplayMessage(String message) {
//		this.message = message;
//	}
//	
//	public void run() {
//		while(true) {
//			System.out.println(message);
//		}
//	}
//}
//
//
//class GuessNum extends Thread {
//	private int number;
//	public GuessNum(int num) {
//		this.number = num;
//	}
//	public void run() {
//		int counter = 0;
//		int guess = 0;
//		do {
//			guess = (int)(Math.random() * 100 + 1);
//			System.out.println(this.getName() + " guesses " + guess);
//			counter++;
//			Thread.yield();
//		} while(guess != number);
//		System.out.println("**Correct!" + this.getName() + "in" + counter);
//	}
//}
