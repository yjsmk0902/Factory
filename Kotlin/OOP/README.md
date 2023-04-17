💻 코틀린 OOP
=============



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
> + <span style="color:greenyellow">__'.필드'__</span> 를 통해 Getter/Setter를 바로 호출한다
> ```kotlin
>   val person = Person("Luke", 10)
>   println(person.name)    //Getter
>   println(person.age)     //Getter
>   person.age = 10         //Setter
> ```

> 초기값을 지정해주지 않으면 에러 발생

***



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
> * <span style="color:greenyellow">__'constructor(파라미터)'__ </span>로 생성자를 추가
> * <span style="color:greenyellow">__'this(파라미터)'__ </span>로 위에 있는 생성자를 호출
> * constructor(생성자) 블록에 바디를 작성할 수 있음
> ### __주생성자__ 는 반드시 있어야 하며, 파라미터는 없을 수 있음
> ### __부생성자__ 는 있을 수도 있고, 없을 수도 있음



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
> 따라서 <span style="color:yellow">자기 자신을 호출하는 경우에는 <span style="color:yellowgreen">__'field'__ </span></span>를 사용
>
> 이것을 <span style="color:yellow">__Backing field__ </span>라고 부름

> ### name을 set할때 무조건 대문자로 바꾸기
> ```kotlin
>   var name: String = name
>       set(value){
>           field = value.uppercase()
>       }
> ```
> 사실 <span style="color:tomato">__Setter 자체를 지양__ 하기 때문에 Custom Setter도 잘 안씀</span>

***
</br>

📖 추상 클래스
-------------

> ### 다음과 같이 추상 클래스를 구성할거임
>
> ![1](1.png)

> ### Animal 추상 클래스
>   * Java의 추상 클래스
> ```java
> public abstract class JavaAnimal {
> 
>     protected final String species;
>     protected final int legCount;
> 
>     public JavaAnimal(String species, int legCount) {
>         this.species = species;
>         this.legCount = legCount;
>     }
> 
>     //추상 메소드
>     abstract public void move();
> 
>     //Getter
>     public String getSpecies() {
>         return species;
>     }
>     public int getLegCount() {
>         return legCount;
>     }
> }
> ```
>   * Kotlin의 추상 클래스
> ```kotlin
> abstract class Animal(
>     //Getter/Setter 자동 생성
>     //상속받은 자식클래스에서 Getter를 재정의 하기위해 open 붙임
>     protected val species: String,
>     protected open val legCount: Int, //오버라이드할 때 open
> ) {
>     //추상 메소드
>     abstract fun move()
> }
> ```

> ### Cat 상속 클래스
>   * Java의 상속 클래스
> ```java
> public class JavaCat extends JavaAnimal {
> 
>     //생성자 상속
>     public JavaCat(String species) {
>         super(species, 4);
>     }
> 
>     //상속받은 메소드 재정의
>     @Override
>     public void move() {
>         System.out.println("JavaCat Move");
>     }
> }
> ```
>   * Kotlin의 상속 클래스
> ```kotlin
> class Cat(
>     //생성자 상속
>     species: String
> ) : Animal(species, 4) {
> 
>     //상속받은 메소드 재정의
>     override fun move() {   //어노테이션 없이 override를 붙여 씀
>         println("Cat Move")
>     }
> }
> ```
> - extends 대신에 <span style="color:yellowgreen">__':'__ </span>을 써서 상속받음
> - 상위 클래스의 생성자를 바로 호출해야함

