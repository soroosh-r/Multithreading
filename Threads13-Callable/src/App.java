/* Use Callable and Future in Java to get results from your threads and to allow your threads 
 * to throw exceptions. Plus, Future allows you to control your threads, checking to see if 
 * they're running or not, waiting for results and even interrupting them or descheduling them. */

/*
 * {@link java.lang.Runnable} is the default abstraction for creating a task in Java. 
 * It has a single method {@link Runnable#run()}
 * that accepts no arguments and returns no value, nor it can throw
 * any checked exception. To overcome these limitations, Java 5 introduced a new
 * task abstraction through {@link java.util.concurrent.Callable} interface. 
 */

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class App {

	public static void main(String[] args) {
		
		ExecutorService executor = Executors.newCachedThreadPool();
		
		Future<Integer> future = executor.submit(new Callable<Integer>() {
			@Override
			public Integer call() throws Exception {
				
				Random random = new Random();
				int duration = random.nextInt(4000);
				
				if(duration > 2000) {
					throw new IOException("Sleeping for too long.");
				}
				
				System.out.println("Starting ...");
				
				try {
					Thread.sleep(duration);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				System.out.println("Finished.");
				
				return duration;					
			}						
		});
		
		executor.shutdown();
		
        try {
            System.out.println("Result is: " + future.get()); //get returned value from call()
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            IOException ex = (IOException) e.getCause();
            System.out.println(ex.getMessage());
        }

	}

}
