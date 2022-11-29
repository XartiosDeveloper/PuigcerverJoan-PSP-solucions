package ud2.exams.sushi;

public class Sushi {
    public static void main(String[] args) {
        SushiRestaurant restaurant = new SushiRestaurant(10);
        Waiter w1 = new Waiter(5, restaurant);
        Waiter w2 = new Waiter(7, restaurant);

        restaurant.start();
        w1.start();
        w2.start();
    }
}
