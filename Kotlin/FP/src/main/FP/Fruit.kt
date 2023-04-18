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

    //Filter
    val apples = fruits.filter{fruit->fruit.name=="사과"}		//이름이 사과인 애들 뽑기
    val applesIdx = fruits.filterIndexed(){ idx, fruit ->
        println(idx)
        fruit.name == "사과"
    }		//이름이 사과인 애들 뽑기 + 인덱스 사용

    //Map
    //사과인 애들의 현재 가격을 뽑기
    val applePrice = fruits.filter{it.name=="사과"}.map { it.currentPrice }
    val applePriceIndex = fruits.filter{ it.name = "사과"}.mapIndexed { idx, fruit ->
        println(idx)
        fruit.currentPrice
    }		//사과인 애들의 현재 가격을 뽑기 + 인덱스 사용
    //사과인 애들의 nullOrValue()에 해당하는 값 중 null이 아닌 것만 뽑기
    val appleNull = fruits.filter{it.name=="사과"}.mapNotNull{fruit: Fruit -> fruit.nullOrValue() }

    //All
    val isAppleAll = fruits.all{ it.name == "사과"}		//컬렉션 안의 모든 name이 사과면 true

    //none
    val isAppleNone = fruits.none{ it.name == "사과"}		//컬렉션 안의 모든 name이 사과가 아니면 true

    //Any
    val isAppleAny = fruits.any{ it.name == "사과"}		//컬렉션 안의 name 중 하나라도 사과면 true

    //Count
    val fruitCount = fruits.count()     //컬렉션의 사이즈

    //SortedBy(Decending)
    val fruitCountSortedBy = fruits.sortedBy{ it.currentPrice }     //현재가를 오름차순으로 정렬 저장

    //DistinctBy
    val fruitDistinctBy = fruits.distinctBy{it.name}    //같은 이름인 애들 중복 제거

    //First/Last
    val fruitFirst = fruits.first()     //첫번째 요소를 가져옴(null이면 안됨)
    val fruitFirstOrNull = fruits.firstOrNull() //첫번째 요소를 가져옴
    val fruitLast = fruits.last()       //마지막 요소를 가져옴(null이면 안됨)
    val fruitLastOrNull = fruits.lastOrNull()   //마지막 요소를 가져옴

    //GroupBy
    //Key: 과일이름 / Value: 리스트<과일>
    val mapGroup: Map<String, List<Fruit>> =fruits.groupBy{ it.name }
    //Key: 과일이름 / Value: 출고가
    val mapGroup2: Map<String, List<Unit>> =fruits.groupBy({ it.name }, { it.factoryPrice })

    //AssociateBy
    //Key: ID / Value: 리스트<과일>
    val mapAssociate: Map<String, Fruit> = fruits.associateBy { it.id }
    //Key: ID / Value: 출고가
    val mapAssociate2 = fruits.associateBy({it.id}, {it.factoryPrice})
    
    
    
    
}
