import java.util.concurrent.*;

class WorkerThread implements Runnable {
    private int workerNumber;

    WorkerThread(int number) {
        workerNumber = number;
    }

    public void run() {
        for (int i=0;i<=100;i+=20) {
        // Perform some work ...
            System.out.println("Worker number: " + workerNumber + ", percent complete: " + i );
            try {
            	Thread.sleep((int)(Math.random() * 1000));
            } catch (InterruptedException e) {
            }
        }
    }
}

public class ThreadPoolTest {
    public static void main(String[] args) {
    	
        int numWorkers = 4;
        int threadPoolSize = 2;
    
        ExecutorService tpes = Executors.newFixedThreadPool(threadPoolSize);
    
        WorkerThread[] workers = new WorkerThread[numWorkers];
        
        for (int i = 0; i < numWorkers; i++) {
            workers[i] = new WorkerThread(i);
            tpes.execute(workers[i]);
        }
        tpes.shutdown();
    }
}
