💻 코틀린 기초
=============
</br>

📖 변수 선언 키워드 - var / val의 차이점
-------------

```kotlin
    var num = 10L           //long num = 10L;
    val numFinal = 10L      //long final numFinal = 10L; (readOnly)
```
> 초기값을 지정해주지 않으면 에러 발생
***
</br>

📖 Kotlin에서의 Primitive Type
-------------

> 숫자, 문자, 불리언과 같은 타입은 내부적으로 __특별한 표현__ 을 갖음
>   
> 해당 타입들은 실행시에 __Primitive Value__ 로 표현되지만, 코드에서는 평범한 클래스로 보임
>   
> 즉, 프로그래머가 boxing / unboxing을 고려하지 않아도 되도록 __Kotlin이 알아서 처리__ 해줌
***
</br>

📖 Kotlin에서의 nullable 변수
-------------

> Kotlin에서 null이 변수에 들어갈 수 있다면 __'타입?'__ 을 사용해야함
```kotlin
    var numNull: Long? = 1_000L
    numNull = null
```
***
</br>

📖 Kotlin에서의 객체 인스턴스화
-------------

> Kotlin에서 객체 인스턴스화를 할때에는 __new를 붙이지 않음__
```kotlin
    val person = Person("who", 10)
```
***
</br>

📖 Kotlin에서의 null 체크
-------------

> ### Java의 경우
>   >
>   > Case1. null일 경우를 조건문으로 잡아 Exception을 냄
>   >
>   > Case2. null일 경우, 조건문으로 잡아 null을 리턴함
>   >
>   > Case3. null일 경우, 조건문으로 잡아 false를 리턴함
&nbsp; 

> ### Kotlin의 경우
>   > ### Case1.
>  ```kotlin
>    fun startsWithCase1(str: String?): Boolean {
>        if (str == null) {
>            throw IllegalArgumentException("null이 들어옴")
>        }
>        return str.startsWith("A")
>    }
>```
>   > ### Case2.
>```kotlin
>    fun startsWithCase2(str: String?): Boolean? {
>        if (str == null) {
>            return null
>        }
>        return str.startsWith("A")
>    }
>```
>   > ### Case3.
>    ```kotlin
>    fun startsWithCase3(str: String?): Boolean {
>        if (str == null) {
>            return false
>        }
>        return str.startsWith("A")
>    }
>    ```
>    __'타입?'__ 으로 null값의 가능성을 열어둠
***
</br>

📖 Safe Call과 Elvis 연산자
-------------

> ### Safe Call __('함수?')__
>   null이 아니면 실행하고, null이면 실행하지 않는다 (그대로 null)
```kotlin
    val strSafeCall: String? = "ABC"
    //strSafeCall.length    => 불가능
    strSafeCall?.length  // => 가능
```
> ### Elvis 연산자 __('?:')__
>   앞의 연산 결과가 null이면 뒤의 값을 사용
```kotlin
    val strElvis: String? = "ABC"
    strElvis?.length ?: 0
```
>   위의 코드들을 연산자를 활용해 좀 더 코틀린스럽게 바꾼다면?
>   > 
>   > ### Case1.
>   ```kotlin
>        fun startsWithKotlinCase1(str: String?): Boolean {
>            return str?.startsWith("A")
>                ?: throw IllegalArgumentException("null이 들어옴")
>        }
>   ```
>   > ### Case2.
>   ```kotlin
>       fun startsWithKotlinCase2(str: String?): Boolean? {
>           return str?.startsWith("A")
>       }
>   ```
>   > ### Case3.
>   ```kotlin
>       fun startsWithKotlinCase3(str: String?): Boolean {
>           return str?.startsWith("A") ?: false
>       }
>   ```
> &nbsp; 
***
</br>

📖 null 아님 단언 __('!!')__
-------------
    
> nullable type이지만, 아무리 생각해도 null이 될 수 없는 경우
```kotlin
    fun startsWithNeverNull(str: String?): Boolean {
        return str!!.startsWith("A")
    }
```
***
</br>

