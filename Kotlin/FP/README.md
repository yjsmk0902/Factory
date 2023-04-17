💻 코틀린 FP
=============



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

<img src="1.png" alt="4" style="zoom: 67%;" />

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

+ ##### Kotlin의 List

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
val emptySet = emptySet<Int>()		//빈 리스트 만들기 - 타입을 명시해줌
mutableSet.add(300)		//가변집합에 element 추가하기
```

> + <span style="color:yellowgreen">setOf()</span>를 통해 불변집합을 만들어 줄 수 있음
> + 빈 Set을 만들 경우 타입을 명시해 주어야 함

> + <span style="color:yellowgreen">mutableListOf()</span>를 통해 가변리스트를 만들어 줄 수 있음
> + LinkedHashSet이 기본 구현체임
> + <span style="color:yellowgreen">'집합.add(요소)'</span>로 element를 추가해줄 수 있음

### Collection - Map

+ ##### Java의 Map

```java
//JDK 8까지
Map<Integer, String> map = new HashMap<>();
map.put(1, "MONDAY");
map.put(2, "TUESDAY");

//JDK 9부터
Map.of(1, "MONDAY", 2, "TUESDAY"); //(Key1, Value1, Key2, Value2)
```

+ ##### Kotlin의 Map

```kotlin
//Java처럼 쓰기
val oldMap = mutableMapOf<Int, String>()
oldMap.put(1,"MONDAY")

//Kotlin 답게 쓰기
val map = mutableMapOf<Int, String>()		//타입추론이 되지 않아 타입을 명시해줌
map[1] = "MONDAY"
map[2] = "TUESDAY"

mapOf(1 to "MONDAY", 2 to "TUESDAY")    //중위 호출로 넣기
```

> + Kotlin도 동일하게 MutableMap을 만들어 넣을 수도 있고, 정적 팩토리 메소드를 활용할 수도 있음
> + <span style="color:yellowgreen">'map[key] = value'</span>를 써서 element를 넣을 수 있음

```kotlin
//key를 통해 접근
for (key in map.keys) {
    println(key)
    println(oldMap[key])
}

//객체를 가져와서 접근
for ((key, value) in map.entries) {
    println(key)
    println(value) 
}
```

다음과 같이 활용 가능함

***

## 📖 컬렉션의 null 가능성 / Java와 함께 사용하기

+ ### List<Int?>

  + List에 null이 들어갈 수는 있지만, List는 절대 null이 아님

+ #### List<Int>?

  + List에 null이 들어갈 수 없지만, List는 null일 수 있음

+ ### List<Int?>?

  + List에 null이 들어갈 수도 있고, List가 null일 수도 있음

### '?' 위치에 따라 null 가능성 의미가 달라지므로 조심해야됨

Java는 읽기 전용 컬렉션과 변경 가능 컬렉션을 구분하지 않음

Java는 nullable 타입과 non-nullable 타입을 구분하지 않음

#### 	> 다음과 같은 문제는 Java <-> Kotlin 간의 오작동을 일으킬 수 있음

​	따라서 Kotlin 쪽의 컬렉션이 Java에서 호출되면 컬렉션 내용이 변할 수 있음을 감안해야함

​	Kotlin 쪽에서 <span style="color:yellowgreen">'Collections.unmodifiableXXX()'</span> 활용하면 변경 자체를 막을 수 있음

​	Java 코드를 보며 맥락을 확인하고 Java 코드를 가져오는 지점을 wrapping 해서 영향 범위를 최소화해야함 

***

## 📖 확장함수

 Kotlin은 기본적으로 Java와 100% 호환하는 것을 목표로 함

기존 Java위에 자연스럽게 Kotlin 코드를 추가할 수는 없을까?

##### 함수의 코드 자체는 클래스 밖에 있지만, 마치 클래스 안에 있는 멤버함수처럼 호출해서 사용 ( = 확장함수)

```kotlin
//String 클래스를 확장하는 확장함수
fun String.lastChar(): Char {
    return this[this.length - 1]	//this를 이용해 실제 클래스 안의 값에 접근
}
```

+ <span style="color:yellowgreen">'fun 확장하려는 클래스.함수이름(파라미터):리턴타입{}'</span>과 같은 형식으로 만들 수 있음
+ <span style="color:yellowgreen">'this'</span>를 통해서 실제 클래스 안의 값에 접근 가능 (수신 객체 )
+ 확장함수는 클래스에 있는 private/protected 멤버를 가져올 수 없음 (캡슐화가 깨지는 것처럼 보여짐)
+ 확장함수와 멤버함수의 시그니처가 같다면,<span style="color:yellow"> 멤버함수가 우선적으로</span> 호출됨
+ 확장함수 오버라이딩이 일어난 경우엔 해당 변수의 <span style="color:yellow">현재 타입에 따라</span> 어떤 확장함수가 호출될지 결정됨

```kotlin
open class Train(
    val name: String = "새마을 기차",
    val price: Int = 5_000
)

