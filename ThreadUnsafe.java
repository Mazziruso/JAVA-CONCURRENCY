package Demo;
import java.util.concurrent.*;
import java.util.*;

public class ThreadUnsafe {
	public static void main(String[] args) {
		EvenChecker.test(new EvenGen(), 100);
	}
}

abstract class IntGenerator {
	private volatile boolean canceled = false;
	public abstract int next();
	public void cancel() { canceled=true; }
	public boolean isCanceled() { return canceled; }
}

class EvenChecker implements Runnable {
	private IntGenerator gen;
	private final int id;
	public EvenChecker(IntGenerator g, int ident) {
		this.gen = g;
		this.id = ident;
	}
	@Override
	public void run() {
		int val;
		while(!gen.isCanceled()) {
			val = gen.next();
			if(val % 2 == 1) {
				System.out.println(val + " is not even!");
				gen.cancel();
			}
		}
	}
	public static void test(IntGenerator gp, int count) {
		System.out.println("Press Control-C to exit!");
		ExecutorService exec = Executors.newCachedThreadPool();
		for(int i=0; i<count; i++) {
			exec.execute(new EvenChecker(gp, i));
		}
		exec.shutdown();
	}
	public static void test(IntGenerator gp) {
		test(gp, 10);
	}
}

class EvenGen extends IntGenerator {
	private int curVal = 0;
	synchronized public int next() {
		curVal++;
		curVal++;
		return curVal;
	}
}