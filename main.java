
public class main {
    /**
     * @param <T>
     * @param args
     */

    public final static String ANSI_RESET = "\u001B[0m";
    public final static String ANSI_YELLOW = "\u001B[33m";
    public static <T> void main(String[] args) {
        
        CustomPriorityQueue<T> queue = new CustomPriorityQueue<>(5);
        // Producer<T> producer = new Producer<>(queue);
        // Consumer<T> consumer = new Consumer<>(queue);
        // Thread thread1 = new Thread(producer);
        // thread1.start();
        // Thread thread2 = new Thread(consumer);
        // thread2.start();

        ProducerConsumer pc = new ProducerConsumer<>(queue);
        
        Thread threadP1 = new Thread(new Runnable() {
            
            public void run() {
                try {
                    pc.produce(true);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread threadP2 = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    pc.produce(false);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                
            }
            
        });

        Thread threadC = new Thread(new Runnable() {
            
            public void run() {
                try {
                    pc.consume(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        Thread threadD = new Thread(new Runnable() {
            
            public void run() {
                try {
                    pc.consume(false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        System.out.print(ANSI_YELLOW + "******************************************************" + ANSI_RESET + "\n");
        System.out.print(ANSI_YELLOW + "\n\n    **********YELLOW INDICATES DEQUEUE***********\n\n" + ANSI_RESET);
        System.out.print(ANSI_YELLOW + "\n******************************************************" + ANSI_RESET + "\n");
        threadP1.start();
        threadP2.start();
        threadC.start();
        threadD.start();
    

    }
}