


public class Consumer<T> implements Runnable {
    private CustomPriorityQueue<T> queue;
    public Consumer(CustomPriorityQueue<T> queue){
        this.queue = queue;
    }
    @Override
    public void run() {
        try {
            //Thread.sleep(1000);
            System.out.print(queue.dequeue().getPriority() + "\n");
            System.out.print(queue.dequeue().getPriority());
            System.out.print(queue.dequeue().getPriority());
            System.out.print(queue.dequeue().getPriority());
            System.out.print(queue.dequeue().getPriority());
            System.out.print(queue.dequeue().getPriority());
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
