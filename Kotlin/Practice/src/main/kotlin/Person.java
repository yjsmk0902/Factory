import org.jetbrains.annotations.NotNull;

public class Person {
    private final String name;
    public final int age;


    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @NotNull
    public String getName() {
        return name;
    }

    public int compareTo(@NotNull Person operatorB) {
        return Long.compare(this.age, operatorB.age);
    }

    @NotNull
    public int plus(@NotNull Person operatorB) {
        return this.age + operatorB.age;
    }
}
