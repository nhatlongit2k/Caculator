package com.example.caculator.viewmodel

import com.example.caculator.respository.CaculatorRespository

class CaculatorViewModel {
    private val caculatorRespository: CaculatorRespository = CaculatorRespository()

    fun addSubtractCalculate(passedList: MutableList<Any>): String = caculatorRespository.addSubtractCalculate(passedList)

    fun timesDivisionCalculate(passedList: MutableList<Any>): MutableList<Any> = caculatorRespository.timesDivisionCalculate(passedList)

    fun digitsOperators(workingsTV: String): MutableList<Any> = caculatorRespository.digitsOperators(workingsTV)
}