📖 플랫폼 타입
-------------

> Kotlin에서 Java 코드를 가져다 사용할 때 어떻게 처리될까?
```kotlin
    //Person은 Java클래스
    val personA = Person("의으이ㅡ이ㅡ읭", 10)
    fun startsWithPersonA(str: String): Boolean {
        return str.startsWith("A")
    }    
    startsWithPersonA(personA.name)
```
>Java 코드에서 null에 관한 정보 __(@NotNull)__ 를 어노테이션을 통해 전달한다면
>Kotlin에서 이를 이해하고 사용할 수 있음

>하지만 그를 알 수 없는 '플랫폼 타입' 이라면 알아서 잘 찾아 처리해야 함
***
</br>

📖 기본 타입
-------------

> Java와 동일하게 Byte / Short / Int / Long / Float / Double 등이 있음

    val numberInt = 3       //Int
    val numberLong = 3L     //Long
    val numberDouble = 3.0  //Double
    val numberFloat = 3.0f  //Float

> ### Java와 다른 점
>   > Java    -> 기본 타입간의 변환 = __암시적__
>   > 
>   > Kotiln  -> 기본 타입간의 변환 = __명시적__
>   >

> ### Java의 경우
>```java
>    int numberInt = 4;
>    long numberLong = numberInt;
>    sysout.println(numberInt + numberLong);
>```

> ### Kotlin의 경우 
>   > __'to변환타입()'__ 을 사용해야함
>```kotlin
>    val numberIntKt = 4
>    val numberLongKt: Long = numberIntKt.toLong()
>```
>   > __주의__ . nullable변수 처리 잘해야함
>```kotlin
>    val numberNullableInt: Int? = 3
>    val numberNullableLong: Long = numberNullableInt?.toLong() ?: 0L
>```
>&nbsp;    
***
</br>

📖 타입 캐스팅 
-------------
> ### instanceof -> __is__
> 
> ### !instanceof -> __!is__

>   > ### Java의 경우
>   ```java
>   public static void printAgeIfPerson(Object obj){
>       if(obj instanceof Person){
>           Person person = (Person) obj;
>           sysout.println(person.getAge());
>       }
>   }
>   ```

>   > ### Kotlin의 경우
>   ```kotlin
>       fun printAgeIfPerson(obj: Any) {
>           if (obj is Person) {
>               println(obj.age)
>           }
>       }
>   ```
>   > ### obj에 null이 들어오는 경우
>   ```kotlin
>       fun printAgeIfPersonNullable(obj: Any?) {
>           val person = obj as? Person
>           println(person?.age)
>       }
>   ```   
***
</br>

📖 Kotlin의 특이한 타입 3가지
-------------

> ### Any 
>   > __Java의 Object__ 역할 (모든 객체의 최상위 타입)
>
>   > 모든 Primitive Type의 최상위 타입도 Any
>
>   > Any 자체로는 null을 포함할 수 없어 __null을 포함하고 싶다면, Any?__ 로 표현
>
>   > Any에 equals / hashCode / toString 존재

> ### Unit
>   > __Java의 void__ 와 동일한 역할
>
>   > void와는 다르게 Unit은 그 자체로 __타입 인자로 사용 가능__ 함
>
>   > 함수형 프로그래밍에서 Unit은 __단 하나의 인스턴스__ 만 갖는 타입을 의미
>
>   > 즉, __코틀린의 Unit은 실제 존재하는 타입__ 이라는 것을 표현

> ### Nothing
>   > 함수가 정상적으로 __끝나지 않았다는 사실을 표현__ 하는 역할
>
>   > 무조건 예외를 반환하는 함수 또는 무한 루프 함수 등
```kotlin
    fun fail(message: String): Nothing {
        throw IllegalArgumentException(message)
    }
```
***
</br>

📖 String interpolation / String indexing
-------------

