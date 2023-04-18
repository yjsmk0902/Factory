import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class JavaFruit {
    private final String name;
    private final int price;

    public JavaFruit(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    private List<JavaFruit> findApples(List<JavaFruit> fruits) {
        List<JavaFruit> apples = new ArrayList<>();
        for (JavaFruit fruit : fruits) {
            if (fruit.getName().equals("사과")) {
                apples.add(fruit);
            }
        }
        return apples;
    }
    private List<JavaFruit> findBananas(List<JavaFruit> fruits) {
        List<JavaFruit> bananas = new ArrayList<>();
        for (JavaFruit fruit : fruits) {
            if (fruit.getName().equals("바나나")) {
                bananas.add(fruit);
            }
        }
        return bananas;
    }

    private List<JavaFruit> findFruitsWithName(List<JavaFruit> fruits, String name) {
        List<JavaFruit> results = new ArrayList<>();
        for (JavaFruit fruit : fruits) {
            if(fruit.getName().equals(name)){
                results.add(fruit);
            }
        }
        return results;
    }

    private List<JavaFruit> filterFruits(List<JavaFruit> fruits, Predicate<JavaFruit> fruitFilter) {
//        List<Fruit> results = new ArrayList<>();
//        for (Fruit fruit : fruits) {
//            if (fruitFilter.test(fruit)) {
//                results.add(fruit);
//            }
//        }
//        return results;
        return fruits.stream()
                .filter(fruitFilter)
                .collect(Collectors.toList());
    }

    public void main(String[] args) {
        List<JavaFruit> fruits = Arrays.asList(
                new JavaFruit("사과", 1_000),
                new JavaFruit("사과", 1_200),
                new JavaFruit("사과", 1_200),
                new JavaFruit("사과", 1_500),
                new JavaFruit("바나나", 3_000),
                new JavaFruit("바나나", 3_200),
                new JavaFruit("바나나", 2_500),
                new JavaFruit("수박", 1_000)
        );
        filterFruits(fruits, JavaFruit::isApple);
    }

}
