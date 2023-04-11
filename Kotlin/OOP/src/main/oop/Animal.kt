//Kotlin의 추상 클래스
abstract class Animal(
    //Getter/Setter 자동 생성
    //상속받은 자식클래스에서 Getter를 재정의 하기위해 open 붙임
    protected val species: String,
    protected open val legCount: Int,
) {
    //추상 메소드
    abstract fun move()
}

//상위 클래스를 설계할 때 생성자 또는 초기화 블럭에서 사용되는 프로퍼티는 open을 피해야함

//상속 관련 키워드 정리
//  1. final => override 할 수 없게 함 (default)
//  2. open => override를 열어줌
//  3. abstract => 반드시 override 해야함
//  4. override => 상위 타입을 override

//상속 또는 구현을 할때 ':'을 사용함
//상위 클래스 상속을 구현할 때 생성자를 반드시 호출해야함
//override를 필수적으로 붙여야함
//추상 멤버가 아니면 기본적으로 오버라이드 불가능
//  이 때는 open으로 열어줘야함
//상위 클래스의 생성자 또는 초기화 블럭에서 open 프로퍼티를
//사용할 경우 얘기치 못한 버그가 발생할 수 있음