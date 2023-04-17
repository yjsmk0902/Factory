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