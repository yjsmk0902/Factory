//Kotlin의 상속
class Penguin(
    //생성자 상속
    species: String,
    //상속받은 클래스 속 새로운 필드
    private val wingCount: Int = 2
) : Animal(species, 2),
Flyable, Swimable{

    //상속받은 추상 메소드 재정의
    override fun move() {
        println("Penguin Move")
    }

    //부모 프로퍼티 상속받아 Getter 재정의
    override val legCount:Int
        get() = super.legCount + this.wingCount

    //인터페이스 구현
    override fun act() {
        super<Swimable>.act()
        super<Flyable>.act()
    }

    override val swimAbility: Int
        get() = 5
}