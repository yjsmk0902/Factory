💻 코틀린 PLUS
=============



## 📖 Type Alias / As import

+ ### Type Alias란?

  ##### 긴 이름의 클래스 혹은 함수 타입이 있을 때 더 축약하거나 더 좋은 이름을 쓰고 싶은 경우 사용

```kotlin
fun filterFruits(fruits: List<Fruit>, filter: (Fruit) -> Boolean) {
    
}
```

위의 함수는 파라미터로 함수를 받는데, 이때 '(타입) -> 타입'의 타입은 코드를 읽어내는데 어려움을 줄 수 있음

그래서 위와 같은 경우 Type Alias를 사용함

```kotlin
typealias FruitFilter = (Fruit) -> Boolean

fun filterFruitsAlias(fruits: List<Fruit>, filter: FruitFilter) {}
```

+ ### As import란?

  ##### 다른 패키지의 같은 이름 함수를 동시에 가져오고 싶을 때 사용함

  ##### 어떤 클래스나 함수를 import할 때 이름을 바꾸는 기능

***

## 📖 구조분해 / ComponentN 함수

+ ### 구조분해란?

  ##### 복합적인 값을 분해하여 여러 변수를 한 번에 초기화하는 것 

```kotlin
val person = Person("Luke", 100)    //Person 인스턴스 생성
val (name, age) = person            //각 변수에 person의 데이터 입력
println("이름 : $name / 나이 : $age")       //각 변수에 저장된 값 출력
```

```kotlin
//val (name, age) = person 이 코드는 다음과 같음
val name = person.component1()
val age = person.component2()
```

data class에서는 기본적으로 본인들이 가지고 있는 필드에 대해 componentN이라는 함수를 만듦

따라서 위처럼 바로 할당해줄 수 있음

만약 그럼 data class가 아닌 곳에서 활용하려면 어떻게 해야할까?

```kotlin
class Person(
    val name: String,
    val age: Int
) {
    //componentN은 연산자의 속성을 가지고 있기 때문에 operator를 써줘야함
    operator fun component1(): String {
        return this.name
    }

    operator fun component2(): Int {
        return this.age
    }
}
```

위처럼 componentN에 대한 연산자 오버라이딩으로 data class에서 사용했던 것처럼 만들 수 있음

***

## 📖 Jump / Label

기본적인 for문에서는 break / continue를 활용하지만 함수형 for문(forEach)에서는 break와 continue의 사용이 불가능

따라서 다음과 같이 사용함

```kotlin
val numbers = listOf(1, 2, 3)
run {
    numbers.map { it + 1 }.forEach {
        println(it)
        return@run				//break의 역할을 함
    }
}
for (number in numbers) {
    println(number)
    break
}
```

continue의 경우도 비슷하게 'return@forEach'로 사용할 수 있지만 사용을 지양함

그냥 break / continue를 사용해야할 상황이라면 기본적인 for문을 사용하는 것이 나을 듯

+ ### Label이란?

  ##### 특정 expression에 라벨이름@ 을 붙여 하나의 라벨로 간주하고 break / continue / return 등을 사용할 수 있음

```kotlin
//Label의 사용
loop@ for (i in 1..100) {
    for (j in 1..100) {
        if (j == 2) {
            break@loop      //이중루프 전체를 탈출함
        }
    }
}
```

이처럼 Label을 이용해 특정 루프를 돌게 할 수 있지만 이 또한 사용을 지양함

위아래로 왔다갔다 할 경우 복잡도가 띠용할 정도로 증가할 수 있고, 코드의 유지보수도 힘들어 질 수 있음

***

## 📖 TakeIf / TakeUnless

+ ### TakeIf란?

  ##### 주어진 조건을 만족하면 그 값이, 그렇지 않으면 null을 반환

```kotlin
fun getNumberOrNullV2(): Int? {
    return number.takeIf { it > 0 }
}
```

+ ### TakeUnless란?

  ##### 주어진 조건을 만족하지 않으면 그 값이, 그렇지 않으면 null을 반환

```kotlin
fun getNumberOrNullV3(): Int? {
    return number.takeUnless { it > 0 }
}
```

***

+ 수강한 강의 - 자바 개발자를 위한 코틀린 입문(Java to Kotlin Starter Guide), 최태현 from 인프런
  <https://www.inflearn.com/course/java-to-kotlin/dashboard>