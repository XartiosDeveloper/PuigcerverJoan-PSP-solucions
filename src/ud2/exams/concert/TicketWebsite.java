package ud2.exams.concert;

public class TicketWebsite {
    private int totalTickets;
    private int ticketsLeft;

    public TicketWebsite(int totalTickets) {
        this.totalTickets = totalTickets;
        this.ticketsLeft = totalTickets;
    }

    public int getNextTicket(){
        int nextTicket = ticketsLeft;
        ticketsLeft--;
        return nextTicket;
    }
    public int buyTicket() throws InterruptedException {
        System.out.printf("%s està a la cua.\n", Thread.currentThread().getName());
        System.out.printf("%s està comprant l'entrada.\n", Thread.currentThread().getName());
        Thread.sleep(2500);
        int ticket = getNextTicket();
        System.out.printf("%s ha acabat la compra.\n", Thread.currentThread().getName());
        System.out.printf("%s ha eixit de la cua.\n", Thread.currentThread().getName());
        return ticket;
    }
}
