//해당 코드는 main.kt의 main함수 밖에 쓰여있음

val a = 3

fun add(a: Int, b: Int): Int {
    return a + b
}

////이렇게 쓰면 안되고
//class Member private(
//    val username:String,
//    val age: Int
//)
//이렇게 써야함
class Member private constructor(
    val username:String,
    val age:Int
)

fun main() {

    val person = Person("Luke", 10)

    //'.필드'로 Getter/Setter 호출 가능
    println(person.name)
    person.age=20

    println()

    val javaPerson = JavaPerson("Luke", 10)

    //Java에서 가져온 클래스도 마찬가지
    println(javaPerson.name)
    javaPerson.age=20

    println()

    //부생성자 호출시 호출 순서 테스트
    val constructorTest = Person()

}