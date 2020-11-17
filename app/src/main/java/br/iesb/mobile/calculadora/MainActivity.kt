package br.iesb.mobile.calculadora

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import br.iesb.mobile.calculadora.R.layout
import kotlinx.android.synthetic.main.activity_main.*
import net.objecthunter.exp4j.ExpressionBuilder
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)

        Log.d("CICLO", "MainActivity")
        Log.d("CICLO", "Passando pelo onCreate")

        //Numbers listeners
        bt0.setOnClickListener {pressedButton(false, "0") }
        bt1.setOnClickListener {pressedButton(false, "1") }
        bt2.setOnClickListener {pressedButton(false, "2") }
        bt3.setOnClickListener {pressedButton(false, "3") }
        bt4.setOnClickListener {pressedButton(false, "4") }
        bt5.setOnClickListener {pressedButton(false, "5") }
        bt6.setOnClickListener {pressedButton(false, "6") }
        bt7.setOnClickListener {pressedButton(false, "7") }
        bt8.setOnClickListener {pressedButton(false, "8") }
        bt9.setOnClickListener {pressedButton(false, "9") }
        btPoint.setOnClickListener {pressedButton(false, ".") }

        //Operators Listeners
        btSum.setOnClickListener {pressedButton(false, "+") }
        btSubtract.setOnClickListener {pressedButton(false, "-") }
        btMultiply.setOnClickListener {pressedButton(false, "*") }
        btDivide.setOnClickListener {pressedButton(false, "/") }
        btOpenP.setOnClickListener {pressedButton(false, "(") }
        btCloseP.setOnClickListener {pressedButton(false, ")") }
        btLn.setOnClickListener {pressedButton(false, "/") }

        btBack.setOnClickListener{
            val lengthTvInput: Int = tvInput.length().toInt() -1
            Log.d("CALC", tvInput.toString())
            Log.d("CALC", "tamanho da tvInput:" + lengthTvInput.toString())

            if (lengthTvInput >= 0 ){
                val string = tvInput.text
                tvInput.text  = string.removeRange(lengthTvInput..lengthTvInput)
            }
        }

        btAc.setOnClickListener {
            tvInput.text = ""
        }

        btEqual.setOnClickListener {
            val resultOp: String = calculate()
            Log.d("CALC", "Resulta do btEqual = " + resultOp)
        }

        btLog.setOnClickListener {
            calcLog()
        }
        btFactorial.setOnClickListener{
            calcFactorial()
        }
        btSin.setOnClickListener{
            calcSin()
        }
        btCos.setOnClickListener{
            calcCos()
        }

        btSquareRoot.setOnClickListener{
            squareRoot()
        }

        btPower.setOnClickListener{
            power()
        }

        btLn.setOnClickListener {
            calcLn()
        }

        btTan.setOnClickListener{
            calcTan()
        }
    }
    /* calculo log base 10 */
    fun calcLog() {
        try {
            val resultLog = takeLog(tvInput.text.toString().toDouble(), 10.0).toString()
            val textHistory = "log("+tvInput.text.toString()+") = "+ resultLog
            registerHistoryOperator(textHistory)
            tvInput.text = resultLog
        }catch (e: Exception){
            error()
        }
    }
    fun takeLog(x: Double, base: Double): Double {
        if (base <= 0.0 || base == 1.0) return Double.NaN
        return Math.log(x) / Math.log(base)
    }


    /* calculo de fatorial */
    fun calcFactorial() {
        Log.d("CALC", "calcFactorial()")
        try {
            when {
                tvInput.text.toString().toInt()==0 -> // 0!
                    tvInput.text="1"
                tvInput.text.toString().toInt()>0 -> {     // >0
                    var sonuc = 1.0
                    for (i in 1..tvInput.text.toString().toInt()){
                        sonuc *= i
                    }
                    val textHistory = "fac("+tvInput.text.toString()+") = "+ sonuc.toString()
                    registerHistoryOperator(textHistory)
                    tvInput.text = sonuc.toString()
                }
                else -> tvInput.text="0"
            }      //<0
        } catch (e: Exception){
            error()
        }
    }


    fun calcLn() {
        Log.d("CALC", "calcLn()")
        try {
            val resultLn =  Math.log(tvInput.text.toString().toDouble()).toString()
            val textHistory = "ln("+tvInput.text.toString()+") = "+ resultLn
            registerHistoryOperator(textHistory)
            tvInput.text = resultLn
        }catch (e: Exception){
            error()
        }
    }
    fun ln(x: Double): Double = Math.log(x)

    /* calculo de tangente */
    fun calcTan() {
        Log.d("CALC", "calcSin()")
        try {
            val resultTan = takeTan(takeSin(tvInput.text.toString().toDouble()) / takeCos(tvInput.text.toString().toDouble()))
            val textHistory = "tan("+tvInput.text.toString()+") = "+ resultTan
            registerHistoryOperator(textHistory)
            tvInput.text = resultTan
        }catch (e: Exception){
            error()
        }
    }
    private fun takeTan(angle: Double):String{     // açılarda virgülden sonraki sayıları 10 adet olarak yuvarlar
        val angstr=angle.toString()
        val angdotcut=angstr.split('.')
        return if (angdotcut[1].length>=10){        // noktadan sonraki 10 karakter ele alınacak
            val dotright10=angdotcut[1].substring(0, 10)
            angdotcut[1].substring(10, angdotcut[1].lastIndex) // 10. sayıdan sonrası
            if(dotright10[9].toString().toInt()>=5){  // noktadan sonraki 10. sayı >=5 ise
                var res = (angdotcut[0]+"."+dotright10.substring(0, 9)).toDouble()
                res += 0.000000001
                res.toString()
            } else{
                "${angdotcut[0]}.${dotright10.substring(0, 9)}"
            }
        }else{
            angle.toString()
        }

    }


    /* calculo de seno  */
    fun calcSin() {
        Log.d("CALC", "calcSin()")
        try {
            val resultSin = takeSin(tvInput.text.toString().toDouble()).toString()
            val textHistory = "sin("+tvInput.text.toString()+") = "+ resultSin
            registerHistoryOperator(textHistory)
            tvInput.text = resultSin
        }catch (e: Exception){
            error()
        }
    }
    private fun takeSin(angle: Double) : Double {
        return if (angle.rem(180) == 0.0 && (angle / 90).rem(2.0) == 0.0) {
            0.0
        } else {
            sin(angle * Math.PI / 180)
        }
    }

    /* calculo de cosseno  */
    fun calcCos() {
        Log.d("CALC", "calcCos()")
        try {
            val resultCos = takeCos(tvInput.text.toString().toDouble()).toString()
            val textHistory = "sin("+tvInput.text.toString()+") = "+ resultCos
            registerHistoryOperator(textHistory)
            tvInput.text = resultCos
        }catch (e: Exception){
            error()
        }
    }
    private fun takeCos(angle: Double) : Double{
        return if (angle.rem(180)!=0.0 && angle.rem(90)==0.0){
            0.0
        }else{
            cos(angle * Math.PI / 180)
        }
    }

    /* raiz quadrada */
    fun squareRoot(){
        try {
            val resultSquareRoot = takeRoot(tvInput.text.toString().toDouble(), 2).toString()
            val textHistory = "√"+tvInput.text.toString()+" = "+ resultSquareRoot
            registerHistoryOperator(textHistory)
            tvInput.text = resultSquareRoot
        }catch (e: Exception){
            error()
        }
    }
    fun takeRoot(x: Double, n: Int): Double {
        if( x>=0 && n>=2){
            if(x==0.0){
                return 0.0
            }
            val np = n - 1
            fun iter(g: Double) = (np * g + x / Math.pow(g, np.toDouble())) / n
            var g1 = x
            var g2 = iter(g1)
            while (g1 != g2) {
                g1 = iter(g1)
                g2 = iter(iter(g2))
            }
            return g1
        }else {
            return 0.0
        }
    }

    /* potencia de 2 */
    fun power(){
        try {
            val resultPower = tvInput.text.toString().toDouble().pow(2.0).toString()
            val textHistory = tvInput.text.toString()+"²"+ " = "+ resultPower
            registerHistoryOperator(textHistory)
            tvInput.text = resultPower
        }catch (e: Exception){
            error()
        }
    }


    /* calcular expressoes */
    fun calculate(): String {
        Log.d("CALC", "calcular")

        if (tvInput.length() == 0){ return ""}

        val input = ExpressionBuilder(tvInput.text.toString()).build()
        val output = input.evaluate()
        Log.d("CALC", "output.toString() = " + output.toString())
        val longOutput = output.toLong()
        Log.d("CALC", "longOutput.toString() = " + longOutput.toString())

        if (output == longOutput.toDouble()){
            val result = longOutput.toString()
            if (tvInput.text.toString() == result.toString()) {return ""}
            Log.d("CALC", " resultado inteiro ")
            registerHistoryEqual(result.toString())
            tvInput.text = result
        }else{
            val result = output.toString()
            if (tvInput.text.toString() == result.toString()) {return ""}
            Log.d("CALC", " resultado fracionario ")
            registerHistoryEqual(result.toString())
            tvInput.text = result
        }
        return tvInput.text.toString()
    }

    /* funcition for error */
    fun error() {
        Log.d("CALC", "error()")
        val textInput = StringBuilder()
        textInput.append(tvHistory.text)
                .append(tvInput.text)
                .append("=")
                .append("NaN \n").toString()
    }

    fun registerHistoryEqual(Result: String) {
        Log.d("CALC", "registerHistory()")
        val textInput = StringBuilder()
        val lineCount: Int = tvHistory.lineCount - 1
        Log.d("CALC", "History LInes: " + lineCount.toString())

        textInput.append(tvHistory.text)
                .append(tvInput.text)
                .append("=")
                .append(Result)
                .append("\n").toString()

        tvHistory.text = textInput

//        gravity="right|bottom"
//        tvHistory.setGravity(Gravity.RIGHT and Gravity.BOTTOM)
        tvHistory.movementMethod= ScrollingMovementMethod()
        Log.d("CALC", "conteudo linha: " + tvHistory.text.lines()[lineCount])

    }

    fun registerHistoryOperator(operation: String) {
        Log.d("CALC", "registerHistory()")
        val textInput = StringBuilder()

        textInput.append(tvHistory.text)
                .append(operation.toString())
                .append("\n").toString()

        tvHistory.text = textInput
        tvHistory.movementMethod= ScrollingMovementMethod()

    }


    fun pressedButton(clear: Boolean, string: String) {
        Log.d("CALC", string)
        tvInput.append(string)
    }


    override fun onStart() {
        super.onStart()
        Log.d("CICLO", "Passando pelo onStart")
    }
    override fun onResume() {
        super.onResume()
        Log.d("CICLO", "Passando pelo onResume")
    }
}