> ### Penguin 상속 클래스
>   * Java의 상속 클래스
> ```java
> public final class JavaPenguin extends JavaAnimal {
>
>     //상속받은 클래스 속 새로운 필드
>     private final int wingCount;
> 
>     //생성자 상속
>     public JavaPenguin(String species) {
>         super(species, 2);
>         this.wingCount = 2;
>     }
> 
>     //상속받은 추상 메소드 재정의
>     @Override
>     public void move() {
>         System.out.println("Penguin Move");
>     }
> 
>     //부모 메소드 상속받아 재정의
>     @Override
>     public int getLegCount() {
>         return super.getLegCount() + this.wingCount;
>     }
> }
> ```
>   * Kotlin의 상속 클래스
> ```kotlin
> class Penguin(
>     //생성자 상속
>     species: String,
>     //상속받은 클래스 속 새로운 필드
>     private val wingCount: Int = 2
> ) : Animal(species, 2){
> 
>     //상속받은 추상 메소드 재정의
>     override fun move() {
>         println("Penguin Move")
>     }
> 
>     //부모 프로퍼티 상속받아 Getter 재정의
>     override val legCount:Int
>         get() = super.legCount + this.wingCount> 
> }
> ```
> ### <span style="color:tomato">__중요!!__ 프로퍼티를 오버라이드 할때는 부모 클래스에서 __open__ 으로 열어줘야함 </span>

> * ### 상위 클래스에 접근할 때는 Java, Kotlin 모두 super를 사용함
> * ### Java, Kotlin 모두 추상 클래스를 인스터스화 할수 없음

***
</br>

📖 인터페이스
-------------

> ### 다음과 같이 인터페이스를 구성할거임![2](2.png)

> ### Flyable, Swimmable 인터페이스
>   * Java의 인터페이스
> ```java
> public interface JavaFlyable {
> 
>     default void act() {
>         //default 바디
>         System.out.println("Flying");
>     }
> }
> ```
> ```java
> public interface JavaSwimable {
> 
>     default void act() {
>         //default 바디
>         System.out.println("Swimming");
>     }
> }
> ```

>   * Kotlin의 인터페이스
> ```kotlin
> interface Flyable {
>     
>     //default 없이 메소드 구현 가능
>     fun act() = println("Flying")
> }
> ```
> ```kotlin
> interface Swimable {
> 
>     //Kotlin은 인터페이스에 프로퍼티도 구현가능
>     val swimAbility: Int
>         get() = 3 // => default값
> 
>     //default 없이 메소드 구현 가능
>     fun act() = println("Swimming ")
> }
> ```
> ### Kotlin의 인터페이스는 Default 키워드 없이 메소드 구현 가능

> ### Penguin 구현체
>   * Java의 구현체
> ```java
> public final class JavaPenguin extends JavaAnimal implements JavaFlyable, JavaSwimable {
>     ...
>     //인터페이스 구현
>     @Override
>     public void act() {
>         JavaSwimable.super.act();
>         JavaFlyable.super.act();
>     }
> }
> ```
>   * Kotlin의 구현체
> ```kotlin
> class Penguin(...) : Animal(species, 2),
> Flyable, Swimable{
>     ...
>     //인터페이스 구현
>     override fun act() {
>         super<Swimable>.act()
>         super<Flyable>.act()
>     }
>     //프로퍼티 재정의
>     override val swimAbility: Int
>         get() = 5
> }
> ```
> * ### 상속과 같이 상속받는 클래스를 이어서 적어줌으로써 상속 가능
> * ### 중복되는 인터페이스를 특정할때 <span style="color:yellowgreen">__'super<타입>.함수'__ </span>사용
> * ### Java, Kotlin 모두 인터페이스를 인스터스화 할 수 없음
> * ### Kotlin에서는 __Backing Field__ 가 없는 프로퍼티를 인터페이스에 만들 수 있음

***


📖 클래스를 상속할 때 주의할 점
-------------

<img src="3.png" alt="3" style="zoom:33%;" />

```kotlin
open class Base(
    open val number: Int = 100
){
    init {
        println("Base Class")
        println(number)
    }
}
```
```kotlin
class Derived(
    override val number: Int
) : Base(number) {
    init {
        println("Derived Class")
    }
}
```

