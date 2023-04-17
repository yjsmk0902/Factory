import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JavaMain {
    public static void main(String[] args) {
        int[] array = {100, 200};
        for (int i = 0; i < array.length; i++) {
            System.out.printf("%s %s", i, array[i]);
        }
        final List<Integer> numbers = Arrays.asList(100, 200);

        //JDK 8까지
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "MONDAY");
        map.put(2, "TUESDAY");

        //JDK 9부터
        Map.of(1, "MONDAY", 2, "TUESDAY");
    }
}