> ### __'${변수}'__ 로 바로 사용가능
```kotlin
    val me = Person("Luke", 10)
    val log = "My name is ${me.name}, and ${me.age}years old."
```
> ### 여러 줄에 걸친 문자열을 작성해야 할 때
```kotlin
    val withoutIndent =
        """
            ABC
            123
            456
            789
            ${me.age}
        """.trimIndent()
```
> ### 문자열 가져올 때 => __배열__ 처럼 그냥 가져올 수 있음
```kotlin
    val str = "ABCD"
    val ch = str[1]
```    
***
</br>

📖 단항 연산자 / 산술 연산자
-------------

> ### 단항 연산자 -> __++ / --__
>
> ### 산술 연산자 -> __+ / - / * / / / %__
>
> ### 산술대입 연산자 -> __+= / -= / *= / /= / %=__
***
</br>

📖 비교 연산자와 동등성, 동일성
-------------
```kotlin
    //사전 준비
    val operatorA = Person("A", 10)
    val operatorB = operatorA
    val operatorC = Person("A", 10)
```
> ### 비교 연산자 -> > / < / >= / <=
>
>  > 코틀린의 경우 객체 비교시, 자동으로 compareTo를 호출해줌
>  ```kotlin
>      if (operatorA > operatorB) {
>          println("operatorA.age > operatorB.age")
>      }
>  ```

> ### __동등성(Equaulity)__  => 두 객체의 값이 같은가?
>
> ### __동일성(Identity)__   => 완전히 동일한 객체인가? (Reference)

||__동등성__|__동일성__|
|:--:|:--:|:--:|
|__Java__|equals|==|
|__Kotlin__|==|===|

```kotlin
    if (operatorA === operatorB) {
        println("operatorA와 operatorB는 동일하다")
    }
    if (operatorA == operatorC) {
        println("operatorA와 operatorC는 동등하다")
    }
```
***
</br>

📖 논리 연산자 / 코틀린에 있는 특이한 연산자
-------------

> ### 논리 연산자 
>   > && / || / ! => Java와 완전히 동일

> ### in / !in 
>   > 컬렉션이나 범위에 __포함되어 있다 / 포함되어 있지 않다__
```kotlin
    if (2 in (1..10)) {
        println("2는 1 ~ 10 사이에 포함")
    }
    if (15 in (1..10)) {
        println("15는 1 ~ 10 사이에 미포함")
    }
```
***
</br>

📖 연산자 오버로딩
-------------
```java
    //Person클래스의 일부분
    @NotNull
    public int plus(@NotNull Person operatorB) {
        return this.age + operatorB.age;
    }
```
> ### 코틀린의 경우 특정 표현으로 자연스럽게 오버로딩 가능
```kotlin
    println(operatorA + operatorB)
```
***
</br>

📖 if문
-------------

> ### Java와 Kotlin 문법은 동일하나 방식이 다름
>
>   > Java의 경우 __Statement__, Kotlin은 __Expression__ 방식임
***
</br>

📖 Expression과 Statement
-------------

> ### Statement   
>   > 프로그램의 문장, __하나의 값으로 도출되지 않음__
>
> ### Expression  
>   > __하나의 값__ 으로 도출되는 문장

> ### Java의 if문
```java
    if( javaA > javaB){
        return true;
    }else{
        return false;
    }
```

> ### Kotlin의 if문
```kotlin
    val kotlinA = 1
    val kotlinB = 2
    val returnValue = if (kotlinA > kotlinB) {
        1
    } else {
        0
    }
    //if가 return 값을 가짐
```
> ### 따라서 Java에는 3항연산자가 있지만 Kotlin에는 없음
>
> 이처럼 사용가능
```kotlin
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
```
***
</br>

📖 switch와 when
-------------

