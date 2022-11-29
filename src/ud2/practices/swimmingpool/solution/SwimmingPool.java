package ud2.practices.swimmingpool.solution;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class SwimmingPool {
    private int poolCapacity;
    private int showersCapacity;

    // TODO: Add semaphores
    private Semaphore poolSemaphore;
    private Semaphore showersSemaphore;

    public SwimmingPool(int poolCapacity, int showersCapacity) {
        this.poolCapacity = poolCapacity;
        this.showersCapacity = showersCapacity;

        // TODO: Create semaphores
        poolSemaphore = new Semaphore(10);
        showersSemaphore= new Semaphore(3);
    }

    // TODO: create get() method for semaphores
    public Semaphore getPoolSemaphore() {
        return poolSemaphore;
    }

    public Semaphore getShowersSemaphore() {
        return showersSemaphore;
    }

    public static void main(String[] args) {
        SwimmingPool pool = new SwimmingPool(10, 3);
        String[] names = {
            "Andrès", "Àngel", "Anna", "Carles", "Enric",
            "Helena", "Isabel", "Joan", "Lorena", "Mar",
            "Maria", "Marta", "Míriam", "Nicolàs", "Òscar",
            "Paula", "Pere", "Teresa", "Toni", "Vicent"
        };
        List<PersonThread> persons = new ArrayList<>();
        for(String name : names) {
            // TODO: Create the threads and start them
            PersonThread p = new PersonThread(name, pool);
            persons.add(p);
            p.start();
        }

        try {
            // TODO: Wait 60 seconds and kick all persons
            Thread.sleep(60000);
            System.out.println("S'està tancant la piscina.");
            for(Thread t : persons)
                t.interrupt();

            // TODO: Wait for all persons to leave
            for(Thread t : persons)
                t.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Tothom ha marxat de la piscina.");
    }
}
