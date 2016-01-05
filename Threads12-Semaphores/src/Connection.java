/* Semaphores are mainly used to limit the number of simultaneous threads that can access a resources, 
 * but you can also use them to implement deadlock recovery systems since a semaphore with one permit 
 * is basically a lock that you can unlock from other threads. 
 */

/*
 * Mutex (or a semaphore initialized to 1; meaning there's only one resource)
 * is basically a mutual exclusion; Only one thread can acquire the resource
 * at once, and all other threads trying to acquire the resource are blocked
 * until the thread owning the resource releases.
 */

/*
 * Semaphore is used to control the number of threads executing. There will be
 * fixed set of resources. The resource count will gets decremented every time
 * when a thread owns the same. When the semaphore count reaches 0 then no other
 * threads are allowed to acquire the resource. The threads get blocked till
 * other threads owning resource releases. 
 */


/*
 * true means whichever thread gets first in the waiting pool (queue)
 * waiting to acquire a resource, is first to obtain the permit.

 * Note that I called it a pool!
 * The reason is when you say "Queue", you're saying that things are
 * scheduled to be FIFO (First In First Out) .. which is not always the case here!
    
 * When you initialize the semaphore with Fairness, by setting its second
 * argument to true, it will treat the waiting threads like FIFO.
 
 * But, it doesn't have to be that way if you don't set on the fairness. the JVM
 * may schedule the waiting threads in some other manner that it sees best
 */


import java.util.concurrent.Semaphore;

public class Connection {
	
	private static Connection instance = new Connection();
	
	private Semaphore sem = new Semaphore(10, true);  // limit to 10 connections at a time
	
	private int connections = 0;
	
	private Connection() {
		
	}
	
	public static Connection getInstance() {
		return instance;
	}
	
	public void connect() {
		
		try {
			sem.acquire(); // get permit, decrease the sem value, if 0 wait for release
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			doConnect();
		}
		finally {
			sem.release(); //release permit, increase the sem value and activate waiting thread			
		}
		
	}
	
	public void doConnect() {		
		synchronized(this) {
			connections++;
			System.out.println("Current connections: " + connections);			
		}
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		synchronized(this) {
			connections--;
		}				
	}

}
