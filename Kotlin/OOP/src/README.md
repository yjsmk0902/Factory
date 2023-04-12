💻 코틀린 OOP
=============
</br>

📖 클래스와 프로퍼티
-------------

> ### 개명이 불가능한 Person 클래스가 있을때, 
>
>   > ### Java의 경우
> ```java
>     public class JavaPerson {
> 
>     //Java의 필드
>     private final String name;
>     private int age;
> 
>     //Java의 생성자
>     public JavaPerson(String name, int age) {
>         this.name = name;
>         this.age = age;
>     }
> 
>     //Java의 Getter
>     public String getName() {
>         return name;
>     }
>     public int getAge() {
>         return age;
>     }
> 
>     //Java의 Setter
>     public void setAge(int age) {
>         this.age = age;
>     }
> }
> ```
>   > ### Kotlin의 경우
> ```kotlin
> class Person constructor(name: String, age: Int) {
>           //constructor는 생략가능
>     val name = name
>     var age = age
> }
> ```
> ## __프로퍼티__ = 필드 + Getter + Setter
> 코틀린에서는 필드(프로퍼티)만 만들면 나머지는 자동으로 만들어줌
>
> + 클래스의 필드와 생성자를 __동시에 선언__ 할 수 있음
> ```kotlin
> class Person (
>     val name: String,
>     var age: Int
> ) 
> 코틀린에서는 필드(프로퍼티)만 만들면 나머지는 자동으로 만들어줌
>
> + 클래스의 필드와 생성자를 __동시에 선언__ 할 수 있음
> ```kotlin
> class Person (
>     val name: String,
>     var age: Int
> ) 
> ```
>
> + __'.필드'__ 를 통해 Getter/Setter를 바로 호출한다
> ```kotlin
>   val person = Person("Luke", 10)
>   println(person.name)    //Getter
>   println(person.age)     //Getter
>   person.age = 10         //Setter
> ```

> 초기값을 지정해주지 않으면 에러 발생

***
</br>

📖 생성자와 init
-------------

> ### 클래스가 생성되는 시점에 나이를 검증해보기
>
>   > ### Java의 경우
> ```java
>     public class JavaPerson {
>       ...
>     //Java의 생성자
>     public JavaPerson(String name, int age) {
>         //Java의 검증로직
>         if (age <= 0) {
>             throw new IllegalArgumentException(String.format("나이(%s)는 1이상 이어야 합니다.", age));
>         }
>         this.name = name;
>         this.age = age;
>     }
>       ...
> }
> ```
>   > ### Kotlin의 경우
> ```kotlin
> class Person(
>     val name: String,
>     var age: Int
> ) {
>     //Kotlin의 검증로직
>     init {
>         if (age <= 0) {
>             throw IllegalArgumentException("나이${age}는 1이상 이어야 합니다.")
>         }
>     }
> }
> ```
>
> ### init
>
> * 값을 적절히 만들어주거나, 검증하는 로직을 넣는 용도로 사용
> * 생성자가 호출되는 시점에 생성됨

> ### 부생성자 만들어보기
>
>   > ### Java의 경우
> ```java
>    public class JavaPerson {
>       ...
>       //Java의 나이가 1인 생성자 (부생성자)
>       public JavaPerson(String name) {
>           this(name, 1);
>       }
>       ...
>    }
> ```
>   > ### Kotlin의 경우
> ```kotlin
>    class Person(...){
>       ...
>       //Kotlin의 나이가 1인 생성자 (부생성자)
>       constructor(name: String) : this(name, 1){
>           println("부생성자1")    //다음과 같이 생성자 블록에 바디를 작성할 수 있음
>       }
>       //Kotlin의 이름이 홍길동인 생성자
>       constructor() : this("홍길동"){
>           println("부생성자2")
>       }
>       ...
>    }
> ```
> * __'constructor(파라미터)'__ 로 생성자를 추가
> * __'this(파라미터)'__ 로 위에 있는 생성자를 호출
> * constructor(생성자) 블록에 바디를 작성할 수 있음
> ### __주생성자__ 는 반드시 있어야 하며, 파라미터는 없을 수 있음
> ### __부생성자__ 는 있을 수도 있고, 없을 수도 있음
</br>

> 다음과 같은 코드에서 __두 번째 부생성자를 실행__ 할 경우,
> ```kotlin
>       class Person(
>           val name: String,
>           var age: Int,
>       ) {
>           init {
>               if (age <= 0) {
>                   throw IllegalArgumentException("나이${age}는 1이상 이어야 합니다.")
>               }
>               println("초기화")
>           }
>           constructor(name: String) : this(name, 1){
>               println("부생성자1")    //다음과 같이 생성자 블록에 바디를 작성할 수 있음
>           }
>           constructor() : this("홍길동"){
>               println("부생성자2")
>           }
>       }
> ```
> ### 출력값: __초기화 -> 부생성자1 -> 부생성자2__
> 역순으로 호출됨

</br>

> ### 근데 코틀린에서는 부생성자를 잘 안씀 (__Default Parameter__ 로 대체)
> ```kotlin
>       class Person(
>           val name: String = "홍길동",    //Default
>           var age: Int = 1,   //Default
>       ) {
>           init {
>               if (age <= 0) {
>                   throw IllegalArgumentException("나이${age}는 1이상 이어야 합니다.")
>               }
>               println("초기화")
>           }
>       }
> ```
> ### Converting처럼 객체의 변경이 일어날때는 써야하지만 거의 없음


***
</br>

📖 커스텀 Getter / Setter & Backing field
-------------

> ### 성인인지 확인하는 기능
>
>   > ### Java의 경우
> ```java
>    public class JavaPerson {
>       ...
>       //Java의 성인인지 확인하는 기능
>       public boolean isAdult() {
>           return this.age >= 20;
>       }
>       ...
>    }
> ```
>   > ### Kotlin의 경우
> ```kotlin
>    class Person(...){
>       ...
>        //Kotlin의 성인인지 확인하는 기능
>        fun isAdultV1(): Boolean = this.age >= 20
>
>        //밑에 두 개가 Custom Getter
>        val isAdultV2: Boolean
>            get() = this.age >= 20
>
>        val isAdultV3: Boolean
>            get() {
>                return this.age >= 20
>            }
>       ...
>    }
> ```
> Expression을 통해 Getter를 Custom

> ### 자기 자신도 변형 가능
> ```kotlin
>    class Person(
>       name: String,
>       var age:Int
>    ){
>       ...
>       val name:String = name
>           get() = field.uppercase()
>       ...
>    }
> ```
> field가 아닌 name을 쓰면 호출이 무한루프를 돌게 됨
>
> 따라서 자기 자신을 호출하는 경우에는 __'field'__ 를 사용
>
> 이것을 __Backing field__ 라고 부름

> ### name을 set할때 무조건 대문자로 바꾸기
> ```kotlin
>   var name: String = name
>       set(value){
>           field = value.uppercase()
>       }
> ```
> 사실 __Setter 자체를 지양__ 하기 때문에 Custom Setter도 잘 안씀

***
</br>

* 수강한 강의 - 자바 개발자를 위한 코틀린 입문(Java to Kotlin Starter Guide), 최태현 from 인프런
<https://www.inflearn.com/course/java-to-kotlin/dashboard>
