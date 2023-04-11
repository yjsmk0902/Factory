//Java의 상속
public final class JavaPenguin extends JavaAnimal implements JavaFlyable, JavaSwimable {

    //상속받은 클래스 속 새로운 필드
    private final int wingCount;

    //생성자 상속
    public JavaPenguin(String species) {
        super(species, 2);
        this.wingCount = 2;
    }

    //상속받은 추상 메소드 재정의
    @Override
    public void move() {
        System.out.println("Penguin Move");
    }

    //부모 메소드 상속받아 재정의
    @Override
    public int getLegCount() {
        return super.getLegCount() + this.wingCount;
    }

    //인터페이스 구현
    @Override
    public void act() {
        JavaSwimable.super.act();
        JavaFlyable.super.act();
    }
}
