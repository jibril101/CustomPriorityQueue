


public class Consumer<T> implements Runnable {
    private CustomPriorityQueue<T> queue;
    public Consumer(CustomPriorityQueue<T> queue){
        this.queue = queue;
    }
    @Override
    public void run() {
        try {
            System.out.print(queue.dequeue().getPriority() + "\n");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            System.out.print(queue.dequeue().getPriority() + "\n");
        } catch (Exception e) {

            e.printStackTrace();
        }
        try {
            System.out.print(queue.dequeue().getPriority() + "\n");
        } catch (Exception e) {

            e.printStackTrace();
        }
        try {
            System.out.print(queue.dequeue().getPriority() + "\n");
        } catch (Exception e) {

            e.printStackTrace();
        }
        try {
            System.out.print(queue.dequeue().getPriority() + "\n");
        } catch (Exception e) {

            e.printStackTrace();
        }
        try {
            System.out.print(queue.dequeue().getPriority() + "\n");
        } catch (Exception e) {

            e.printStackTrace();
        }

    }
}
