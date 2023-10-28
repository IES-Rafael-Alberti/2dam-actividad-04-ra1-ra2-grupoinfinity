package com.evilrey844.actividad04

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button


/**
 * Esta es la actividad principal de la aplicación.
 *
 * @suppress "SpellCheckingInspection"
 */
@Suppress("SpellCheckingInspection")
class MainActivity : AppCompatActivity() {

    // Botón para ir a IMCActivity
    private lateinit var buttonIMC: Button

    //Botón primera calculadora
    private lateinit var buttonCalculadora1: Button

    // Botón Segunda calculadora
    private lateinit var buttonCalculadora2: Button

    // Botón para salir de la app
    private lateinit var buttonSalir: Button

    /**
     * Se llama cuando se crea la actividad. Inicializa los botones y define sus comportamientos al hacer clic.
     *
     * @param savedInstanceState Si la actividad se reinicia después de una pausa o detención previa,
     *                           este contiene los datos que más recientemente se suministraron en [onSaveInstanceState].
     *                           De lo contrario, es nulo.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializa el botón IMC
        buttonIMC = findViewById(R.id.IMCCalc)
        buttonIMC.setOnClickListener {
            val intent = Intent(this, IMCActivity::class.java)
            startActivity(intent)
        }

        buttonCalculadora1 = findViewById(R.id.Calculadora1_btn)
        buttonCalculadora1.setOnClickListener {

            val intent = Intent(this, Calculadora1::class.java)
            startActivity(intent)
        }

        buttonCalculadora2 = findViewById(R.id.Calculadora2_btn)
        buttonCalculadora2.setOnClickListener {

            val intent = Intent(this, Calculadora2::class.java)
            startActivity(intent)
        }

        buttonSalir = findViewById(R.id.btnSalir)
        buttonSalir.setOnClickListener {
            /**finish()
            exitProcess(0)
            **/
            finishAndRemoveTask()
        }
    }
}