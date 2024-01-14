package net.hicare.hicaretest.util

/**
 * 함수 참조를 사용하여 다음 list 에서 짝수를 구하는 함수를 구현 하시오.
 * val numbers = listOf(1,2,3,4,5)
 */

object GetEvenNum {
    // 함수 참조를 사용하여 짝수를 구하는 함수
    private fun isEven(number: Int): Boolean = number % 2 == 0

    fun List<Int>.getOnlyEvenNum() = filter(::isEven)
}