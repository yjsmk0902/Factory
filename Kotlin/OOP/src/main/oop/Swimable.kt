//Kotlin 인터페이스
interface Swimable {

    //Kotlin은 인터페이스에 프로퍼티도 구현가능
    val swimAbility: Int
        get() = 3 // => default값

    //default 없이 메소드 구현 가능
    fun act() = println("Swimming ")
}