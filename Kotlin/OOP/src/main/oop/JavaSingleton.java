public class JavaSingleton {

    private static final JavaSingleton INSTANCE = new JavaSingleton();

    public JavaSingleton() {}

    public static JavaSingleton getInstance() {
        return INSTANCE;
    }
}
