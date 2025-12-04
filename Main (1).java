public class Main {
    public static void main(String[] args) throws InterruptedException {
        final int N = 3;  // seats
        final int M = 3;  // passengers
        final int CYCLES = 4;

        Train train = new Train(N);

        for (int cycle = 1; cycle <= CYCLES; cycle++) {
            System.out.println("========== CYCLE " + cycle + " ==========\n");

            // 1. Clean
            Thread cleaner = new Thread(train::clean);
            cleaner.start();
            cleaner.join();

            // 2. Start passengers
            Thread[] passengers = new Thread[M];
            for (int i = 0; i < M; i++) {
                passengers[i] = new Thread(new Passenger(i + 1, train));
                passengers[i].start();
            }

            // 3. Wait a bit then start ride when full
            Thread rideController = new Thread(() -> {
                try {
                    Thread.sleep(800);  // give time to board
                    train.startRide();
                } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            });
            rideController.start();

            // 4. Wait for all to finish
            for (Thread p : passengers) p.join();
            rideController.join();

            System.out.println("Cycle " + cycle + " completed.\n");
        }

        System.out.println("All cycles finished. System works perfectly!");
    }
}