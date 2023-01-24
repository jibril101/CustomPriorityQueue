


public class Consumer<T> {
    private CustomPriorityQueue<T> queue;
    
    public Consumer(CustomPriorityQueue<T> queue){
        this.queue = queue;
    }

    /**
     * @throws InterruptedException
     */
    public synchronized void consume() throws InterruptedException {

        while(true) {
            
            while(queue.isEmpty()) {
               System.out.print("\n Queue is Empty \n");
               wait();
            }

            try {
                Thread.sleep(5000);
                queue.dequeue();
            } catch (Exception e) {
                e.printStackTrace();
            }
            notify();
        }
    }
}
