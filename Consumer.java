


public class Consumer<T> implements Runnable {
    private CustomPriorityQueue<T> queue;
    public Consumer(CustomPriorityQueue<T> queue){
        this.queue = queue;
    }
    @Override
    public void run() {
        System.out.print(queue.dequeue().getPriority() + "\n");
        System.out.print(queue.dequeue().getPriority() + "\n");
        System.out.print(queue.dequeue().getPriority() + "\n");
        System.out.print(queue.dequeue().getPriority() + "\n");
        System.out.print(queue.dequeue().getPriority() + "\n");
        System.out.print(queue.dequeue().getPriority() + "\n");
    }
}
