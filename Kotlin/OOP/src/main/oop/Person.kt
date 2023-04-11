import java.lang.IllegalArgumentException

//'constructor'로 주생성자 역할 (원하면 생략가능)
class Person(
    //Kotlin의 프로퍼티
    //부생성자 대신 Default Parameter 권장
    val name: String = "홍길동",
    var age: Int = 1,
) {
    //Kotlin의 검증로직
    init {  //init블록 -> 생성자가 호출되는 시점에 호출됨
        if (age <= 0) {
            throw IllegalArgumentException("나이${age}는 1이상 이어야 합니다.")
        }
        println("초기화")
    }

    //Kotlin은 이같은 부생성자 생성방식을 추천하지 않음
//    //Kotlin의 나이가 1인 생성자 (부생성자)
//    constructor(name: String) : this(name, 1){
//        println("부생성자1")    //다음과 같이 생성자 블록에 바디를 작성할 수 있음
//    }
//    //Kotlin의 이름이 홍길동인 생성자
//    constructor() : this("홍길동"){
//        println("부생성자2")
//    }

    //Converting과 같은 경우에는, 부생성자를 사용할 수 있지만
    //웬만하면 정적 팩토리 메소드를 사용하는게 나음

    //Kotlin의 성인인지 확인하는 기능 (Custom Getter)
    fun isAdultV1(): Boolean = this.age >= 20

    //밑에 두 개가 Custom Getter
    val isAdultV2: Boolean
        get() = this.age >= 20
    val isAdultV3: Boolean
        get() {
            return this.age >= 20
        }

}
//프로퍼티만 만들어주면 자동으로 Getter/Setter를 만들어줌