> ### 클래스가 다음과 같이 구성될 때,
```kotlin
Derived(300)
```
> ### 다음과 같이 인스턴스화 하면?
> ### __Base Class -> 0 -> Derived Class__ 같은 순서로 출력됨

> Derived에 있는 number에 값을 집어 넣어 줄 때
>
> 상위 클래스에서 number를 호출하게 되면 하위 클래스에 있는 number를 가져오게 되고
>
> 근데 아직 상위 클래스의 constructor가 먼저 실행된 단계이기 때문에 
>
> 하위 클래스 number의 초기화가 이루어지지 않은 상태임
>
> 따라서 0이 출력됨

> ### <span style="color:tomato">__상위 클래스의 constructor와 init 블럭에서는 하위 클래스의 필드에 접근하면 안됨__</span>
> 고로 상위클래스를 설계할 때 <span style="color:tomato">생성자 또는 초기화 블록에 사용되는 프로퍼티에는 __'open'을 피해야 함__</span>


***


📖 상속 관련 지시어 정리
-------------
> * ### final : override를 할 수 없게 함 (Default)
> * ### open : override를 열어 줌
> * ### abstract : 반드시 override 해야함
> * ### override : 상위 타입을 오버라이드 하고 있음

***


## 📖 자바와 코틀린의 가시성 제어

* ### Java와 Kotlin의 접근 제어 비교

|                            모든 곳에서 접근 가능 |        public | public        | 모든 곳에서 접근 가능                              |
| -----------------------------------------------: | ------------: | :------------ | :------------------------------------------------- |
| **같은 패키지 또는 하위 클래스**에서만 접근 가능 | **protected** | **protected** | **선언된 클래스 또는 하위 클래스**에서만 접근 가능 |
|                  **같은 패키지에서만** 접근 가능 |   **default** | **internal**  | **같은 모듈**에서만 적용 가능                      |
|             선언된 **클래스 내에서만** 접근 가능 |   **private** | **private**   | 선언된 **클래스 내에서만** 접근 가능               |

> * #### **Java**의 경우
>
> **Java**의 기본 접근 지시어는 **default**

> * #### **Kotlin**의 경우
>
> **Kotlin**의 기본 접근 지시어는 **public**
>
> **Kotlin**에서는 **패키지를 namespace를 관리하기 위한 용도**로만 사용함 (가시성 제어에는 사용되지 않음)
>
> <span style="color:yellow">**모듈**</span>이란? **한 번에 컴파일되는 Kotlin 코드**를 의미함 ex) IDEA Module / Maven Project / Gradle Source Set ...etc

***



## 📖 코틀린 파일의 접근 제어

**Kotlin**은 .kt 파일에 변수, 함수, 클래스를 바로 만들 수 있다.

> ```kotlin
> //해당 코드는 main.kt의 main함수 밖에 쓰여있음 
> val a = 3
> 
> fun add(a: Int, b: Int): Int {
>  return a + b
> }
> ```

+ ### Kotlin의 접근제어+

|  접근제어자   |                             의미                             |
| :-----------: | :----------------------------------------------------------: |
|  **public**   |             **기본값** / 어디서든 접근할 수 있음             |
| **protected** | 파일(최상단)에는 **사용 불가능** (위처럼 함수 밖, 즉 파일 자체) |
| **internal**  |                **같은 모듈**에서만 적용 가능                 |
|  **private**  |             선언된 **클래스 내에서만** 접근 가능             |

***



## 📖 다양한 구성요소의 접근 제어

* #### **클래스 / 생성자 / 프로퍼티**의 접근 제어

**클래스**의 경우엔 Java -> Kotlin의 변화 방식과 동일함

**생성자**의 경우도 마찬가지로 같지만, 생성자에 접근 지시어를 붙일 경우 <span style="color:yellow">**constructor을 써주어야함**</span>

> ```kotlin
> //이렇게 쓰면 안되고
> class Member private(
>  val username:String,
>  val age: Int
> )
> //이렇게 써야함
> class Member private constructor(
>  val username:String,
>  val age:Int
> )
> ```

