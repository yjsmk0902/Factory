//Java의 상속
public class JavaCat extends JavaAnimal {

    //생성자 상속
    public JavaCat(String species) {
        super(species,4);
    }

    //상속받은 추상 메소드 재정의
    @Override
    public void move() {
        System.out.println("JavaCat Move");
    }
}
