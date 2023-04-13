class Car (
    internal val name: String,  //name에 대한 getter를 internal로 만듦
    private var owner:String,   //owner에 대한 getter/setter를 private으로 만듦
    _price:Int
){
    var price = _price
        private set     //price에 대한 setter를 private으로 만듦
}