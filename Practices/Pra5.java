package Practices;
import java.util.*;
import java.util.concurrent.*;

public class Pra5 {
	public static void main(String[] args) {
		ExecutorService exec = Executors.newCachedThreadPool();
		Fibb fibbb = new Fibb();
		//thread unsafe
		Future<Integer> ft = exec.submit(fibbb);
		for(int i=2; i<10; i++) {
			ft = exec.submit(fibbb);
		}
		try {
			System.out.println(ft.get());
		} catch(Exception e) {
			System.out.println(e);
		} finally {
			exec.shutdown();
		}
	}
}

class Fibb implements Callable<Integer> {
	private int a;
	private int b;
	private static int taskId = 0;
	private int id;
	public Fibb() {
		this.a = 0;
		this.b = 1;
		this.id = taskId++;
	}
	public Integer call() {
		int c = a + b;
		a = b;
		b = c;
		return c; 
	}
}