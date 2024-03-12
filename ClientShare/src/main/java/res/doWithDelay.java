package res;

public class doWithDelay implements Runnable {
    private final doWithDelay.Operation operation;
    private final int ms;
    public doWithDelay(doWithDelay.Operation operation, int ms) {
        this.operation = operation;
        this.ms = ms;
    }
    @Override
    public void run() {
        try {
            Thread.sleep(ms);
            operation.execute();
        } catch (InterruptedException ignored) {
        }
    }

    public interface Operation{
        void execute();
    }
}