import Person.kt.Person

fun main() {

    //1. 변수 선언 키워드 - var / val의 차이점
    var num = 10L           //long num = 10L;
    val numFinal = 10L      //long final numFinal = 10L;    (readOnly)
    //초기값을 지정해주지 않으면 에러 발생

    //2. Kotlin에서의 Primitive Type
    //  숫자, 문자, 불리언과 같은 타입은 내부적으로 특별한 표현을 갖음
    //  해당 타입들은 실행시에 Primitive Value로 표현되지만, 코드에서는 평범한 클래스로 보임
    //  즉, 프로그래머가 boxing / unboxing을 고려하지 않아도 되도록 Kotlin이 알아서 처리해줌

    //3. Kotlin에서의 nullable 변수
    //  Kotlin에서 null이 변수에 들어갈 수 있다면 '타입?'을 사용해야함
    var numNull: Long? = 1_000L
    numNull = null

    //4. Kotlin에서의 객체 인스턴스화
    //  Kotlin에서 객체 인스턴스화를 할때에는 new를 붙이지 않음
    val person = Person("who")
}