fun Train.isExpensive(): Boolean {
    println("Train의 확장함수")
    return this.price>=10000
}

class Srt:Train("SRT", 40_000)

fun Srt.isExpensive(): Boolean {
    println("SRT의 확장함수")
    return this.price>=10000
}

fun test() {
    val train: Train = Train()
    train.isExpensive() //Train의 확장함수 호출됨
    val srt1: Train = Srt()
    srt1.isExpensive()  //Train의 확장함수 호출됨
    val srt2: Srt = Srt()
    srt2.isExpensive()  //SRT의 확장함수 호출됨
}
```

+ 확장함수 + Custom Getter를 써서 <span style="color:yellow">확장 프로퍼티</span>를 만드는 것도 가능

```kotlin
//String 클래스를 확장하는 확장프로퍼티
val String.lastChar:Char
    get() = this[this.length - 1]
```



***

## 📖 infix 함수

#### 함수를 호출하는 새로운 방법임 (ex. downTo, step)

원래는 '변수.함수이름(argument)'로 호출하지만 중위함수의 경우엔 <span style="color:yellow">'변수 함수이름 argument'</span>로 호출함

```kotlin
//일반함수
fun Int.add(other: Int): Int {
    return this + other
}
//중위함수
infix fun Int.add2(other: Int): Int {
    return this + other
}

    3.add(4)        //일반 확장함수
    3.add2(4)       //Infix 함수 (일반함수처럼 사용가능)
    3 add2 4            //Infix 함수
```

+ 중위함수는 일반함수처럼도 사용이 가능함
+ 예시에는 확장함수에 붙여 사용했지만 당연히 일반함수에도 붙여서 사용할 수 있음

***

## 📖 inline 함수

함수가 호출되는 대신, 함수를 호출한 지점에 함수 본문을 그대로 복붙하고 싶은 경우 사용함

함수를 파라미터로 전달할 때에 오버헤드를 줄일 수 있지만, inlin함수의 사용은 성능측정과 함께 신중해야함

***

## 📖 지역함수

함수안에 함수로 코드가 중복되는 경우 함수안에 함수를 만들어 코드를 간결화 할 수 있음

함수로 추출하면 좋을 것 같은데, 해당 함수를 지금 함수 내에서만 사용하고 싶을 경우 사용함

```kotlin
fun createPerson(firstName: String, lastName: String): Person {
    //지역함수
    fun validateName(name: String, fieldName: String) {
        if (name.isEmpty()) {
            throw IllegalArgumentException("${fieldName}은 비어있을 수 없습니다. 현재 값 : ${name}")
        }
    }
    validateName(firstName, "firstName")
    validateName(lastName, "lastName")

    return Person(firstName, lastName, 1)
}
```

하지만 depth가 깊어지기도 하고 코드가 생각보다 깔끔하지는 않음 (위같은 경우도 그냥 Person 클래스에서 검증하는게 나을듯)

***

+ 수강한 강의 - 자바 개발자를 위한 코틀린 입문(Java to Kotlin Starter Guide), 최태현 from 인프런
  <https://www.inflearn.com/course/java-to-kotlin/dashboard>