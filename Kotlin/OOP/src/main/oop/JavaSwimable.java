//Java 인터페이스
public interface JavaSwimable {

    default void act() {
        //default 바디

        System.out.println("Swimming");
    }
}