**유틸성 코드**를 만들 때 **Java**에서는 **추상 클래스 + private 생성자**를 사용해 인스턴스화를 막았음

> ```java
> public abstract class StringUtilsJava {
>  private StringUtilsJava() {}
> 
>  public boolean isDirectoryPath(String path) {
>      return path.endsWith("/");
>  }
> }
> ```

**Kotlin**도 비슷하게 가능하지만, <span style="color:yellow">파일 최상단에 유틸 함수를 작성</span>하면 같은 기능을 할 수 있음

> ```kotlin
> //해당 코드는 StringUtilsJava.kt의 파일 최상단에 있음
> fun isDirectoryPath(path: String): Boolean {
>  return path.endsWith("/")
> }
> ```

**프로퍼티**도 마찬가지로 **동일**하지만 **가시성을 설정해주는 방법**이 있음

> ```kotlin
> class Car (
>     internal val name: String,  //name에 대한 getter를 internal로 만듦
>     private var owner:String,   //owner에 대한 getter/setter를 private으로 만듦
>     _price:Int
> ){
>     var price = _price
>         private set     //price에 대한 setter를 private으로 만듦
> }
> ```
>
> **프로퍼티를 제어**할 때는,
>
> * **Getter/Setter를 한번에 설정**하는 방법
> * **Custom Setter를 설정**하는 방법

***



## 📖 Java와 Kotlin을 함께 사용할 경우 주의할 점

#### **Internal**은 바이트 코드 상 **public**이 됨 

#### 따라서 <span style="color:yellow">**Java 코드에서는 Kotlin 모듈의 internal 코드를 가져올 수 있음**</span>



예를 들어 **상위 모듈은 Java, 하위 모듈은 Kotlin**으로 이루어진 코드가 있다면

<span style="color:yellow">**하위 모듈의 internal로 감싸진 필드나 함수를 상위 모듈의 Java에서 가져올 수 있음**</span>



같은 맥락으로, Java와 Kotlin의 protected는 다르기 때문에 <span style="color:yellow">**Java는 같은 패키지의 Kotlin protected 멤버에는 접근이 가능**</span>함

***



## 📖 Static 함수와 변수

* ### Java의 Static

```java
public class JavaPersonStatic {

  	//java의 static 변수
    private static final int MIN_AGE = 1;

    public static JavaPersonStatic newBaby(String name) {
        return new JavaPersonStatic(name, MIN_AGE)
    }

    private String name;
    private int age;
		
  	//Java의 static 함수
    private JavaPersonStatic(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
```



* ### Kotlin의 Static

```kotlin
class PersonStatic private constructor(
    var name:String,
    var age:Int
) {
  	//Kotlin의 동행 오브젝트 (static의 역할을 함)
    companion object{
        private const val MIN_AGE=1		//const를 쓸 경우 컴파일 시에 변수 할당
        fun newBaby(name: String): PersonStatic {
            return PersonStatic(name, MIN_AGE)
        }
    }
}
```

#### Kotlin에서는 <span style="color:yellowgreen">**'companion object'**</span> 블럭을 사용해 해당 블럭 안에 있는 변수와 함수를 **static으로 사용**함

> ######  **Static** : 클래스가 인스턴스화 될 때, 새로운 값이 복제되는게 아니라 **정적**으로 인스턴스끼리의 값을 공유