> ### Java에서는 __switch__ / Kotlin에서는 __when__ 을 씀
>
> 예시를 잘보면 될듯
```kotlin
    fun getGradeWithSwitchV1(score: Int): String {
        return when (score / 10) {
            9 -> "A"
            8 -> "B"
            7 -> "C"
            else -> "D"
        }
    }
```
```kotlin
    fun getGradeWithSwitchV2(score: Int): String {
        return when (score) {
            in (100..90) -> "A"
            in (89..80) -> "B"
            in (79..70) -> "C"
            else -> "D"
        }
    }
```
```kotlin
    fun judgeNumberV1(number: Int) {
        when (number) {
            1, 0, -1 -> println("어디서 많이 본 숫자입니다")
            else -> println("다른 숫자입니다")
        }
    }
```
```kotlin
    fun judgeNumberV2(number: Int) {
        when {
            number == 0 -> println("주어진 숫자는 0입니다")
            number % 2 == 0 -> println("주어진 숫자는 짝수입니다")
            else -> println("주어지는 숫자는 홀수입니다")
        }
    }
```
> 진짜 개좋다...이게 코딩이지 __when__ 은 사기다
***
</br>

📖 for-each문
-------------

> ### Java의 for-each문
```java
    List<Long> numbers = Arrays.asList(1L, 2L, 3L);
    for(long number : numbers){
        Sysout.println(number);
    }
```
> ### Kotlin의 for-each문
>   > __'in'__ 으로 표현
```kotlin
    val numbers = listOf(1L, 2L, 3L)
    for (number in numbers) {   
        println(number)
    }
```
***
</br>

📖 전통적인 for문
-------------

> ### Java의 for문
```java
    for (int i = 1; i<=3; i++) {
        println(i)
    }
```
> ### Kotlin의 for문
>   > __'in'__ 과 __'..'__ 으로 표현
```kotlin
    for (i in 1..3) {   
        println(i)
    }
```
***
</br>

📖 Progression과 Range
-------------

> ### 내려가는 경우
>
>   > __'downTo'__ 사용
```kotlin
    for (i in 3 downTo 1) {
        println(i)
    }
```
> ### 두칸씩 올라가는 경우
>
>   > __'step'__ 사용
```kotlin
    for (i in 1..10 step 2) {
        println(i)
    }
```
***
</br>

📖 while문 
-------------
> ### Java, Kotlin 완전 동일
***
</br>

📖 try / catch / finally 구문
-------------

> ### Ex. 1) 주어진 문자열을 정수로 변경

> ### Java
```java
    private Integer parseIntOrThrowV1(@NotNull String str){
        try{
            return Integer.parseInt(str);
        }catch(NumberFormatException e){
            throw new IllegalArgumentException(String.format("주어진 %s는 숫자가 아닙니다.", str));
        }
    }
```

> ### Kotlin
```kotlin
    fun parseIntOrThrowV1(str: String): Int {
        try {
            return str.toInt()
        } catch (e: NumberFormatException) {
            throw IllegalArgumentException("주어진 ${str}는 숫자가 아닙니다.")
        }
    }
```
> 타입이 뒤에 위치, new를 사용하지 않음, 포맷팅이 간결
>
> try/catch 구문은 Java와 완전히 동일

> ### Ex. 2) 주어진 문자열을 정수로 변경하는 예제 (실패시 null을 반환)

> ### Java
    //    private Integer parseIntOrThrowV2(@NotNull String str){
    //        try{
    //            return Integer.parseInt(str);
    //        }catch{
    //            return null;
    //        }
    //    }

> ### Kotlin
    fun parseIntOrThrowV2(str: String): Int? {
        return try {
            str.toInt()
        } catch (e: NumberFormatException) {
            null
        }
    }
> if/else문과 마찬가지로 try/catch문도 Statement가 아닌 Expression임
>
> 따라서 값으로 __return 가능__
>
> try/catch/finally구문도 Java와 일치
***
</br>

📖 Checked Exception과 Unchecked Exception
-------------

