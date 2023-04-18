import java.lang.IllegalArgumentException

fun main() {
    val array = arrayOf(100, 200)
    //array 안을 돌리기
    for (i in array.indices) {
        println("${i} ${array[i]}")
    }

    //index와 같이 뽑기
    for ((idx, value) in array.withIndex()) {
        println("${idx} ${value}")
    }

    val numbersList = listOf(100, 200)
    val mutableNumbers = mutableListOf(100, 200)
    val emptyList = emptyList<Int>()	//빈 리스트 만들기 - 타입을 명시해줌
    println(numbersList[0])		//element 가져오기
    mutableNumbers.add(300) 	//가변 리스트에 element 추가하기

    val numbersSet = setOf(100, 200)
    val mutableSet = mutableSetOf(100,200)
    val emptySet = emptySet<Int>()		//빈 리스트 만들기 - 타입을 명시해줌
    mutableSet.add(300)		//가변집합에 element 추가하기

    //Java처럼 쓰기
    val oldMap = mutableMapOf<Int, String>()
    oldMap.put(1,"MONDAY")

    //Kotlin 답게 쓰기
    val map = mutableMapOf<Int, String>()
    map[1] = "MONDAY"
    map[2] = "TUESDAY"

    mapOf(1 to "MONDAY", 2 to "TUESDAY")    //중위 호출로 넣기

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
}

//String 클래스를 확장하는 확장함수
fun String.lastChar(): Char {
    return this[this.length - 1]
}

//String 클래스를 확장하는 확장프로퍼티
val String.lastChar:Char
    get() = this[this.length - 1]

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
//일반함수
fun Int.add(other: Int): Int {
    return this + other
}
//중위함수
infix fun Int.add2(other: Int): Int {
    return this + other
}



fun test() {
    val train: Train = Train()
    train.isExpensive() //Train의 확장함수 호출됨
    val srt1: Train = Srt()
    srt1.isExpensive()  //Train의 확장함수 호출됨
    val srt2: Srt = Srt()
    srt2.isExpensive()  //SRT의 확장함수 호출됨

    3.add(4)        //일반 확장함수
    3.add2(4)       //Infix 함수 (일반함수처럼 사용가능)
    3 add2 4            //Infix 함수
}

//fun createPerson(firstName: String, lastName: String): Person {
//    //지역함수
//    fun validateName(name: String, fieldName: String) {
//        if (name.isEmpty()) {
//            throw IllegalArgumentException("${fieldName}은 비어있을 수 없습니다. 현재 값 : ${name}")
//        }
//    }
//    validateName(firstName, "firstName")
//    validateName(lastName, "lastName")
//
//    return Person(firstName, lastName, 1)
}
