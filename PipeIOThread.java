package Demo;
import java.util.concurrent.*;
import java.util.*;
import java.io.*;

public class PipeIOThread {

	public static void main(String[] args) throws Exception {
		Sender tx = new Sender();
		Receiver rx = new Receiver(tx);
		ExecutorService exec = Executors.newCachedThreadPool();
		exec.execute(tx);
		exec.execute(rx);
		TimeUnit.SECONDS.sleep(10);
		exec.shutdownNow();
	}

}

class Sender implements Runnable {
	private Random rand = new Random(47);
	private PipedWriter out = new PipedWriter();
	public PipedWriter getPipedWriter() {
		return out;
	}
	@Override
	public void run() {
		try {
			while(!Thread.interrupted()) {
				for(char c='A'; c<='z'; c++) {
					out.write(c);
					TimeUnit.MILLISECONDS.sleep(rand.nextInt(500));
				}
			}
		} catch(IOException e) {
			System.out.println(e + "Sender write exception");
		} catch(InterruptedException e) {
			System.out.println(e + "Sender sleep exception");
		}
	}
}


class Receiver implements Runnable {
	private PipedReader in;
	public Receiver(Sender sender) throws IOException {
		this.in = new PipedReader(sender.getPipedWriter());
	}
	@Override
	public void run() {
		try {
			while(!Thread.interrupted()) {
				System.out.println("Read: " + (char)in.read());
			}
		} catch(IOException e) {
			System.out.println(e + " Receiver write exception");
		}
	}
}