class PersonStatic private constructor(
    var name:String,
    var age:Int
) {
    //Factory라는 이름을 부여하고, Log 인터페이스를 상속받음
    companion object Factory : Log{
        private const val MIN_AGE=1

        @JvmStatic
        fun newBaby(name: String): PersonStatic {
            return PersonStatic(name, MIN_AGE)
        }
        //인터페이스 구현
        override fun log() {
            println("This object is companion object.")
        }
    }
}

interface Log{
    fun log()
}