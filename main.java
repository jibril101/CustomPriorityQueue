
public class main {
    /**
     * @param <T>
     * @param args
     */
    public static <T> void main(String[] args) {
        
        CustomPriorityQueue<T> queue = new CustomPriorityQueue<>(20);
        // Producer<T> producer = new Producer<>(queue);
        // Consumer<T> consumer = new Consumer<>(queue);
        // Thread thread1 = new Thread(producer);
        // thread1.start();
        // Thread thread2 = new Thread(consumer);
        // thread2.start();
        
        ProducerConsumer pc = new ProducerConsumer<>(queue);
        
        Thread threadP = new Thread(new Runnable() {
            
            public void run() {
                try {
                    pc.produce();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        
        Thread threadC = new Thread(new Runnable() {
            
            public void run() {
                try {
                    pc.consume();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        
        threadP.start();
        threadC.start();

    }
}