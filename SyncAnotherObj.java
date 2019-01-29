package Demo;
import java.util.concurrent.*;
import java.util.*;

public class SyncAnotherObj {
	public static void main(String[] args) {
		DualSync ds = new DualSync();
		new Thread() {
			@Override
			public void run() {
				ds.f();
			}
		}.start();
		ds.g();
	}
}

class DualSync {
	private Object syncObj = new Object();
	public synchronized void f() { //ds对象的锁
		for(int i=0; i<100; i++) {
			System.out.println("f()");
			Thread.yield();
		}
	}
	public void g() {
		synchronized(syncObj) { //syncObj对象的锁
			for(int i=0; i<100; i++) {
				System.out.println("g()");
				Thread.yield();
			}
		}
	}
}