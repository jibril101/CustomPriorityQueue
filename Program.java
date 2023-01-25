import java.util.Scanner;
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

    /**
     * @param <T>
     * @param args
     * @throws InterruptedException
     */
    public static <T> void main(String[] args) throws InterruptedException {
        LOG.addHandler(handler);
        
        Scanner cli = new Scanner(System.in);
        System.out.print("Enter the Queue Capacity: ");
        Integer capacity = cli.nextInt();
        System.out.print("Enter how many producers to use. Please dont use too many!\n");
        Integer producers = cli.nextInt();
        System.out.println("Spawning " + producers + " Producers");
        System.out.print("Enter how many consumers to use. Please dont use too many!\n");
        Integer consumers = cli.nextInt();
        System.out.println("Spawning " + consumers + " Consumer");
        cli.close();

        System.out.print("HERE " + capacity);

        LOG.info(ANSI_YELLOW + "\n******************************************************" + "\n " +
                                "\n\n**********YELLOW INDICATES DEQUEUE***********\n\n" +  
                            "\n******************************************************" + ANSI_RESET + "\n");
        
        CustomPriorityQueue<T> queue = new CustomPriorityQueue<>(capacity);

        ProducerConsumer<T> pc = new ProducerConsumer<>(queue);
        

        for (int i =0; i < producers; i++) {
            Thread threadP = new Thread(new Runnable() {
                
                public void run() {
                    try {
                        pc.produce();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            threadP.start();
         }
         for (int i =0; i < consumers; i++) {
            Thread threadC = new Thread(new Runnable() {
                
                public void run() {
                    try {
                        pc.consume();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            threadC.start();
         }

    }

}
