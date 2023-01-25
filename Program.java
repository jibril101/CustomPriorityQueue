import java.util.logging.Logger;
import java.util.logging.StreamHandler;

public class Program {
    /**
     * @param <T>
     * @param args
     */

    private final static String ANSI_RESET = "\u001B[0m";
    private final static String ANSI_YELLOW = "\u001B[33m";
    private static Logger LOG = Logger.getLogger(Program.class.getName());
    private static StreamHandler handler = new StreamHandler(System.out, new MyFormatter());

    public static <T> void main(String[] args) throws InterruptedException {
        
        
        LOG.addHandler(handler);
        LOG.info(ANSI_YELLOW + "******************************************************" + "\n " +
                                "\n\n**********YELLOW INDICATES DEQUEUE***********\n\n" +  
                            "\n******************************************************" + ANSI_RESET + "\n");

        
        CustomPriorityQueue<T> queue = new CustomPriorityQueue<>(10);

        ProducerConsumer<T> pc = new ProducerConsumer<>(queue);
        
        Thread threadP1 = new Thread(new Runnable() {
            
            public void run() {
                try {
                    pc.produce();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread threadP2 = new Thread(new Runnable() {

            @Override
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
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });


        Thread threadD = new Thread(new Runnable() {
            
            public void run() {
                try {
                    pc.consume();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    
        threadP1.start();
        threadP2.start();
        threadC.start();
        threadD.start();
        //Thread.sleep(5000);
        // threadP1.join();
        // threadP2.join();
        // threadC.join();
        // threadD.join();
    

    }
}