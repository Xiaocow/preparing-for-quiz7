package edu.singaporetech.factorial

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_factorial.*
import kotlinx.coroutines.*
import java.math.BigInteger
import java.math.BigDecimal
import kotlin.system.measureNanoTime
import kotlin.system.measureTimeMillis

class FactorialActivity : AppCompatActivity(), CoroutineScope by MainScope() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_factorial)

        button.setOnClickListener{
            // Get the checked radio button id from radio group
            var id: Int = radioGroup.checkedRadioButtonId
            if (id!=-1){ // If any radio button checked from radio group
                // Get the instance of radio button using id
                launch{

                    var radio: RadioButton = findViewById(id)
                    var result = ""

                    var time = measureNanoTime {

                        when (radio){
                            radio_mj -> {
                                result = factorialMJ(textedit_input.text.toString())
                            }
                            radio_jr ->{
                                result = factorialKR(textedit_input.text.toString())
                            }
                            radio_ji ->{
                                result = factorialKI(textedit_input.text.toString())
                            }
                            radio_nr ->{
                                result = factorialNR(textedit_input.text.toString())
                            }
                            radio_ni ->{
                                result = factorialNI(textedit_input.text.toString())
                            }
                        }
                    }


                    Toast.makeText(this@FactorialActivity, "took "+  time.toDouble()/1000000L +"ms", Toast.LENGTH_LONG).show()
                    textview_output.text = result
                }



            }else{
                // If no radio button checked in this radio group
                Toast.makeText(applicationContext,"On button click : nothing selected", Toast.LENGTH_SHORT).show()
            }
        }

    }

    /**
     * Calculate factorial using http://api.mathjs.org
     * - DON'T EDIT THE NAME of the function
     * - leave the function as PUBLIC
     * - you may edit all other aspects of the function signature and body
     *
     * @param numStr The input number
     * @return The output factorial of num
     */
    suspend fun factorialMJ(numStr: String): String {

        val inputNumber = numStr.toLong()
        var completeFlag = false
        var result = "Infinity"

        val queue = Volley.newRequestQueue(this)
        val url = "https://api.mathjs.org/v4/?expr=factorial($inputNumber)"
        val stringRequest = StringRequest(Request.Method.GET, url,
            Response.Listener{response->
                if (response != "Infinity"){
                    result = response
                }
                completeFlag = true
            },
            Response.ErrorListener {
                completeFlag = true
            })

        queue.add(stringRequest)

        while (!completeFlag){
            delay(1000)
        }

        return result
    }

    /**
     * Calculate factorial recursively in Kotlin
     * - DON'T EDIT THE NAME and input/output TYPES
     * - leave the function as PUBLIC
     * - you may edit all other aspects of the function signature and body
     *
     * @param numStr The input number
     * @return The output factorial of num
     */
    suspend fun factorialKR(numStr: String): String = withContext(Dispatchers.Default)  {
        return@withContext factorial(BigInteger(numStr)).toString()
    }

    private fun factorial(num: BigInteger ): BigInteger  {
        return if (num == BigInteger("1")){
            return num
        }else{
            num * factorial(num - BigInteger("1"))
        }
    }

    /**
     * Calculate factorial iteratively in Kotlin
     * - DON'T EDIT THE NAME and input/output TYPES
     * - leave the function as PUBLIC
     * - you may edit all other aspects of the function signature and body
     *
     * @param numStr The input number
     * @return The output factorial of num
     */
    suspend fun factorialKI(numStr: String): String = withContext(Dispatchers.Default) {

        var factorial  = BigInteger("1")

        for (i in 1..numStr.toLong()) {
            factorial *= BigInteger(i.toString())
        }
        return@withContext factorial.toString()
    }

    /**
     * Calculate factorial recursively in native code
     * - DON'T EDIT THE NAME and input/output TYPES
     * - leave the function as PUBLIC
     * - you may edit all other aspects of the function signature and body
     *
     * @param numStr The input number
     * @return The output factorial of num
     */
    suspend fun factorialNR(numStr: String): String = withContext(Dispatchers.Default){
        return@withContext factorialNRJNI(numStr)
    }

    external fun factorialNRJNI(num: String): String

    /**
     * Calculate factorial iteratively in native code
     * - DON'T EDIT THE NAME and input/output TYPES
     * - leave the function as PUBLIC
     * - you may edit all other aspects of the function signature and body
     *
     * @param numStr The input number
     * @return The output factorial of num
     */
    suspend fun factorialNI(numStr: String): String = withContext(Dispatchers.Default){
        return@withContext factorialNIJNI(numStr)
    }

    external fun factorialNIJNI(num: String): String

    companion object{
        init {
            System.loadLibrary("native")
        }
    }
}
