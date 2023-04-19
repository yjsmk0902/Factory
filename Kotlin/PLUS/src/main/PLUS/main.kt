class Fruit(
    val name: String,
)

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

fun main() {

    val person = Person("Luke", 100)    //Person 인스턴스 생성
    val (name, age) = person            //각 변수에 person의 데이터 입력
    println("이름 : $name / 나이 : $age")       //각 변수에 저장된 값 출력

    //val (name, age) = person 이 코드는 다음과 같음
    val name = person.component1()
    val age = person.component2()

    //Jump의 사용
    val numbers = listOf(1, 2, 3)

    run {
        numbers.map { it + 1 }.forEach {
            println(it)
            return@forEach
        }
    }

    for (number in numbers) {
        println(number)
        break
    }

    //Label의 사용
    loop@ for (i in 1..100) {
        for (j in 1..100) {
            if (j == 2) {
                break@loop      //이중루프 전체를 탈출함
            }
        }
    }

    //TakeIf / TakeUnless
    val number = 3
    fun getNumberOrNullV2(): Int? {
        return number.takeIf { it > 0 }
    }
    fun getNumberOrNullV3(): Int? {
        return number.takeUnless { it > 0 }
    }
}

//이런 함수가 있을 때
fun filterFruits(fruits: List<Fruit>, filter: (Fruit) -> Boolean) {}

//이처럼 Typealias를 활용
typealias FruitFilter = (Fruit) -> Boolean

fun filterFruitsAlias(fruits: List<Fruit>, filter: FruitFilter) {}
