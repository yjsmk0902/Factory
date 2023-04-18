class Fruit(
    val name: String,
    private val price: Int
) {

}

private fun filterFruits(
    fruits: List<Fruit>, filter: (Fruit) -> Boolean
): List<Fruit> {
    val results = mutableListOf<Fruit>()
    for (fruit in fruits) {
        if (filter(fruit)) {
            results.add(fruit)
        }
    }
    return results
}

fun testFruit() {
    val fruits = listOf(
        Fruit("사과", 1_000),
        Fruit("사과", 1_200),
        Fruit("사과", 1_200),
        Fruit("사과", 1_500),
        Fruit("바나나", 3_000),
        Fruit("바나나", 3_200),
        Fruit("바나나", 2_500),
        Fruit("수박", 1_000),
        )
    val isAppleV1 = fun(fruit: Fruit): Boolean {
        return fruit.name == "사과"
    }

    val isAppleV2: (Fruit) -> Boolean = { fruit: Fruit -> fruit.name == "사과" }

    isAppleV1(fruits[0])
    isAppleV1.invoke(fruits[0])

    filterFruits(fruits, isAppleV1)
    filterFruits(fruits, { fruit: Fruit -> fruit.name == "사과" })
    filterFruits(fruits) { fruit: Fruit -> fruit.name == "사과" }
    filterFruits(fruits) { fruit -> fruit.name == "사과" }
    filterFruits(fruits) { it.name == "사과" }

    var targetFruitName = "바나나"
    targetFruitName = "수박"
    filterFruits(fruits) { it.name == targetFruitName }


}
