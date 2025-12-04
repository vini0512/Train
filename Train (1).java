public class Train {
    private final int capacity;
    private int boarded = 0;
    private int left = 0;
    private boolean cleaned = false;

    public Train(int capacity) {
        this.capacity = capacity;
    }

    public synchronized void clean() {
        while (boarded > 0 || left > 0) {
            try { wait(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }
        System.out.println("[TRAIN] Cleaning...");
        cleaned = true;
        System.out.println("[TRAIN] Cleaned and ready!");
        notifyAll();
    }

    public synchronized void board(int id) {
        while (!cleaned || boarded >= capacity) {
            try { wait(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }
        boarded++;
        System.out.println("Passenger " + id + " BOARDED (" + boarded + "/" + capacity + ")");
        notifyAll();
    }

    public synchronized void startRide() {
        while (boarded < capacity) {
            try { wait(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }
        System.out.println("\n[TRAIN] RIDE STARTING with " + capacity + " passengers!\n");
        try { Thread.sleep(2000); } catch (InterruptedException e) {}
        System.out.println("[TRAIN] RIDE FINISHED!\n");
        notifyAll();
    }

    public synchronized void leave(int id) {
        while (boarded == 0 || left >= boarded) {
            try { wait(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }
        left++;
        System.out.println("Passenger " + id + " LEFT (" + left + "/" + capacity + ")");
        if (left == capacity) {
            System.out.println("[TRAIN] All passengers left. Train is EMPTY.\n");
            boarded = 0;
            left = 0;
            cleaned = false;
            notifyAll();
        }
    }
}