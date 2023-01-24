

public class Item<T> {
    private final int priority;
    private final String message;
    /**
     * @param priority
     */
    public Item(int priority, String message){
        this.priority = priority;
        this.message = message;
    }

    public int getPriority() {
        return priority;
    }

    public String getMessage() {
        return message;
    }

}