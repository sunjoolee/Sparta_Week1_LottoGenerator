package com.sunjoolee.sparta_week1_lottogenerator

import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import androidx.core.view.isGone

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    private val numberPicker:NumberPicker by lazy{findViewById<NumberPicker>(R.id.numberPicker)}

    private val selectButton:Button by lazy{findViewById<Button>(R.id.btn_select)}
    private val generateButton:Button by lazy{findViewById<Button>(R.id.btn_generate)}
    private val clearButton:Button by lazy{findViewById<Button>(R.id.btn_clear)}

    private val numberTextViewArray:Array<TextView> by lazy{
        arrayOf(findViewById(R.id.tv_number1),
            findViewById(R.id.tv_number2),
            findViewById(R.id.tv_number3),
            findViewById(R.id.tv_number4),
            findViewById(R.id.tv_number5),
            findViewById(R.id.tv_number6))
    }

    private var pickedNumbers = arrayOf<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        numberPicker.minValue = 1
        numberPicker.maxValue = 45

        initUI()

        initSelectButton()
        initGenerateButton()
        initClearButton()
    }
    private fun initUI(){
        numberTextViewArray.forEach { it.isGone = true }
    }
    private fun initSelectButton(){
        selectButton.setOnClickListener {
            if(pickedNumbers.size == 6){
                Log.d(TAG, "더이상 번호를 선택할 수 없습니다. 번호를 초기화하십시오.")
                return@setOnClickListener
            }

            val selectedNum = numberPicker.value
            if(pickedNumbers.contains(selectedNum)) {
                Log.d(TAG, "이미 선택한 번호입니다.")
                return@setOnClickListener
            }

            pickedNumbers += selectedNum
            updateUI()
        }
    }
    private fun generatedNumList(){
        val numbers = (1..45).filter{it !in pickedNumbers}
            .shuffled().take(6 - pickedNumbers.size)
        pickedNumbers += numbers
    }
    private fun initGenerateButton() {
        generateButton.setOnClickListener {
            if (pickedNumbers.size == 6) {
                Log.d(TAG, "더이상 번호를 선택할 수 없습니다. 번호를 초기화하십시오.")
                return@setOnClickListener
            }

            generatedNumList()
            updateUI()
        }
    }
    private fun initClearButton(){
        clearButton.setOnClickListener {
            pickedNumbers = arrayOf<Int>()
            initUI()
        }
    }
    private fun updateUI(){
        for(i in 0 until pickedNumbers.size){
            val num = pickedNumbers[i]
            val tv = numberTextViewArray[i]

            tv.text = num.toString()
            tv.isGone = false

            //배경 색 지정하기
            var circleColorId = when(num) {
                    in 0..10 -> R.color.number_color1
                    in 11..20 -> R.color.number_color2
                    in 21..30 -> R.color.number_color3
                    in 31..40 -> R.color.number_color4
                    else -> R.color.number_color5
                }
           var shapeDrawable: GradientDrawable = tv.background as GradientDrawable
            shapeDrawable.setColor(resources.getColor(circleColorId))
            tv.background = shapeDrawable
        }
    }
}

