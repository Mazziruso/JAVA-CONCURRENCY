package Demo;
import java.util.concurrent.*;

public class ThreadException implements Runnable {
	@Override
	public void run() {
		throw new RuntimeException();
	}
	public static void main(String[] args) {
//		try {
//			ExecutorService exec = Executors.newCachedThreadPool();
//			exec.execute(new ThreadException());
//		} catch(RuntimeException e) {
//			System.out.println("Exception has been handle");
//		}
		//线程默认的捕获异常处理器
		Thread.setDefaultUncaughtExceptionHandler(new myUncaughtExceptionHander());
		ExecutorService exec = Executors.newCachedThreadPool();
		exec.execute(new ThreadException());
	}
}

class myUncaughtExceptionHander implements Thread.UncaughtExceptionHandler {
	@Override
	public void uncaughtException(Thread t, Throwable e) {
		System.out.println("cause " + e);
	}
}