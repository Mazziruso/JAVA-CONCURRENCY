package Practices;

public class Pra2 {
	public static void main(String[] args) {
		for (int n=10; n<15; n++) {
			new Thread(new Fib(n)).start();
		}
		System.out.println("Main out");
	}
}

class Fib implements Runnable {
	private int n;
	private int id;
	private static int taskId = 0;
	public Fib() {
		this.id = taskId++;
		this.n = 0;
	}
	public Fib(int n) {
		this.id = taskId++;
		this.n = n;
	}
	public void run() {
		int a = 0;
		int b = 1;
		int c;
		for(int i=1; i<n; i++) {
			c = a + b;
			a = b;
			b = c;
			System.out.println("Thread-" + this.id + ": " + b);
			Thread.yield();
		}
		System.out.println("Thread-" + this.id + " exit");
	}
}