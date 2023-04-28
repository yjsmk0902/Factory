package com.group.libraryapp

import java.lang.Exception
import kotlin.IllegalStateException

fun main() {
    val calculatorTest = CalculatorTest()
    calculatorTest.addTest()
    calculatorTest.minusTest()
    calculatorTest.multiplyTest()
    calculatorTest.divideTest()
}

class CalculatorTest{

    fun addTest() {

        //given
        val calculator = Calculator(5)

        //when
        calculator.add(3)

        //then
        //Calculator 클래스를 Data 클래스로 만들어 검증
//        val expectedCalculator = Calculator(8)
//        if (calculator != expectedCalculator) {
//            throw IllegalStateException()
//        }

        //set()을 풀어주거나 _을 이용한 custom getter를 만들어 검증
        if (calculator.number != 8) {
            throw IllegalStateException()
        }
    }

    fun minusTest() {

        //given
        val calculator = Calculator(5)

        //when
        calculator.minus(3)

        //then
        if (calculator.number != 2) {
            throw IllegalStateException()
        }
    }

    fun multiplyTest() {

        //given
        val calculator = Calculator(5)

        //when
        calculator.multiply(3)

        //then
        if (calculator.number != 15) {
            throw IllegalStateException()
        }
    }

    fun divideTest() {

        //Case 1) 0이 아닌 수로 나눌 때
        //given
        val calculator = Calculator(5)

        //when
        calculator.divide(2)

        //then
        if (calculator.number != 2) {
            throw IllegalStateException()
        }

        //Case 2) 0인 수로 나눌 때
        fun divideExceptionTest() {
            //given
            val calculator = Calculator(5)

            //when
            try {
                calculator.divide(0)
            } catch (e: IllegalStateException) {
                //테스트 성공!
                return
            } catch (e: Exception) {
                throw IllegalStateException()
            }
            throw IllegalStateException("기대하는 예외가 발생하지 않음")
        }
    }
}