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