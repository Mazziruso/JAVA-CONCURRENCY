package Practices;
import java.util.*;
import java.util.concurrent.*;

public class Pra10 {
	public static void main(String[] args) {
		ThreadMethod tm = new ThreadMethod();
		tm.runTask(10);
		try {
			TimeUnit.MILLISECONDS.sleep(1000);
		} catch(InterruptedException e) {
			System.out.println("Interrupted");
		}
		System.out.println(tm.getFib());
	}
}

class ThreadMethod {
	private int id;
	private static int taskId = 0;
	private Thread t;
	private int fib;
	public ThreadMethod() {
		this.id = taskId++;
		this.fib = 0;
	}
	public void runTask(int n) {
		if(t == null) {
			t = new Thread(new Runnable() {
				@Override
				public void run() {
					int a=0;
					int b=1;
					int c;
					for(int i=1; i<n; i++) {
						c = a + b;
						a = b;
						b = c;
					}
					fib = b;
				}
				public String toString() {
					return Thread.currentThread().getName() + ": " + id;
				}
			});
			t.start();
		}
	}
	public int getFib() {
		return this.fib;
	}
}