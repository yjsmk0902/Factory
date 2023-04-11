//Java의 추상 클래스
public abstract class JavaAnimal {

    protected final String species;
    protected final int legCount;

    public JavaAnimal(String species, int legCount) {
        this.species = species;
        this.legCount = legCount;
    }

    //추상 메소드
    abstract public void move();

    //Getter
    public String getSpecies() {
        return species;
    }
    public int getLegCount() {
        return legCount;
    }
}
