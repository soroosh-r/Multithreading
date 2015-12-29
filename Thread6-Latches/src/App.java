import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
- Main thread start
- Create CountDownLatch for N threads
- Create and start N threads
- Main thread wait on latch
- N threads completes there tasks are returns
- Main thread resume execution
*/

class Processor implements Runnable {
	private CountDownLatch latch;
	
	public Processor (CountDownLatch latch) {
		this.latch = latch;
	}

	@Override
	public void run() {
		System.out.println("Processor starting ...");
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Processor Completed!");
		
		if(latch != null) {
			latch.countDown();
		}
		
	}	
}


public class App {

	public static void main(String[] args) {
		
		CountDownLatch latch = new CountDownLatch(3);
		
		ExecutorService executor = Executors.newFixedThreadPool(3);
		
		for(int i=0; i < 3; i++) {
			executor.submit(new Processor(latch));
		}
		
		executor.shutdown();
		
		try {
			latch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("\nAll tasks completed.");
	}
	
}
