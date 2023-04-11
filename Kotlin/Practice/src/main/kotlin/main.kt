import org.jetbrains.annotations.NotNull
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException
import java.lang.IllegalArgumentException
import java.lang.NumberFormatException

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
    val person = Person("who", 10)

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
    val personA = Person("의으이ㅡ이ㅡ읭", 10)
    fun startsWithPersonA(str: String): Boolean {
        return str.startsWith("A")
    }
    startsWithPersonA(personA.name)
    //  Java 코드에서 null에 관한 정보를 어노테이션을 통해 전달한다면
    //  Kotlin에서 이를 이해하고 사용할 수 있음

    //  하지만 그를 알 수 없는 '플랫폼 타입' 이라면 알아서 잘 찾아 처리해야 함

    //9. 기본 타입

    //  Java와 동일하게 Byte / Short / Int / Long / Float / Double 등이 있음
    val numberInt = 3       //Int
    val numberLong = 3L     //Long
    val numberDouble = 3.0  //Double
    val numberFloat = 3.0f  //Float

    //  Java와 다른 점 =>
    //      Java    -> 기본 타입간의 변환 = 암시적
    //      Kotile  -> 기본 타입간의 변환 = 명시적

    //Java의 경우
    //  int numberInt = 4;
    //  long numberLong = numberInt;
    //  sysout.println(numberInt + numberLong);

    //Kotlin의 경우 => 'to변환타입()'을 사용해야함
    val numberIntKt = 4
    val numberLongKt: Long = numberIntKt.toLong()

    //##주의. nullable변수 처리 잘해야함
    val numberNullableInt: Int? = 3
    val numberNullableLong: Long = numberNullableInt?.toLong() ?: 0L

    //10. 타입 캐스팅 => instanceof -> is / !instanceof -> !is

    //Java의 경우
    //    public static void printAgeIfPerson(Object obj){
    //        if(obj instanceof Person){
    //            Person person = (Person) obj;
    //            sysout.println(person.getAge());
    //        }
    //    }

    //Kotlin의 경우
    fun printAgeIfPerson(obj: Any) {
        if (obj is Person) {
            println(obj.age)
        }
    }

    //obj에 null이 들어오는 경우
    fun printAgeIfPersonNullable(obj: Any?) {
        val person = obj as? Person
        println(person?.age)
    }

    //11. Kotlin의 특이한 타입 3가지

    //Any <=> Java. Object
    //  Java의 Object 역할 (모든 객체의 최상위 타입)
    //  모든 Primitive Type의 최상위 타입도 Any
    //  Any 자체로는 null을 포함할 수 없어 null을 포함하고 싶다면, Any?로 표현
    //  Any에 equals / hashCode / toString 존재

    //Unit <=> Java. void
    //  Java의 void와 동일한 역할
    //  void와는 다르게 Unit은 그 자체로 타입 인자로 사용 가능함
    //  함수형 프로그래밍에서 Unit은 단 하나의 인스턴스만 갖는 타입을 의미
    //  즉, 코틀린의 Unit은 실제 존재하는 타입이라는 것을 표현

    //Nothing
    //  함수가 정상적으로 끝나지 않았다는 사실을 표현하는 역할
    //  무조건 예외를 반환하는 함수 또는 무한 루프 함수 등
    fun fail(message: String): Nothing {
        throw IllegalArgumentException(message)
    }

    //12. String interpolation / String indexing

    //${변수}로 바로 사용가능
    val me = Person("Luke", 10)
    val log = "My name is ${me.name}, and ${me.age}years old."

    //여러 줄에 걸친 문자열을 작성해야 할 때
    val withoutIndent =
        """
            ABC
            123
            456
            789
            ${me.age}
        """.trimIndent()

    //문자열 가져올 때 => 배열처럼 그냥 가져올 수 있음
    val str = "ABCD"
    val ch = str[1]

    //13. 단항 연산자 / 산술 연산자

    //  단항 연산자 -> ++ / --
    //  산술 연산자 -> + / - / * / / %
    //  산술대입 연산자 -> += / -= / *= / /= / %=

    //14. 비교 연산자와 동등성, 동일성

    val operatorA = Person("A", 10)
    val operatorB = operatorA
    val operatorC = Person("A", 10)

    //  비교 연산자 -> > / < / >= / <=
    //  코틀린의 경우 객체 비교시, 자동으로 compareTo를 호출해줌

    if (operatorA > operatorB) {
        println("operatorA.age > operatorB.age")
    }

    //  동등성(Equaulity)  => 두 객체의 값이 같은가?
    //  동일성(Identity)   => 완전히 동일한 객체인가? (Reference)
    //          동등성             동일성
    //  Java    equals            ==
    //  Kotlin  ==                ===

    if (operatorA === operatorB) {
        println("operatorA와 operatorB는 동일하다")
    }
    if (operatorA == operatorC) {
        println("operatorA와 operatorC는 동등하다")
    }

    //15. 논리 연산자 / 코틀린에 있는 특이한 연산자

    //  논리 연산자 -> && / || / ! => Java와 완전히 동일

    //  in / !in -> 컬렉션이나 범위에 포함되어 있다 / 포함되어 있지 않다
    if (2 in (1..10)) {
        println("2는 1 ~ 10 사이에 포함")
    }
    if (15 in (1..10)) {
        println("15는 1 ~ 10 사이에 미포함")
    }

    //16. 연산자 오버로딩

    println(operatorA + operatorB)

    //17. if문

    //  Java와 Kotlin 문법은 동일하나 방식이 다름
    //  Java의 경우 Statement, Kotlin은 Expression 방식임

    //18. Expression과 Statement

    //  Statement   => 프로그램의 문장, 하나의 값으로 도출되지 않음
    //  Expression  => 하나의 값으로 도출되는 문장

    //  Java
    //    if( javaA > javaB){
    //        return true;
    //    }else{
    //        return false;
    //    }

    //  Kotlin
    val kotlinA = 1
    val kotlinB = 2
    val returnValue = if (kotlinA > kotlinB) {
        1
    } else {
        0
    }

    //  따라서 Java에는 3항연산자가 있지만 Kotlin에는 없다

    fun getGrade(score: Int): String {
        return if (score >= 90) {
            "A"
        } else if (score >= 80) {
            "B"
        } else if (score >= 70) {
            "C"
        } else {
            "D"
        }
    }

    //19. switch와 when

    //  Java에서는 switch / Kotlin에서는 when을 씀
    //  예시를 잘보면 될듯

    fun getGradeWithSwitchV1(score: Int): String {
        return when (score / 10) {
            9 -> "A"
            8 -> "B"
            7 -> "C"
            else -> "D"
        }
    }

    fun getGradeWithSwitchV2(score: Int): String {
        return when (score) {
            in (100..90) -> "A"
            in (89..80) -> "B"
            in (79..70) -> "C"
            else -> "D"
        }
    }

    fun judgeNumber(number: Int) {
        when (number) {
            1, 0, -1 -> println("어디서 많이 본 숫자입니다")
            else -> println("다른 숫자입니다")
        }
    }

    fun judgeNumber2(number: Int) {
        when {
            number == 0 -> println("주어진 숫자는 0입니다")
            number % 2 == 0 -> println("주어진 숫자는 짝수입니다")
            else -> println("주어지는 숫자는 홀수입니다")
        }
    }

    //  진짜 개좋다...이게 코딩이지 when은 사기다

    //20. for-each문

    //  Java
    //    List<Long> numbers = Arrays.asList(1L, 2L, 3L);
    //    for(long number : numbers){
    //        Sysout.println(number);
    //    }

    //  Kotlin
    val numbers = listOf(1L, 2L, 3L)
    for (number in numbers) {
        println(number)
    }

    //21. 전통적인 for문

    //  Java
    //    for (int i = 1; i<=3; i++) {
    //        println(i)
    //    }

    //  Kotlin
    for (i in 1..3) {
        println(i)
    }

    //22. Progression과 Range

    //  내려가는 경우
    for (i in 3 downTo 1) {
        println(i)
    }
    //  두칸씩 올라가는 경우
    for (i in 1..10 step 2) {
        println(i)
    }

    //23. while문 -> Java, Kotlin 완전 동일

    //24. try / catch / finally 구문

    //  Ex.1) 주어진 문자열을 정수로 변경

    //  Java
    //    private Integer parseIntOrThrowV1(@NotNull String str){
    //        try{
    //            return Integer.parseInt(str);
    //        }catch(NumberFormatException e){
    //            throw new IllegalArgumentException(String.format("주어진 %s는 숫자가 아닙니다.", str));
    //        }
    //    }

    //  Kotlin
    fun parseIntOrThrowV1(str: String): Int {
        try {
            return str.toInt()
        } catch (e: NumberFormatException) {
            throw IllegalArgumentException("주어진 ${str}는 숫자가 아닙니다.")
        }
    }
    //      타입이 뒤에 위치, new를 사용하지 않음, 포맷팅이 간결
    //      try/catch 구문은 Java와 완전히 동일

    //  Ex.2) 주어진 문자열을 정수로 변경하는 예제 (실패시 null을 반환)

    //  Java
    //    private Integer parseIntOrThrowV2(@NotNull String str){
    //        try{
    //            return Integer.parseInt(str);
    //        }catch{
    //            return null;
    //        }
    //    }

    //  Kotlin
    fun parseIntOrThrowV2(str: String): Int? {
        return try {
            str.toInt()
        } catch (e: NumberFormatException) {
            null
        }
    }
    //      if/else문과 마찬가지로 try/catch문도 Statement가 아닌 Expression임
    //      따라서 값으로 return 가능

    //  try/catch/finally구문도 Java와 일치

    //25. Checked Exception과 Unchecked Exception

    //  Java
    //    public void readFile() throws IOException{
    //        File currentFile = new File(".");
    //        File file = new File(currentFile.getAbsolutePath() + "/a.txt");
    //        BufferedReader reader = new BufferedReader(new FileReader(file));
    //        Sysout.println(reader.readLine());
    //        reader.close();
    //    }

    //  Kotlin
    fun readFile() {
        val currentFile = File(".")
        val file = File(currentFile.absolutePath + "/a.txt")
        val reader = BufferedReader(FileReader(file))
        println(reader.readLine())
        reader.close()
    }
    //      코틀린은 모든 Exception을 Unchecked Exception으로 처리함

    //26. try with resources 구문 (JDK7)

    //  try(자원 받고){try문 실행 후 알아서 자원 반납}

    //  Java
    //    public void readFile(String path) throws IOExeption{
    //        try(BufferedReader reader = new BufferedReader(new FileReader(path))){
    //            Sysout.println(reader.readLine());
    //        }
    //    }

    //  Kotlin
    fun readFile(path: String) {
        BufferedReader(FileReader(path))
            .use { reader ->
                println(reader.readLine())
            }
    }

    //      Kotlin에는 try with resources 구문이 없는 대신 use라는 inline 확장함수를 사용함

    //27. 함수 선언 문법

    //  Ex.1) 두 정수를 받아 더 큰 정수를 반환 -> =으로 return을 지우고 return할 값을 바로 넘길 수 있음
    fun max(a: Int, b: Int): Int = if (a > b) a else b

    //28. Default Parameter

    //  Ex.1) 주어진 문자열을 N번 출력
    //      fun 함수이름(파라미터:파라미터 타입 = Default)와 같이 Default Parameter 설정 가능
    fun repeat(str: String, num: Int = 3, useNewLine: Boolean = true) {
        for (i in 1..num) {
            if (useNewLine) {
                println(str)
            } else {
                print(str)
            }
        }
    }

    //  다음과 같이 Default Parameter를 설정하여 오버로딩을 구현할 수 있지만
    //  Java와 동일하게 오버로딩을 구현하는 것도 가능함

    //29. Named Argument (parameter)

    //  위와 같은 함수를 이름을 부여해서 호출가능
    repeat("Hello world\n", useNewLine = false)
    //  파라미터를 명시하여 지정해서 넣을 수 있음
    //  명시하지 않은 파라미터는 자동적으로 Default 값을 사용
    //  이와 같은 특징은 builder를 직접 만들지 않고 builder의 장점을 갖음

    //  Ex.1) name과 gender를 받아 출력해주는 함수
    fun printNameAndGender(name: String, gender: String) {
        println(name)
        println(gender)
    }

    printNameAndGender("male", "Luke")     //다음과 같이 헷갈리는 경우를
    printNameAndGender(gender = "male", name = "Luke")  //이처럼 헷갈리지 않게 쓸 수 있음

    //  단, Kotlin에서 Java함수를 가져다 쓰는 경우에는 Named Argument를 쓸 수 없음

    //30. 같은 타입의 여러 파라미터 받기 (가변인자)

    //  Ex.1) 문자열을 N개 받아 출력하는 예제
    fun printAll(vararg strings: String) {  //...이 아닌 앞에 vararg를 써서 받아줌
        for (str in strings) {
            println(str)
        }
    }
    printAll("A", "B", "C")
    val array = arrayOf("A", "B", "C")
    printAll(*array)    //배열을 바로 넣는 대신에 Spread 연산자(*)를 붙여주어야 함

}



