import java.lang.IllegalArgumentException

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

    //5. Kotlin에서의 null 체크

    //자바의 경우
    //  case1 => null일 경우를 조건문으로 잡아 Exception을 냄
    //  case2 => null일 경우, 조건문으로 잡아 null을 리턴함
    //  case3 => null일 경우, 조건문으로 잡아 false를 리턴함

    //코틀린의 경우
    //Case1.
    fun startsWithCase1(str: String?): Boolean {
        if (str == null) {
            throw IllegalArgumentException("null이 들어옴")
        }
        return str.startsWith("A")
    }
    //Case2.
    fun startsWithCase2(str: String?): Boolean? {
        if (str == null) {
            return null
        }
        return str.startsWith("A")
    }
    //Case3.
    fun startsWithCase3(str: String?): Boolean {
        if (str == null) {
            return false
        }
        return str.startsWith("A")
    }
    //'타입?'으로 null값의 가능성을 열어둠

    //6. Safe Call과 Elvis 연산자

    //  Safe Call => null이 아니면 실행하고, null이면 실행하지 않는다 (그대로 null)
    val strSafeCall: String? = "ABC"
    //strSafeCall.length    => 불가능
    strSafeCall?.length  // => 가능

    //  Elvis 연산자 => 앞의 연산 결과가 null이면 뒤의 값을 사용
    val strElvis: String? = "ABC"
    strElvis?.length ?: 0

    //5의 코드들을 6을 활용해 좀더 코틀린스럽게 바꾼다면?

    //Case1.
    fun startsWithKotlinCase1(str: String?): Boolean {
        return str?.startsWith("A")
            ?: throw IllegalArgumentException("null이 들어옴")
    }
    //Case2.
    fun startsWithKotlinCase2(str: String?): Boolean? {
        return str?.startsWith("A")
    }
    //Case3.
    fun startsWithKotlinCase3(str: String?): Boolean {
        return str?.startsWith("A") ?: false
    }

    //7. null 아님 단언 => nullable type이지만, 아무리 생각해도 null이 될 수 없는 경우

    fun startsWithNeverNull(str: String?): Boolean {
        return str!!.startsWith("A")
    }

    //8. 플랫폼 타입

    //  Kotlin에서 Java 코드를 가져다 사용할 때 어떻게 처리될까?
    val personA = Person("의으이ㅡ이ㅡ읭")
    fun startsWithPersonA(str: String): Boolean {
        return str.startsWith("A")
    }
    startsWithPersonA(personA.name)
    //  Java 코드에서 null에 관한 정보를 어노테이션을 통해 전달한다면
    //  Kotlin에서 이를 이해하고 사용할 수 있음

    //  하지만 그를 알 수 없는 '플랫폼 타입' 이라면 알아서 잘 찾아 처리해야 함
}


