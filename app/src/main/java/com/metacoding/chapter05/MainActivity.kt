package com.metacoding.chapter05

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.metacoding.chapter05.databinding.ActivityMainBinding
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    //입력값을 받기 위한 변수
    private val firstNumberText = StringBuilder("")
    private val secondNumberText = StringBuilder("")
    private val operatorText = StringBuilder("")
    private val decimalFormatText = DecimalFormat("#,###")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }

    //숫자 버튼이 눌린 경우
    fun numberClicked(view: View) {

        //모든 뷰가 text라는 속성을 갖고 있지 않으므로,
        //그 속성을 갖고 있는 버튼으로 캐스팅을 해준다
        //버튼일 수도 있고 아닐 수도 있으므로 as?로 사용한다
        //따라서 그 이후 값도 모두 nullable 이다.
        val numberString = (view as? Button)?.text.toString() ?: ""
        val numberText = if (operatorText.isEmpty()) firstNumberText else secondNumberText

        //append : 문자열 추가하기기
        numberText.append(numberString)
        updateEquationTextView()
    }

    //지우기 버튼이 눌린 경우
    fun clearClicked(view: View) {
        firstNumberText.clear()
        secondNumberText.clear()
        operatorText.clear()

        updateEquationTextView()
        binding.resultTextView.text = ""
    }

    //= 버튼이 눌린 경우
    fun equalClicked(view: View) {
        if (secondNumberText.isEmpty() || secondNumberText.isEmpty() || operatorText.isEmpty()) {
            Toast.makeText(this, "올바르지 않은 수식입니다.", Toast.LENGTH_SHORT).show()
            return
        }

        updateResultTextView()
    }

    //연산자 버튼이 눌린 경우
    fun operatorClicked(view: View) {
        val operatorString = (view as? Button)?.text.toString() ?: ""

        if (firstNumberText.isEmpty()) {
            Toast.makeText(this, "먼저 숫자를 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        if (secondNumberText.isEmpty().not()) {
            Toast.makeText(this, "1개의 연산자에 대해서만 연산이 가능합니다.", Toast.LENGTH_SHORT).show()
            return
        }

        operatorText.append(operatorString)
        updateEquationTextView()
    }

    private fun updateEquationTextView() {
        //포맷이 적용된 숫자
        val firstFormattedNumber = if(firstNumberText.isNotEmpty())decimalFormatText.format(firstNumberText.toString().toBigDecimal()) else ""
        val secondFormattedNumber = if(secondNumberText.isNotEmpty())decimalFormatText.format(secondNumberText.toString().toBigDecimal()) else ""
        binding.equationTextView.text = "$firstFormattedNumber $operatorText $secondFormattedNumber"
    }

    private fun updateResultTextView() {

        val firstNumber = firstNumberText.toString().toBigDecimal()
        val secondNumber = secondNumberText.toString().toBigDecimal()

        var result = when (operatorText.toString()) {
            "+" -> decimalFormatText.format(firstNumber + secondNumber)
            "-" -> decimalFormatText.format(firstNumber - secondNumber)
            else -> "잘못된 수식입니다."
        }
        //포맷해서 나오는 값은  String이므로 .toString()은 안 붙여줘도 된다!

        binding.resultTextView.text = result
    }
}