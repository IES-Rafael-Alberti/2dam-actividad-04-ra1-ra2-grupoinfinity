package com.evilrey844.actividad04

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.slider.RangeSlider


/**
 * Actividad para calcular el Índice de Masa Corporal (IMC).
 */
@Suppress("SpellCheckingInspection")
class IMCActivity : AppCompatActivity() {

    // Variable para almacenar si el usuario ha seleccionado "Hombre" o "Mujer"
    private var generoSeleccionado: String? = null

    /**
     * Se llama cuando se inicia la actividad.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_imc)

        configurarVista()
    }

    /**
     * Configura la vista inicial de la actividad.
     * Esto incluye la configuración de los botones, el selector de rango y el botón calcular.
     */
    private fun configurarVista() {
        val textViewPeso = findViewById<TextView>(R.id.textViewNumeroPeso)
        val textViewEdad = findViewById<TextView>(R.id.textViewNumeroEdad)
        val rangeSliderAltura = findViewById<RangeSlider>(R.id.rangeSliderAltura)
        val tvHeight = findViewById<TextView>(R.id.tvHeight)

        configurarBotones(textViewPeso, textViewEdad)
        configurarSeleccionDeGenero()
        configurarRangeSlider(rangeSliderAltura, tvHeight)
        configurarBotonCalcular(textViewPeso, rangeSliderAltura)

        // Botón para volver a MainActivity
        val fabBack = findViewById<FloatingActionButton>(R.id.fabBackMain)
        fabBack.setOnClickListener {
            finish()
        }
    }

    /**
     * Configura los botones de incremento y decremento para el peso y la edad.
     *
     * @param textViewPeso El TextView que muestra el peso.
     * @param textViewEdad El TextView que muestra la edad.
     */
    private fun configurarBotones(textViewPeso: TextView, textViewEdad: TextView) {
        val botonMasPeso = findViewById<Button>(R.id.botonMasPeso)
        val botonMenosPeso = findViewById<Button>(R.id.botonMenosPeso)
        val botonMasEdad = findViewById<Button>(R.id.botonMasEdad)
        val botonMenosEdad = findViewById<Button>(R.id.botonMenosEdad)

        botonMasPeso.setOnClickListener {
            val pesoActual = textViewPeso.text.toString().toInt()
            textViewPeso.text = getString(R.string.Weight, pesoActual + 1)
        }

        botonMenosPeso.setOnClickListener {
            val pesoActual = textViewPeso.text.toString().toInt()
            if (pesoActual > 0) {
                textViewPeso.text = (pesoActual - 1).toString()
            }
        }

        botonMasEdad.setOnClickListener {
            val edadActual = textViewEdad.text.toString().toInt()
            textViewEdad.text = getString(R.string.Age, edadActual + 1)
        }

        botonMenosEdad.setOnClickListener {
            val edadActual = textViewEdad.text.toString().toInt()
            if (edadActual > 0) {
                textViewEdad.text = (edadActual - 1).toString()
            }
        }
    }

    /**
     * Configura la selección de género.
     * Maneja cambio de colores en las cardviews cuando estas son seleccionadas y
     * también cuando dejan de estarlo.
     */
    private fun configurarSeleccionDeGenero() {
        val cardView1 = findViewById<CardView>(R.id.cardView1)
        val cardView2 = findViewById<CardView>(R.id.cardView2)

        val colorOriginalCardView1 = cardView1.cardBackgroundColor.defaultColor
        val colorOriginalCardView2 = cardView2.cardBackgroundColor.defaultColor

        // Configura los botones de "Hombre" y "Mujer" para actualizar la variable generoSeleccionado
        cardView1.setOnClickListener {
            generoSeleccionado = "Hombre"
            cardView1.setCardBackgroundColor(Color.parseColor("#2E2EFE"))
            cardView2.setCardBackgroundColor(colorOriginalCardView2)
        }

        cardView2.setOnClickListener {
            generoSeleccionado = "Mujer"
            cardView2.setCardBackgroundColor(Color.parseColor("#DF01D7"))
            cardView1.setCardBackgroundColor(colorOriginalCardView1)
        }
    }



    /**
     * Configura el RangeSlider para seleccionar la altura y actualizar el TextView correspondiente.
     *
     * @param rangeSliderAltura El RangeSlider que permite seleccionar la altura.
     * @param tvHeight El TextView que muestra la altura seleccionada.
     */
    private fun configurarRangeSlider(rangeSliderAltura: RangeSlider, tvHeight: TextView) {
        // Agrega un listener al RangeSlider para actualizar el TextView de la altura
        rangeSliderAltura.addOnChangeListener { _, value, _ ->
            tvHeight.text = getString(R.string.Height, value.toInt())
        }
    }

    /**
     * Configura el botón Calcular.
     *
     * @param textViewPeso El TextView que muestra el peso.
     * @param rangeSliderAltura El RangeSlider que permite seleccionar la altura.
     */
    private fun configurarBotonCalcular(textViewPeso: TextView, rangeSliderAltura: RangeSlider) {
        val botonCalcular = findViewById<Button>(R.id.botonCalcular)

        botonCalcular.setOnClickListener {

            if (generoSeleccionado == null) {
                Toast.makeText(
                    this,
                    "Por favor, selecciona tu género.",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val peso = textViewPeso.text.toString().toDoubleOrNull()

            // Formateo la altura a metros.
            val altura = rangeSliderAltura.values[0].toDouble() / 100

            if (peso != null && peso > 0 && peso < 350) {
                val imc = calcularIMC(peso, altura)

                // Uso un Intent para iniciar ResultIMCActivity
                val intent = Intent(this, ResultIMCActivity::class.java)
                // Se el resultado del cálculo del IMC como un extra
                intent.putExtra("RESULTADO_IMC", imc)
                // Se usa el género seleccionado como un extra
                intent.putExtra("GENERO_SELECCIONADO", generoSeleccionado)
                // Inicio ResultIMCActivity
                startActivity(intent)
            } else {
                Toast.makeText(
                    this,
                    "Por favor, introduce un número válido para el peso.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    /**
     * Calcula el Índice de Masa Corporal (IMC).
     *
     * @param peso El peso en kilogramos.
     * @param altura La altura en metros.
     * @return El IMC calculado.
     */
    private fun calcularIMC(peso: Double, altura: Double): Double {
        return peso / (altura * altura)
    }
}
