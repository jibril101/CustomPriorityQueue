
public class Producer<T> implements Runnable {

    private CustomPriorityQueue<T> queue;

    public Producer(CustomPriorityQueue<T> queue){
        this.queue = queue;
    }
    @Override
    public void run() {
        Item<T> a = new Item<>(1);
        Item<T> b = new Item<>(1);
        Item<T> c = new Item<>(1);
        Item<T> d = new Item<>(2);
        Item<T> e = new Item<>(7);
        Item<T> f = new Item<>(1);
        
       try {
        queue.enqueue(a);
        queue.enqueue(b);
        queue.enqueue(c);
        queue.enqueue(d);
        queue.enqueue(e);
        queue.enqueue(f);

        
       } catch (Exception exp) {
        System.out.print(exp);
       }
       
    }
    
}
