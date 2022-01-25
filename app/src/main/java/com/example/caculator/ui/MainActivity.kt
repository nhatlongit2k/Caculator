package com.example.caculator.ui

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.caculator.R
import com.example.caculator.adapter.AdapterHistory
import com.example.caculator.viewmodel.CaculatorViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var canAddOperation = false
    private var canAddDecimal = false
    private var listCacu: ArrayList<String> = ArrayList()

    private val caculatorViewModel: CaculatorViewModel by lazy {
        CaculatorViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun openHistory(view: View) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.layout_history)
        val window = dialog.window
        if (window == null) {
            return
        }
        window.setLayout(
            (resources.displayMetrics.widthPixels * 0.90f).toInt(),
            (resources.displayMetrics.heightPixels * 0.60f).toInt()
        )
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val windowAttribute: WindowManager.LayoutParams = window.attributes
        windowAttribute.gravity = Gravity.CENTER
        window.attributes = windowAttribute
        dialog.setCancelable(true)
        val recyclerView: RecyclerView = dialog.findViewById(R.id.recyclerHistory)
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = AdapterHistory(listCacu, this)
        dialog.show()
    }

    fun operationAction(view: View) {
        if (view is Button && canAddOperation) {
            workingsTV.append(view.text)
            canAddOperation = false
            canAddDecimal = true
        }
    }

    fun numberAction(view: View) {
        if (view is Button) {
            if (view.text == ".") {
                if (canAddDecimal)
                    workingsTV.append(view.text)
                canAddDecimal = false
            } else {
                workingsTV.append(view.text)
                canAddDecimal = true
            }
            canAddOperation = true
        }
    }

    fun allClearAction(view: View) {
        workingsTV.text = ""
        resultsTV.text = ""
        canAddOperation = false
    }

    fun backSpaceAction(view: View) {
        val length = workingsTV.length()
        resultsTV.text = ""
        if (length > 0) {
            workingsTV.text = workingsTV.text.subSequence(0, length - 1)
        }
        if (workingsTV.text.length == 0) {
            canAddOperation = false
        }
    }

    fun equalsAction(view: View) {
        resultsTV.text = calculateResults()
        listCacu.add(0, workingsTV.text.toString() + " = " + resultsTV.text.toString())
        if (listCacu.size > 10) {
            listCacu.removeAt(10)
        }
    }

    private fun calculateResults(): String {
//        val digitsOperators = digitsOperators()
//        if (digitsOperators.isEmpty()) return ""
//
//        val timesDivision = timesDivisionCalculate(digitsOperators)
//        if (timesDivision.isEmpty()) return ""
//
//        val result = addSubtractCalculate(timesDivision)

        val digitsOperators = caculatorViewModel.digitsOperators(workingsTV.text.toString())
        if (digitsOperators.isEmpty()) return ""

        val timesDivision = caculatorViewModel.timesDivisionCalculate(digitsOperators)
        if (timesDivision.isEmpty()) return ""

        val result = caculatorViewModel.addSubtractCalculate(timesDivision)
        return result
    }

