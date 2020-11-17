package br.iesb.mobile.calculadora.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import br.iesb.mobile.calculadora.R

class CalcularActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calcular)
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