> ###### **Companion object** : 클래스와 **동행**하는 유일한 오브젝트
>
> * ###### Const : 진짜 상수로 쓰는 변수에 붙이기 위한 용도로 기본 타입과 String에만 붙일 수 있음
>
> * ###### 동반객체라는 이름으로, 하나의 객체로 간주되기 때문에 이름을 붙일 수도 있고 interface를 구현할 수도 있음
>
>   > ```kotlin
>   > class PersonStatic private constructor(
>   >     var name:String,
>   >     var age:Int
>   > ) {
>   >     //Factory라는 이름을 부여하고, Log 인터페이스를 상속받음
>   >     companion object Factory : Log{
>   >         private const val MIN_AGE=1
>   >         fun newBaby(name: String): PersonStatic {
>   >             return PersonStatic(name, MIN_AGE)
>   >         }
>   >         //인터페이스 구현
>   >         override fun log() {
>   >             println("This object is Factory companion object.")
>   >         }
>   >     }
>   > }
>   > interface Log{
>   >     fun log()
>   > }
>   > ```
>
> * ###### Companion Object에 **유틸성 함수**를 넣어도 되지만, <span style="color:yellow">**최상단 파일을 활용**하는 것을 추천</span>함
>
> * ###### Java에서 Kotlin에 있는 static 필드나 함수를 사용하고 싶을 때는 
>
>   * 이름이 없을 때는 <span style="color:yellowgreen">**'클래스'.Companion.'필드나 함수'**</span> (Kotlin에서 해당 필드에 @JvmStatic 어노테이션을 사용하면 'Companion.' 생략가능)
>   * 이름이 있을 때는 <span style="color:yellowgreen">**'클래스'.'이름'**</span>
>
>   ```kotlin
>   ...
>   companion object Factory : Log{
>       private const val MIN_AGE=1
>           
>       @JvmStatic
>       fun newBaby(name: String): PersonStatic {
>           return PersonStatic(name, MIN_AGE)
>       }
>       //인터페이스 구현
>       override fun log() {
>           println("This object is companion object.")
>       }
>   }
>   ...
>   ```
>
>   ```java
>   public class JavaMain {
>       public static void main(String[] args) {
>           //PersonStatic.Companion.newBaby("abc");     //이름이 없다면
>           PersonStatic.newBaby("abc");        //@JvmStatic을 썼다면
>           PersonStatic.Factory.newBaby("abc");    //이름을 썼다면
>       }
>   }
>   ```

***

## 📖 Singleton

### 싱글톤이란? 

* ###### 단 하나의 인스턴스만을 갖는 클래스

##### **Java**에서는 static 영역에 인스턴스를 만들어서 <span style="color:yellowgreen">'getInstance()'</span>함수로 하나만 가져오게 하는 방법을 사용함

```java
public class JavaSingleton {

    private static final JavaSingleton INSTANCE = new JavaSingleton();

    public JavaSingleton() {}

    public static JavaSingleton getInstance() {
        return INSTANCE;
    }
}
```

###### 해당 코드에서 동시성처리를 좀더 해주거나 enum 클래스를 활용하는 방법도 있음

##### 하지만 Kotlin에서는

```kotlin
object KotlinSingleton {}
```

## 끝

###### 인스턴스화가 따로 필요없음

***

## 📖 익명 클래스

### 익명 클래스란?

* ###### 특정 인터페이스나 클래스를 상속받은 구현체를 일회성으로 사용할 때 쓰는 클래스

##### Java에서는 다음처럼 사용함

```java
public class JavaMain {
    public static void main(String[] args) {
        moveSomething(new Movable() {
            @Override
            public void move() {
                System.out.println("Move");
            }

            @Override
            public void fly() {
                System.out.println("Fly"); 
            }
        });
    }

    private static void moveSomething(Movable movable) {
        movable.move();
        movable.fly();
    }
}
```

##### Kotlin의 경우,

```kotlin
fun main() {
    moveSomething(object : Movable{		//object 키워드를 사용함
        override fun move() {
            println("Move")
        }

        override fun fly() {
            println("Fly")
        }
    })
}

private fun moveSomething(movable: Movable) {
    movable.move()
    movable.fly()
}
```

###### Java처럼 'new 클래스(){오버라이드}' 형식이 아닌<span style="color:yellowgreen"> **'object : 클래스{오버라이드}'**</span>의 형식을 사용



## 📖 중첩 클래스의 종류

