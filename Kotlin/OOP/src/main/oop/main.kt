//해당 코드는 main.kt의 main함수 밖에 쓰여있음

val a = 3

fun add(a: Int, b: Int): Int {
    return a + b
}

////이렇게 쓰면 안되고
//class Member private(
//    val username:String,
//    val age: Int
//)
//이렇게 써야함
class Member private constructor(
    val username: String,
    val age: Int
)

fun main() {
    moveSomething(object : Movable {
        override fun move() {
            println("Move")
        }

        override fun fly() {
            println("Fly")
        }
    })

    val array = arrayOf(100, 200)
    //array 안을 돌리기
    for (i in array.indices) {
        println("${i} ${array[i]}")
    }
    //index와 같이 뽑기
    for ((idx, value) in array.withIndex()) {
        println("${idx} ${value}")
    }

    val numbers = listOf(100, 200)
    val emptyList = emptyList<Int>()

    val setNumbers = setOf(100, 200)
    val emptySet = emptySet<Int>()

}

private fun moveSomething(movable: Movable) {
    movable.move()
    movable.fly()
}