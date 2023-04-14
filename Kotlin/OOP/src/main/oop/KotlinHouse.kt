class KotlinHouse (
    private val address:String,
    private val livingRoom: LivingRoom = LivingRoom(10.0)
){
    //코틀린의 중첩 클래스
    inner class LivingRoom (
        private val area:Double
    ){
        val address:String
            get() = this@KotlinHouse.address
    }
}