* ### Java의 중첩 클래스

  > * ##### Static을 사용하는 중첩 클래스
  >
  >   밖의 클래스를 직접 참조할 수 없음

  > * ##### Static을 사용하지 않는 중첩 클래스
  >
  >   * ###### 내부 클래스 (Inner Class)
  >
  >     밖의 클래스를 직접 참조 가능
  >
  >   * ###### 지역 클래스 (Local Class)
  >
  >     메소드 내부에 클래스를 정의 (이렇게 쓸 일이 있나..? 나도 못본듯)
  >
  >   * ###### 익명 클래스 (Anonymous Class)
  >
  >     일회성 클래스

  ```java
  public class JavaHouse {
  
      private String address;
      private LivingRoom livingRoom;
  
      public JavaHouse(String address, LivingRoom livingRoom) {
          this.address = address;
          this.livingRoom = new LivingRoom(10);
      }
  
      public LivingRoom getLivingRoom() {
          return livingRoom;
      }
  
      //내부 클래스 (static 권장)
      public static class LivingRoom{
          private double area;
  
          public LivingRoom(double area) {
              this.area = area;
          }
          ////바깥 클래스와 연결되어 있음
          //public String getAddress() {
          //    return JavaHouse.this.address;
          //}
      }
  }
  ```

> 하지만 위 코드와 같이 static 필드를 쓰지 않는 내부 클래스를 사용할 경우 몇 가지 문제점이 발생함 (Effective Java 3rd Edition 중...)
>
> + 내부 클래스가 외부 클래스를 참조함으로 인해서 참조해지를 못할 경우 메모리 누수가 생길 수 있고 이를 디버깅하기 힘들다
>
> + 내부 클래스의 직렬화 형태가 명확하게 정의되어있지 않아 직렬화에 있어서 제한이 있음
>
> ### 그래서 클래스 안에 클래스를 만들 때에는 <span style="color:tomato">static 클래스를 사용해야 함</span>을 권장



***



## 📖 Kotlin의 중첩 클래스와 내부 클래스

### Kotlin에서는 위와 같은 권장 사항을 충실하게 따르고 있음

```kotlin
class KotlinHouse (
    private val address:String,
    private val livingRoom: LivingRoom = LivingRoom(10.0)
){
    //코틀린의 중첩 클래스
    class LivingRoom (
        private val area:Double
    ){

    }
}
```

#### 기본적으로 바깥 클래스에 대한 연결이 없는 중첩 클래스가 알아서 만들어짐

```kotlin
//코틀린의 중첩 클래스
inner class LivingRoom (
    private val area:Double
){
    val address:String
        get() = this@KotlinHouse.address
}
```

#### inner를 붙일 시에 권장되지 않는 클래스안의 클래스의 경우,

+ ##### 다음처럼 <span style="color:yellowgreen">'inner' </span>키워드를 명시적으로 붙여주면 됨

+ ##### 바깥 클래스와 연결시킬 경우는 <span style="color:yellowgreen">'this@바깥클래스.프로퍼티'</span>로 연결해서 사용함

***

## 📖 Data Class

+ ### Java에서 계층간의 데이터를 전달하기 위한 DTO(Data Transfer Object)

```java
import java.util.Objects;

public class JavaPersonDto {
    private final String name;
    private final int age;

    public JavaPersonDto(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JavaPersonDto that = (JavaPersonDto) o;
        return getAge() == that.getAge() && Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getAge());
    }

    @Override
    public String toString() {
        return "JavaPersonDto{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
```

> #### 다음과 같은 메소드를 가짐
>
> + ##### 데이터(필드)
>
> + ##### 생성자와 Getter
>
> + ##### Equals
>
> + ##### hashCode
>
> + ##### toString

#### 다음과 같이 <span style="color:yellow">IDE</span>를 활용할 수도 있고, <span style="color:yellow">Lombok</span>을 활용할 수도 있지만

