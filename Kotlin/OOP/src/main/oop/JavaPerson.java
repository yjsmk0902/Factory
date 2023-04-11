public class JavaPerson {

    //Java의 필드
    private final String name;
    private int age;

    //Java의 주생성자
    public JavaPerson(String name, int age) {
        //Java의 검증로직
        if (age <= 0) {
            throw new IllegalArgumentException(String.format("나이(%s)는 1이상 이어야 합니다.", age));
        }
        this.name = name;
        this.age = age;
    }

    //Java의 나이가 1인 생성자 (부생성자)
    public JavaPerson(String name) {
        this(name, 1);
    }

    //Java의 Getter
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    //Java의 Setter
    public void setAge(int age) {
        this.age = age;
    }

    //Java의 성인인지 확인하는 기능 (Custom Getter)
    public boolean isAdult() {
        return this.age >= 20;
    }
}
