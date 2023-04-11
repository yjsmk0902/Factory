//Kotlin의 상속
class Cat(
    //생성자 상속
    species: String
) : Animal(species, 4) {

    //상속받은 추상 메소드 재정의
    override fun move() {   //어노테이션 없이 override를 붙여 씀
        println("Cat Move")
    }
}