#### <span style="color:tomato">클래스가 장황</span>해지거나, <span style="color:tomato">클래스 생성 이후 추가적인 처리(어노테이션)</span>를 해줘야 하는 ㄹ단점이 있음

+ ### Kotlin에서 계층간의 데이터를 전달하기 위한 DTO(Data Transfer Object)

```kotlin
data class PersonDto(
    private val name: String,
    private val age: Int
) {
}
```

#### 놀랍게도 이렇게<span style="color:yellow"> class 앞에 data</span> 하나만 써주면 Java에서의 Dto와 같은 역할을 하는 클래스가 됨 (대박신기)

###### +name argument까지 활용하면 <span style="color:yellow">builder pattern</span>을 쓰는 것 같은 효과도 있음

###### +Java도 이와 같은 record class를 JDK16부터 도입하기는 함

***

## 📖 Enum Class

* ### Java에서 Enum Class

```java
public enum JavaCountry {
    Korea("KO"),
    AMERICA("US");

    private final String code;

    JavaCountry(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
```

Enum Class는 추가적인 클래스를 상속받을 수 없지만, 인터페이스는 구현할 수 있으며, 각 코드가 싱글톤임

* ### Kotlin에서 Enum Class

```kotlin
enum class Country (
    private val code:String
){
    KOREA("KO"),
    AMERICA("US")
}
```

뭐 예상했다시피 Kotlin에서는 생성자와 Getter의 압축도가 높으니 코드는 다음과 같이 작성됨

#### 하지만 Kotlin에서는 <span style="color:yellowgreen">when</span>이라는 키워드가 있음 (예전 공부에서 Enum/Sealed 클래스와 아주 결합이 좋다고 했었음)

##### 예를 들어 Java에서 다음과 같은 코드를 작성할 때,

```java
private static void handleCountry(JavaCountry country) {
    if (country == JavaCountry.KOREA) {
        //Logic
    }
    if (country == JavaCountry.AMERICA) {
        //Logic
    }
}
```

If-else문이 많아짐 = 코드가 길어짐

##### 하지만 Kotlin에서 when을 사용하게 된다면,

```kotlin
fun handleCountry(country: Country) {
    when (country) {
        Country.KOREA -> TODO()
        Country.AMERICA -> TODO()
    }
}
```

다음과 같이 깔끔한 코드로 정리할 수가 있음

##### 만약 Enum Class에 다른 요소가 추가된다 하더라도 해당 함수의 when이 warning을 통해 알려주기도 함

하지만 Java는 그런게 없음

***

## 📖 Sealed Class, Sealed Interface

### Sealed Class?

+ ##### Sealed = 봉인을 한 / 포장된 / 해결된 이라는 의미를 가짐

+ ##### 상속이 가능한 추상클래스를 만들고 싶은데 외부에서는 상속받지 않았으면 좋겠을 때 사용함

+ ##### <span style="color:yellow">컴파일 타임 때 하위 클래스의 타입을 모두 기억함</span> (런타임 때 클래스 타입이 추가될 수 없음)

+ ##### 하위 클래스는 같은 패키지에 있어야함

```kotlin
sealed class HyundaiCar(
    val name: String,
    val price: Long
)

class Avante : HyundaiCar("아반떼", 1_000L)

class Sonata : HyundaiCar("소나타", 2_000L) 

class Grandeur : HyundaiCar("그랜저", 3_000L) 
```

```kotlin
private fun handleCar(car: HyundaiCar) {
    when (car) {
        is Avante -> TODO()
        is Sonata -> TODO()
        is Grandeur -> TODO()
    }
}
```

Enum 클래스에서 공부했던 것과 마찬가지로 코드가 간결하고 읽기가 쉬움

##### 보통 <span style="color:yellow">추상화가 필요한 Entity나 DTO</span>에 Sealed Class를 활용함

+Java의 JDK17에서도 Sealed Class가 추가되긴 함

***

## 📖 배열

사실 배열은 잘 안씀. (Effective Java에서도 '배열보다는 리스트를 사용하라'라고 함)

