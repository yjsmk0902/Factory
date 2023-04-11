//Java 인터페이스

public interface JavaFlyable {

    default void act() {
        //default 바디
        System.out.println("Flying");
    }
}
