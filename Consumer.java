


public class Consumer<T> {
    private CustomPriorityQueue<T> queue;
    
    public Consumer(CustomPriorityQueue<T> queue){
        this.queue = queue;
    }
}