그치만 일단 배워보자

+ ### Java에서의 배열

```java
int[] array = {100, 200};
for (int i = 0; i < array.length; i++) {
    System.out.printf("%s %s", i, array[i]);
}
```

+ ### Kotlin에서의 배열

```kotlin
val array = arrayOf(100, 200)
//array 안을 돌리기
for (i in array.indices) {
    println("${i} ${array[i]}")
}
//index와 같이 뽑기
for ((idx, value) in array.withIndex()) {
    println("${idx} ${value}")
}
```

> + ##### <span style="color:yellowgreen">배열.indices</span> : 0부터 마지막 index까지의 범위
>
> + ##### <span style="color:yellowgreen">배열.withIndex()</span> : indices와 같지만 인덱스와 값을 한 번에 가져올 수 있음

***

## 📖 Kotlin에서의 Collection - List / Set / Map

+ ### Kotlin의 Collection 계층구조

<img src="4.png" alt="4" style="zoom: 67%;" />

> + #### 불변 컬렉션 : Collection에 element를 추가, 삭제할 수 없음
>
>   + ###### Collection을 만들자 마자 Collections.unmodifiableList()  등을 붙여줌
>
> + #### <span style="color:yellow">가변(Mutable)</span> 컬렉션 : Collection에 element를 추가, 삭제할 수 있음

### <span style="color:tomato">Kotlin은 무조건 불변/가변을 지정해 주어야함!!</span>

### Collection - List

+ ##### Java의 List

```java
final List<Integer> numbers = Arrays.asList(100, 200);
```

+ ###### Kotlin의 List

```kotlin
val numbers = listOf(100, 200)
val mutableNumbers = mutableListOf(100, 200)
val emptyList = emptyList<Int>()	//빈 리스트 만들기 - 타입을 명시해줌
println(numbers[0])		//element 가져오기
mutableNumbers.add(300) 	//가변 리스트에 element 추가하기
```

> + <span style="color:yellowgreen">listOf()</span>를 통해 불변리스트를 만들어 줄 수 있음
> + 빈 List를 만들 경우 타입을 명시해 주어야 함
> + 일반 배열처럼 ''배열.get()' 외에도 <span style="color:yellowgreen">'리스트[idx]'</span>로 element를 가져올 수 있음

> + <span style="color:yellowgreen">mutableListOf()</span>를 통해 가변리스트를 만들어 줄 수 있음
> + ArrayList가 기본 구현체임
> + <span style="color:yellowgreen">'리스트.add(요소)'</span>로 element를 추가해줄 수 있음

##### 간단한 TIP 우선 불변리스트를 만들고, 꼭 필요한 경우 가변 리스트로 바꾸자!!

### Collection - Set

+ List와 다르게 순서가 없고, 같은 element 하나만 존재할 수  있음 (List와 대부분 비슷)

```kotlin
val numbers = setOf(100, 200)
val mutableSet = mutableSetOf(100,200)
val emptyList = emptySet<Int>()		//빈 리스트 만들기 - 타입을 명시해줌
mutableSet.add(300)		//가변집합에 element 추가하기
```

> + <span style="color:yellowgreen">setOf()</span>를 통해 불변집합을 만들어 줄 수 있음
> + 빈 Set을 만들 경우 타입을 명시해 주어야 함

> + <span style="color:yellowgreen">mutableListOf()</span>를 통해 가변리스트를 만들어 줄 수 있음
> + LinkedHashSet이 기본 구현체임
> + <span style="color:yellowgreen">'집합.add(요소)'</span>로 element를 추가해줄 수 있음





***



## 📖 컬렉션의 null 가능성 / Java와 함께 사용하기







***












* 수강한 강의 - 자바 개발자를 위한 코틀린 입문(Java to Kotlin Starter Guide), 최태현 from 인프런
<https://www.inflearn.com/course/java-to-kotlin/dashboard>
