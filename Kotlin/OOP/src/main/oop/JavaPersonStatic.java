public class JavaPersonStatic {

    private static final int MIN_AGE = 1;

    public static JavaPersonStatic newBaby(String name) {
        return new JavaPersonStatic(name, MIN_AGE);
    }

    private String name;
    private int age;

    private JavaPersonStatic(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
