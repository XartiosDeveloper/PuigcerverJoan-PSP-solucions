package ud2.practices.swimmingpool;

public class PersonThread extends Thread {
    private SwimmingPool pool;

    public PersonThread(String name, SwimmingPool pool) {
        super(name);
        this.pool = pool;
    }

    @Override
    public void run() {
        while(secondsElapsed < 60) {
            // TODO: Descansar ente 1 i 5 segons
            // TODO: Dutxar-se
            // TODO: Banyar-se entre 1 i 10 segons
        }
    }
}
