


public class Consumer<T> implements Runnable {
    private CustomPriorityQueue<T> queue;
    public Consumer(CustomPriorityQueue<T> queue){
        this.queue = queue;
    }
    @Override
    public void run() {
        try {
            Thread.sleep(1000);
            System.out.print("---------------DEQUEUE------------\n");
            queue.dequeue().getPriority();
            queue.dequeue().getPriority();
            queue.dequeue().getPriority();
            queue.dequeue().getPriority();
            queue.dequeue().getPriority();
            queue.dequeue().getPriority();
            queue.dequeue().getPriority();
            queue.dequeue().getPriority();
        } catch (Exception e) {
            e.printStackTrace();
    }
}
}
