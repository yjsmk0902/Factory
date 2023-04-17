import java.util.Arrays;
import java.util.List;

public class JavaMain {
    public static void main(String[] args) {
        moveSomething(new Movable() {
            @Override
            public void move() {
                System.out.println("Move");
            }

            @Override
            public void fly() {
                System.out.println("Fly");            }
        });

        int[] array = {100, 200};
        for (int i = 0; i < array.length; i++) {
            System.out.printf("%s %s", i, array[i]);
        }
        final List<Integer> numbers = Arrays.asList(100, 200);
    }

    private static void moveSomething(Movable movable) {
        movable.move();
        movable.fly();
    }
}


