

public class Item<T> {
    private final int priority;

    /**
     * @param priority
     */
    public Item(int priority){
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

}
