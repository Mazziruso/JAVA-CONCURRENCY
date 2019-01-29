package Demo;
import java.util.*;
import java.util.concurrent.*;

public class WaitPersonChef {
	public static void main(String[] args) {
		new Restaurant();
	}
}

class Meal {
	private final int orderNum;
	public Meal(int id) {
		this.orderNum = id;
	}
	public String toString() {
		return "Meal " + this.orderNum;
	}
}

class WaitPerson implements Runnable {
	private Restaurant r;
	public WaitPerson(Restaurant r) {
		this.r = r;
	}
	@Override
	public void run() {
		try {
			while(!Thread.interrupted()) {
				synchronized(this) {
					while(r.meal == null) {
						wait();
					}
				}
				System.out.println("WaitPerson got " + r.meal);
				synchronized(r.chef) {
					r.meal = null;
					r.chef.notifyAll(); //唤醒在chef对象上wait()的线程
				}
			}
		} catch(InterruptedException e) {
			System.out.println("WaitPerson Interrupt");
		}
	}
}

class Chef implements Runnable {
	private Restaurant r;
	private int count = 0;
	public Chef(Restaurant r) {
		this.r = r;
	}
	@Override
	public void run() {
		try {
			while(!Thread.interrupted()) {
				synchronized(this) {
					while(r.meal != null) {
						wait();
					}
				}
				if(++count == 10) {
					System.out.println("Out of food");
					r.exec.shutdownNow();
					return;
				}
				System.out.println("Order up");
				synchronized(r.waiPerson) {
					r.meal = new Meal(count);
					r.waiPerson.notifyAll();
				}
				TimeUnit.MILLISECONDS.sleep(100); //produce meal
			}
		} catch(InterruptedException e) {
			System.out.println("Chef Interrupt");
		}
	}
}

class Restaurant {
	public Meal meal;
	public WaitPerson waiPerson = new WaitPerson(this);
	public Chef chef = new Chef(this);
	public ExecutorService exec = Executors.newCachedThreadPool();
	public Restaurant() {
		exec.execute(chef);
		exec.execute(waiPerson);
	}
}