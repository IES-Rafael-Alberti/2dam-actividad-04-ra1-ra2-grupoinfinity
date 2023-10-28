package com.evilrey844.actividad04

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * Activity para mostrar el resultado del cálculo del IMC.
 *
 * Recibe el resultado de IMCActivity y el género seleccionado
 * desde el Intent, y muestra el resultado y una imagen correspondiente en la interfaz de usuario.
 */
@Suppress("SpellCheckingInspection")
class ResultIMCActivity : AppCompatActivity() {

    // Variables que iniciarán más tarde, no puedo crearlas en el onCreate
    private lateinit var textViewResultado: TextView
    private lateinit var imageViewResultado: ImageView

    /**
     * Se llama cuando se crea la Activity.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_imc)

        textViewResultado = findViewById(R.id.textViewResultado)
        imageViewResultado = findViewById(R.id.imageViewResultado)

        actualizarInterfazConResultadosIMC()
        configurarBotonVolver()
    }

    /**
     * Configura el botón para volver a IMCActivity.
     */
    private fun configurarBotonVolver() {
        val fabBack = findViewById<FloatingActionButton>(R.id.fabBack)
        fabBack.setOnClickListener {
            finish()
        }
    }

    /**
     * Actualiza la interfaz de usuario con los resultados del IMC.
     *
     * Obtiene el resultado del cálculo del IMC y el género seleccionado del Intent,
     * y actualiza el TextView y el ImageView con estos datos.
     */
    private fun actualizarInterfazConResultadosIMC() {
        try {
            val imc = obtenerIMC()
            val generoSeleccionado = obtenerGeneroSeleccionado()

            val mensaje = obtenerMensajeSegunIMC(imc)
            mostrarResultado(imc, mensaje)

            val consejo = obtenerConsejoSegunIMC(imc)
            mostrarConsejo(consejo)

            val idImagenResultado = obtenerImagenResultado(imc, generoSeleccionado)
            mostrarImagen(idImagenResultado)
        } catch (e: Exception) {
            Toast.makeText(this, "Error al obtener los resultados del IMC.", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    /**
     * Obtiene el IMC del Intent.
     *
     * @return El valor del IMC.
     */
    private fun obtenerIMC(): Double {
        return intent.getDoubleExtra("RESULTADO_IMC", 0.0)
    }

    /**
     * Obtiene el género seleccionado del Intent.
     *
     * @return El género seleccionado.
     */
    private fun obtenerGeneroSeleccionado(): String? {
        return intent.getStringExtra("GENERO_SELECCIONADO")
    }


    /**
     * Muestra el resultado del cálculo del IMC en el TextView.
     *
     * @param imc El valor del IMC.
     * @param mensaje El mensaje correspondiente al rango del IMC.
     */
    private fun mostrarResultado(imc: Double, mensaje: String) {
        val imcFormateado = String.format("%.2f", imc)
        val imcMensaje = getString(R.string.imc_result, imcFormateado, mensaje)
        textViewResultado.text = imcMensaje
    }

    /**
     * Muestra una imagen correspondiente al rango del IMC en el ImageView.
     *
     * @param idImagenResultado El ID de la imagen a mostrar.
     */
    private fun mostrarImagen(idImagenResultado: Int) {
        imageViewResultado.setImageResource(idImagenResultado)
    }

    /**
     * Obtiene una imagen correspondiente al rango del IMC y al género seleccionado.
     *
     * @param imc El valor del IMC.
     * @param generoSeleccionado El género seleccionado.
     * @return devuelve la imagen correspondiente al rango del IMC y al género seleccionado.
     */
    private fun obtenerImagenResultado(imc: Double, generoSeleccionado: String?): Int {
        return when {
            imc < 18.5 -> if (generoSeleccionado == "Hombre") R.drawable.delgado else R.drawable.delgada
            imc < 24.9 -> if (generoSeleccionado == "Hombre") R.drawable.normal else R.drawable.normalmujer
            else -> if (generoSeleccionado == "Hombre") R.drawable.grueso else R.drawable.gruesa
        }
    }

    /**
     * Devuelve un mensaje basado en el rango del IMC.
     *
     * @param imc El valor del IMC.
     * @return Un String que representa un mensaje basado en el rango del IMC.
     */
    private fun obtenerMensajeSegunIMC(imc: Double): String {
        return when {
            imc < 18.5 -> "Te encuentras dentro del rango de peso insuficiente."
            imc < 24.9 -> "Te encuentras dentro del rango de peso normal o saludable."
            imc < 29.9 -> "Te encuentras dentro del rango de sobrepeso."
            else -> "Te encuentra dentro del rango de obesidad."
        }
    }

    /**
     * Obtiene un mensaje de consejo basado en el valor del IMC.
     *
     * @param imc El valor del Índice de Masa Corporal.
     * @return Un mensaje de consejo que corresponde al rango del IMC.
     */
    private fun obtenerConsejoSegunIMC(imc: Double): String {
        return when {
            imc < 18.5 -> "Estás por debajo de un peso saludable. No te desanimes, es importante que busques asesoramiento nutricional."
            imc < 24.9 -> "¡Enhorabuena! Te encuentras en un rango de peso saludable. Sigue manteniendo un estilo de vida sano."
            imc < 29.9 -> "Estás en el rango de sobrepeso. No te preocupes, con algunos ajustes en tu dieta y actividad física puedes mejorar tu salud."
            else -> "Estás en el rango de obesidad. No te desanimes, es importante que busques asesoramiento nutricional."
        }
    }

    /**
     * Muestra el mensaje de consejo en la interfaz de usuario y dirige a una cita médica.
     *
     * @param consejo El mensaje de consejo que se mostrará.
     */
    private fun mostrarConsejo(consejo: String) {
        val textoConsejo: TextView = findViewById(R.id.textoConsejo)
        val consejoSalud = getString(R.string.consejo_salud)
        val textoFinal = "$consejo $consejoSalud"
        textoConsejo.text = HtmlCompat.fromHtml(textoFinal, HtmlCompat.FROM_HTML_MODE_COMPACT)
        textoConsejo.movementMethod = LinkMovementMethod.getInstance()
    }

}