> ### Java
```java
    public void readFile() throws IOException{
        File currentFile = new File(".");
        File file = new File(currentFile.getAbsolutePath() + "/a.txt");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        Sysout.println(reader.readLine());
        reader.close();
    }
```

> ### Kotlin
```kotlin
    fun readFile() {
        val currentFile = File(".")
        val file = File(currentFile.absolutePath + "/a.txt")
        val reader = BufferedReader(FileReader(file))
        println(reader.readLine())
        reader.close()
    }
```
> 코틀린은 __모든 Exception__ 을 __Unchecked Exception__ 으로 처리함
***
</br>

📖 try with resources 구문 (JDK7)
-------------


> ### Java
> 형식: try(자원 받고){try문 실행 후 알아서 자원 반납}
```java
    public void readFile(String path) throws IOExeption{
        try(BufferedReader reader = new BufferedReader(new FileReader(path))){
            Sysout.println(reader.readLine());
        }
    }
```

> ### Kotlin
> Kotlin에는 try with resources 구문이 없는 대신 __use__ 라는 inline 확장함수를 사용함
```kotlin
    fun readFile(path: String) {
        BufferedReader(FileReader(path))
            .use { reader ->
                println(reader.readLine())
            }
    }
```

***
</br>

📖 함수 선언 문법
-------------

> ### Ex. 1) 두 정수를 받아 더 큰 정수를 반환
>
> __'='__ 으로 return을 지우고 return할 값을 __바로 넘길 수 있음__
```kotlin
    fun max(a: Int, b: Int): Int = if (a > b) a else b
```
***
</br>

📖 Default Parameter
-------------

> ### Ex. 1) 주어진 문자열을 N번 출력
>
> fun 함수이름(파라미터:파라미터 타입 = Default)와 같이 Default Parameter 설정 가능
```kotlin
    fun repeat(str: String, num: Int = 3, useNewLine: Boolean = true) {
        for (i in 1..num) {
            if (useNewLine) {
                println(str)
            } else {
                print(str)
            }
        }
    }
```
>다음과 같이 Default Parameter를 설정하여 오버로딩을 구현할 수 있지만
>
> __Java와 동일하게 오버로딩__ 을 구현하는 것도 가능함
***
</br>

📖 Named Argument (parameter)
-------------

> 위와 같은 함수를 이름을 부여해서 호출가능
```kotlin
    repeat("Hello world\n", useNewLine = false)
```
> 파라미터를 명시하여 지정해서 넣을 수 있음
>
> 명시하지 않은 파라미터는 __자동적으로 Default 값__ 을 사용
>
> 이와 같은 특징은 builder를 직접 만들지 않고 __builder의 장점__ 을 갖음

> ### Ex. 1) name과 gender를 받아 출력해주는 함수
```kotlin
    fun printNameAndGender(name: String, gender: String) {
        println(name)
        println(gender)
    }

    printNameAndGender("male", "Luke")     //다음과 같이 헷갈리는 경우를
    printNameAndGender(gender = "male", name = "Luke")  //이처럼 헷갈리지 않게 쓸 수 있음
```
> 단, Kotlin에서 __Java함수를 가져다 쓰는 경우에는 Named Argument를 쓸 수 없음__
***
</br>

📖 같은 타입의 여러 파라미터 받기 (가변인자)
-------------

> ### Ex. 1) 문자열을 N개 받아 출력하는 예제
```kotlin
    fun printAll(vararg strings: String) {  //...이 아닌 앞에 vararg를 써서 받아줌
        for (str in strings) {
            println(str)
        }
    }
    printAll("A", "B", "C")
    val array = arrayOf("A", "B", "C")
    printAll(*array)    //배열을 바로 넣는 대신에 Spread 연산자(*)를 붙여주어야 함
    ```

* 수강한 강의 - 자바 개발자를 위한 코틀린 입문(Java to Kotlin Starter Guide), 최태현 from 인프런
<https://www.inflearn.com/course/java-to-kotlin/dashboard>
