/*
 * To be able to call notify() you need to synchronize on the same object.
 *
 * synchronized (someObject) {
 *    someObject.wait();
 * }
 *
 * // different thread / object
 * synchronized (someObject) {
 *    someObject.notify();
 * }
 *
*/

import java.util.Scanner;

public class Processor {
	
	public void produce() throws InterruptedException {
		
		synchronized(this) {
			System.out.println("Producer thread running ....");
			wait();  // Release the intrinsic lock
			System.out.println("Resumed.");
		}
	}
	
	
	public void consume() throws InterruptedException {
		
		Scanner scanner = new Scanner(System.in);
		Thread.sleep(2000);   // producer() would kick off first!
		
		synchronized(this) {
			System.out.println("Waiting for return key.");
			scanner.nextLine();
			System.out.println("Return key pressed.");
			notify();
			Thread.sleep(5000);   // Lock is released after this statement
		}
	}
	

}
