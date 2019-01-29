package Demo;
import java.util.concurrent.*;
import java.util.*;


public class SynchronizedVolatile {
	private static final int SIZE = 10;
	private static CircularSet serials = new CircularSet(1000);
	private static ExecutorService exec = Executors.newCachedThreadPool();
	static class SerialChecker implements Runnable {
		@Override
		public void run() {
			while(true) {
				int serial = SerialNumGen.nextSerialNum();
				if(serials.contains(serial)) {
					System.out.println("Duplicate: " + serial);
					System.exit(0);
				}
				serials.add(serial);
			}
		}
	}
	public static void main(String[] args) throws Exception {
		for(int i=0; i<SIZE; i++) {
			exec.execute(new SerialChecker());
		}
		TimeUnit.MILLISECONDS.sleep(100000);
		System.out.println("No Duplicates Detected");
		System.exit(0);
	}
}

class SerialNumGen {
	private static volatile int serialNum = 0; //变量可视性
	public synchronized static int nextSerialNum() {
		return serialNum++;
	}
}

class CircularSet {
	private int[] array;
	private int len;
	private int index = 0;
	public CircularSet(int size) {
		this.array = new int[size];
		this.len = size;
		for(int i=0; i<size; i++) {
			this.array[i] = -1;
		}
	}
	public synchronized void add(int i) {
		this.array[this.index] = i;
		this.index = (++this.index) % this.len;
	}
	public synchronized boolean contains(int val) {
		for(int i=0; i<this.len; i++) {
			if(this.array[i] == val) {
				return true;
			}
		}
		return false;
	}
}
