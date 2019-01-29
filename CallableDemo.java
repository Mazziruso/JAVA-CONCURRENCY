package Demo;
import java.util.concurrent.*;
import java.util.*;

public class CallableDemo{
	public static void main(String[] args) {
		ExecutorService exec = Executors.newCachedThreadPool();
		ArrayList<Future<String>> results = new ArrayList<>();
		for(int i=0; i<50; i++) {
			results.add(exec.submit(new TaskWithRet(i)));
		}
		for(Future<String> ft : results) {
			try {
				System.out.println(ft.get());
			} catch(InterruptedException e) {
				System.out.println(e);
				return;
			} catch(ExecutionException e) {
				System.out.println(e);
			} finally {
				exec.shutdown();
			}
		}
	}
}

class TaskWithRet implements Callable<String> {
	private int id;
	public TaskWithRet(int id) {
		this.id = id;
	}
	public String call() {
		return "result of TaskWithRet " + this.id;
	}
}