package com.evilrey844.actividad04

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat

/**
 * Clase MainActivity que hereda de AppCompatActivity.
 * Esta clase se encarga de manejar la interfaz de usuario de la aplicación.
 *
 * @property calculo Instancia de la clase Calculo que se encarga de realizar los cálculos.
 * @property txtResultado TextView que muestra el resultado de los cálculos.
 */
@Suppress("SpellCheckingInspection")
class Calculadora2 : AppCompatActivity() {
    private lateinit var calculo: Calculo2
    private lateinit var txtResultado: TextView
    private lateinit var btnVolver: Button

    // Declaro botones numéricos y operacionales que usaré asociados a su id correspondiente.
    private val botonesNumeros = listOf(
        R.id.boton0, R.id.boton1, R.id.boton2, R.id.boton3,
        R.id.boton4, R.id.boton5, R.id.boton6, R.id.boton7,
        R.id.boton8, R.id.boton9
    )

    private val botonesOperaciones = listOf(
        R.id.botonSuma, R.id.botonResta,
        R.id.botonMultiplicacion, R.id.botonDivision
    )

    private fun actualizarTextoResultado() {
        txtResultado.text = getString(R.string.resultado_texto, calculo.num1, calculo.operacion, calculo.num2)
    }
    /**
     * Método onCreate que se ejecuta cuando se crea la actividad
     *
     * @param savedInstanceState Bundle que contiene el estado guardado de la actividad.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculadora2)

        // Botón para volver a la pantalla de inicio
        btnVolver = findViewById(R.id.botonAgusBack)
        btnVolver.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // Cambio el color de la barra de estado a negro para pulir diseño.
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, android.R.color.black)

        calculo = Calculo2(this)
        txtResultado = findViewById(R.id.textViewResultado)

        // Bucle for para asignar la funcionalidad a los botones numéricos
        for (id in botonesNumeros) {
            val boton = findViewById<Button>(id)
            boton.setOnClickListener {
                calculo.setNumClicked((it as Button).text.toString())
                txtResultado.text = if (calculo.operacion.isEmpty()) calculo.num1 else calculo.num2
                actualizarTextoResultado()
            }
        }

        // Bucle for para asignar la funcionalidad a los botones operacionales.
        for (id in botonesOperaciones) {
            val boton = findViewById<Button>(id)
            boton.setOnClickListener {
                calculo.setOperaciones((it as Button).text.toString())
                actualizarTextoResultado()
            }
        }

        // Trabajo con el botón igual, llamo a la función igual. Añado un condicional que me permite discriminar
        // el decimal del número si este es "0".
        val botonIgual = findViewById<Button>(R.id.botonIgual)
        botonIgual.setOnClickListener {
            calculo.calcular()

            // Solo actualiza TextViewResultado si se ha realizado un cálculo
            if (calculo.fase == 3) {
                if (calculo.resultado % 1 == 0.0) {
                    txtResultado.text = calculo.resultado.toInt().toString()
                } else { // Si el número no tiene resto "0", respeto el float y lo convierto a string
                    txtResultado.text = calculo.resultado.toString()
                }
            }
        }

        val botonPunto = findViewById<Button>(R.id.botonPunto)
        botonPunto.setOnClickListener {
            calculo.setPuntoClicked()
            actualizarTextoResultado()
        }

        // Botón CE (Clear Entry) para resetear los valores en la calculadora y en el TextView del resultado.
        val botonCE = findViewById<Button>(R.id.botonCE)
        botonCE.setOnClickListener {
            calculo.resetear()
            txtResultado.text = ""
        }

        // Botón Limpiar C para eliminar dígitos y operadores. Añado recurso a mi string.xml para
        // mejorar la accesibilidad y una futurible localización de mi código.
        val botonLimpiarC = findViewById<Button>(R.id.botonLimpiarC)
        botonLimpiarC.setOnClickListener {
            calculo.botonLimpiarC()
            txtResultado.text = getString(R.string.resultado_texto, calculo.num1, calculo.operacion, calculo.num2)
        }
    }
}
