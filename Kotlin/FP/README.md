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

+ 수강한 강의 - 자바 개발자를 위한 코틀린 입문(Java to Kotlin Starter Guide), 최태현 from 인프런
  <https://www.inflearn.com/course/java-to-kotlin/dashboard>