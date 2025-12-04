public class Passenger implements Runnable {
    private final int id;
    private final Train train;

    public Passenger(int id, Train train) {
        this.id = id;
        this.train = train;
    }

    @Override
    public void run() {
        train.board(id);
        train.leave(id);
    }
}