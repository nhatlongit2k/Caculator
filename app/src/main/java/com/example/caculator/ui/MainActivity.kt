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
        val digitsOperators = caculatorViewModel.digitsOperators(workingsTV.text.toString())
        if (digitsOperators.isEmpty()) return ""

        val timesDivision = caculatorViewModel.timesDivisionCalculate(digitsOperators)
        if (timesDivision.isEmpty()) return ""

        val result = caculatorViewModel.addSubtractCalculate(timesDivision)
        return result
    }
}