//    fun plus(a: String, b: String): String {
//        if (a.contains("-") && b.contains("-")) {
//            val a1 = a.replace("-", "")
//            val b1 = b.replace("-", "")
//            return "-" + plus(a1, b1)
//        }
//        if (!a.contains("-") && b.contains("-")) {
//            val b1 = b.replace("-", "")
//            return minus(a, b1)
//        }
//        if (a.contains("-") && !b.contains("-")) {
//            val a1 = a.replace("-", "")
//            return minus(b, a1)
//        }
//        if (a.length > b.length) {
//            return executePlus(a, b)
//        }
//        if (a.length <= b.length) {
//            return executePlus(b, a)
//        }
//        return ""
//    }
//
//    fun executePlus(bigStr: String, smallStr: String): String {
//        var result = ""
//        var i = bigStr.length - 1
//        var j = smallStr.length - 1
//        var nho = 0
//        while (i >= 0) {
//            var kq = 0
//            if (j >= 0) {
//                kq =
//                    Integer.parseInt(bigStr[i].toString()) + Integer.parseInt(smallStr[j].toString())
//                if (nho != 0) {
//                    kq = kq + nho
//                }
//                if (kq > 9) {
//                    nho = 1
//                    kq = kq - 10
//                } else {
//                    nho = 0
//                }
//                i--
//                j--
//            } else {
//                kq = Integer.parseInt(bigStr[i].toString()) + nho
//                if (kq > 9) {
//                    nho = 1
//                    kq = kq - 10
//                } else {
//                    nho = 0
//                }
//                i--
//            }
//            result = result + kq.toString()
//            if (i == -1 && nho != 0) {
//                result = result + nho.toString()
//            }
//        }
//        return result.reversed()
//    }
//
//    fun minus(a: String, b: String): String {
//        if (a.contains("-") && b.contains("-")) {
//            val a1 = a.replace("-", "")
//            val b1 = b.replace("-", "")
//            return minus(b1, a1)
//        }
//        if (a.contains("-") && !b.contains("-")) {
//            val a1 = a.replace("-", "")
//            return "-" + plus(a1, b)
//        }
//        if (!a.contains("-") && b.contains("-")) {
//            val b1 = b.replace("-", "")
//            return plus(a, b1)
//        }
//        when (compareNumber(a, b)) {
//            1 -> {
//                return executeMinus(a, b)
//            }
//            -1 -> {
//                return "-" + executeMinus(b, a)
//            }
//            0 -> {
//                return "0"
//            }
//        }
//        return ""
//    }
//
//    private fun executeMinus(bigStr: String, smallStr: String): String {
//        var result = ""
//        var i = bigStr.length - 1
//        var j = smallStr.length - 1
//        var nho = 0
//        while (i >= 0) {
//            var kq = 0
//            if (j >= 0) {
//                kq =
//                    Integer.parseInt(bigStr[i].toString()) - Integer.parseInt(smallStr[j].toString())
//                if (nho != 0) {
//                    kq = kq - nho
//                    nho = 0
//                }
//                if (kq < 0) {
//                    nho = 1
//                    kq = kq + 10
//                } else {
//                    nho = 0
//                }
//
//                i--
//                j--
//            } else {
//                kq = Integer.parseInt(bigStr[i].toString()) - nho
//                if (kq < 0) {
//                    nho = 1
//                    kq = kq + 10
//                } else {
//                    nho = 0
//                }
//                i--
//            }
//            result = result + kq.toString()
//        }
//        return result.reversed()
//    }
//
//    fun compareNumber(a: String, b: String): Int {
//        if (a.length > b.length) {
//            return 1
//        }
//        if (a.length < b.length) {
//            return -1
//        }
//        if (a.length == b.length) {
//            for (i in 0..a.length - 1) {
//                if (Integer.parseInt(a[i].toString()) > Integer.parseInt(b[i].toString()))
//                    return 1
//                if (Integer.parseInt(a[i].toString()) < Integer.parseInt(b[i].toString()))
//                    return -1
//            }
//        }
//        return 0
//    }
//
//    fun mul(a: String, b: String): String {
//        if (a.contains("-") && b.contains("-")) {
//            val a1 = a.replace("-", "")
//            val b1 = b.replace("-", "")
//            return executeMul(a1, b1)
//        }
//        if (a.contains("-") || b.contains("-")) {
//            val a1 = a.replace("-", "")
//            val b1 = b.replace("-", "")
//            return "-" + executeMul(a1, b1)
//        }
//        return executeMul(a, b)
//    }
//
//    fun executeMul(a: String, b: String): String {
//        var kq = "0"
//        var tg = ""
//        for (i in b.length - 1 downTo 0 step 1) {
//            var nho: Int = 0
//            for (j in a.length - 1 downTo 0 step 1) {
//                var char = Integer.parseInt(a[j].toString()) * Integer.parseInt(b[i].toString())
//                if (nho != 0) {
//                    char = char + nho
//                    nho = 0
//                }
//                if (char > 9) {
//                    nho = char / 10
//                    char = char % 10
//                }
//                tg = tg + char.toString()
//                if (j == 0 && nho != 0)
//                    tg = tg + nho.toString()
//            }
//            tg = tg.reversed()
//            for (k in b.length - 2 downTo i step 1) {
//                tg = tg + "0"
//            }
//            kq = plus(kq, tg)
//            tg = ""
//        }
//        return kq
//    }
//
//    fun divi(a: String, b: String): String {
//        if (a.contains("-") && b.contains("-")) {
//            val a1 = a.replace("-", "")
//            val b1 = b.replace("-", "")
//            return divi(a1, b1)
//        }
//        if (a.contains("-") || b.contains("-")) {
//            val a1 = a.replace("-", "")
//            val b1 = b.replace("-", "")
//            return "-" + divi(a1, b1)
//        }
//        if (compareNumber(a, b) <= 0) {
//            return "0"
//        }
//        return executeDiv(a, b)
//    }
//
//    fun executeDiv(a: String, b: String): String {
//        var tg = ""
//        var kq = ""
//        for (i in 0..a.length - 1) {
//            tg = tg + a[i]
//            var char = tg.toLong() / b.toLong()
//            kq = kq + char.toString()
//            var plusBack = char * Integer.parseInt(b)
//            tg = minus(tg, plusBack.toString())
//        }
//        var cut = 0
//        for (i in 0..kq.length - 1) {
//            if (!kq[i].equals('0')) {
//                break
//            }
//            cut++
//        }
//        kq = kq.substring(cut)
//        return kq
//    }
//
//
//    private fun addSubtractCalculate(passedList: MutableList<Any>): String {
//        var result = passedList[0].toString()
//        for (i in passedList.indices) {
//            if ((passedList[i].equals("+") || passedList[i].equals("-")) && i != passedList.lastIndex) {
//                val operator = passedList[i]
//                val nextDigit = passedList[i + 1].toString()
//                if (operator == "+") {
//                    result = plus(result, nextDigit)
//                }
//                if (operator == "-") {
//                    result = minus(result, nextDigit)
//                }
//            }
//        }
//        return result
//    }
//
//    private fun timesDivisionCalculate(passedList: MutableList<Any>): MutableList<Any> {
//        var list = passedList
//        while (list.contains("x") || list.contains("/")) {
//            list = calcTimesDiv(list)
//        }
//        return list
//    }
//
//    private fun calcTimesDiv(passedList: MutableList<Any>): MutableList<Any> {
//        val newList = mutableListOf<Any>()
//        var restartIndex = passedList.size
//
//        for (i in passedList.indices) {
//            if (
//                (passedList[i].equals("x") || passedList[i].equals("/") || passedList[i].equals("+") || passedList[i].equals("-"))
//                && i != passedList.lastIndex && i < restartIndex
//            ) {
//                val operator = passedList[i]
//                val prevDigit = passedList[i - 1]
//                val nextDigit = passedList[i + 1]
//                when (operator) {
//                    "x" -> {
//                        newList.add(mul(prevDigit.toString(), nextDigit.toString()))
//                        restartIndex = i + 1
//                    }
//                    "/" -> {
//                        newList.add(divi(prevDigit.toString(), nextDigit.toString()))
//                        restartIndex = i + 1
//                    }
//                    else -> {
//                        newList.add(prevDigit.toString())
//                        newList.add(operator.toString())
//                    }
//                }
//            }
//            if (i > restartIndex) {
//                newList.add(passedList[i].toString())
//            }
//        }
//        return newList
//    }
//
//    private fun digitsOperators(): MutableList<Any> {
//        val list = mutableListOf<Any>()
//        var currentDigit = ""
//        for (character in workingsTV.text) {
//            if (character.isDigit() || character == '.')
//                currentDigit += character
//            else {
//                list.add(currentDigit.toString())
//                currentDigit = ""
//                list.add(character.toString())
//            }
//        }
//
//        if (currentDigit != "")
//            list.add(currentDigit.toString())
//        return list
//    }
}